package com.library.services;

import com.library.enums.BookStatus;
import com.library.enums.BorrowStatus;
import com.library.enums.MemberStatus;
import com.library.exceptions.BookNotAvailableException;
import com.library.exceptions.BookNotFoundException;
import com.library.exceptions.BorrowLimitExceededException;
import com.library.exceptions.RecordNotFoundException;
import com.library.interfaces.BorrowRecordInterface;
import com.library.models.Book;
import com.library.models.BorrowRecord;
import com.library.models.User;
import com.library.repos.BookRepo;
import com.library.repos.BorrowRepo;
import com.library.repos.UserRepo;

import java.time.LocalDate;

import static com.library.app_files.Config.*;


public class BorrowService{
    private final BorrowRecordInterface borrowRecords;
    private final BookRepo bookrepo;
    private final UserRepo userRepo;

    public BorrowService(){
        this.borrowRecords = new BorrowRepo();
        this.bookrepo = new BookRepo();
        this.userRepo = new UserRepo();
    }

    public boolean borrowBook(String userId, String bookId){
        User user = userRepo.getUserById(userId);

        Book book = bookrepo.getBookById(bookId);
        if (book == null)
            throw new BookNotFoundException("Book not found");

        if (!book.getBookStatus().toString().equalsIgnoreCase("AVAILABLE"))
            throw new BookNotAvailableException("Book not available");

        int numberOfBorrowings = borrowRecords.countUserActiveBorrowings(userId);
        int userMaxBorrowing = getBorrowingLimit(user.getMembershipType());

        if(numberOfBorrowings >= userMaxBorrowing)
            throw new BorrowLimitExceededException("Borrowing limit for "+user.getMembershipType()+" members is "
                    + userMaxBorrowing);

        book.setBookStatus(BookStatus.BORROWED);
        String newBookValues = generateBookStringValue(book);

        BorrowRecord newRecord = new BorrowRecord(generateTransactionId(), bookId, userId, LocalDate.now(),
                LocalDate.now().plusDays(BORROW_PERIOD), null, BorrowStatus.BORROWED);

        borrowRecords.addBorrowRecord(newRecord);
        bookrepo.editBook(book, newBookValues);

        return true;
    }

    public boolean returnBook(String bookId){
        BorrowRecord record = borrowRecords.getRecordByBookId(bookId);
        if (record == null)
            throw new RecordNotFoundException("Record not found");

        if (record.getBorrowStatus().toString().equalsIgnoreCase("returned"))
            throw new RuntimeException("This book has already been returned");

        User user = userRepo.getUserById(record.getUserId());
        Book book = bookrepo.getBookById(record.getBookId());

        int daysLate = calculateDaysLate(record.getDueDate(), LocalDate.now());
        double fineRate = getFineRate(user.getMembershipType());
        double fine = calculateFine(daysLate, fineRate);

        if (fine > 0) {
            user.setOutstandingFine(user.getOutstandingFine() + fine);
            updateSuspensionStatus(user);
        }

        book.setBookStatus(BookStatus.AVAILABLE);
        record.setBorrowStatus(BorrowStatus.RETURNED);

        String userNewValues = generateUserStringValue(user);
        String bookNewValues = generateBookStringValue(book);
        String recordNewValues = generateBorrowRecordStringValue(record);

        userRepo.editUser(user, userNewValues);
        bookrepo.editBook(book, bookNewValues);
        borrowRecords.editBorrowRecord(record, recordNewValues);

        return true;
    }

    public void payFine(String userId, double amount){
        User user = userRepo.getUserById(userId);

        if(amount <= 0)
            throw new RuntimeException("Amount must be positive");

        if(amount > user.getOutstandingFine())
            throw new RuntimeException("Please pay only your debt #"+user.getOutstandingFine()+", don't add extra");

        user.setOutstandingFine(user.getOutstandingFine() - amount);
        updateSuspensionStatus(user);

        String userNewValues = generateUserStringValue(user);
        userRepo.editUser(user, userNewValues);
    }

    public void viewPersonalBorrowingRecords(String id){
        BorrowRecord[] records = borrowRecords.getAllBorrowRecords();

        for(BorrowRecord record : records){
            if(record != null && record.getUserId().equals(id))
                System.out.println(record);
        }
    }

    public void viewAllBorrowRecords(String id){
        User user = userRepo.getUserById(id);

        if(!user.getMembershipType().toString().equalsIgnoreCase("staff"))
            throw new RuntimeException("Not authorized, this is only for staff");

        for (BorrowRecord record : borrowRecords.getAllBorrowRecords())
            System.out.println(record);
    }

    private void updateSuspensionStatus(User user){
        if(user.getOutstandingFine() >= SUSPENSION_FINE_THRESHOLD){
            user.setStatus(MemberStatus.SUSPENDED);
        }else if (user.getStatus() == MemberStatus.SUSPENDED){
            user.setStatus(MemberStatus.ACTIVE);
        }
    }
}