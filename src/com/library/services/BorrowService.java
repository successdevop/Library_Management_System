package com.library.services;

import com.library.enums.BookStatus;
import com.library.enums.BorrowStatus;
import com.library.enums.MemberStatus;
import com.library.enums.MembershipType;
import com.library.exceptions.*;
import com.library.interfaces.BorrowRecordInterface;
import com.library.models.Book;
import com.library.models.BorrowRecord;
import com.library.models.User;
import com.library.repos.BookRepo;
import com.library.repos.BorrowRepo;
import com.library.repos.UserRepo;

import java.time.LocalDate;

import static com.library.app_files.Config.*;
import static com.library.utils.MethodUtils.*;


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
        if(user == null)
            throw new UserNotFoundException("member not found");

        Book book = bookrepo.getBookById(bookId);
        if (book == null)
            throw new BookNotFoundException("Book not found");

        if (!book.getBookStatus().toString().equalsIgnoreCase("AVAILABLE"))
            throw new BookNotAvailableException("Book not available");

        int numberOfActiveBorrowings = countUserActiveBorrowings(userId);
        int userMaxBorrowing = getBorrowingLimit(user.getMembershipType());

        if(numberOfActiveBorrowings >= userMaxBorrowing)
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

    public boolean returnBook(String userId, String bookId){
        BorrowRecord record = borrowRecords.getRecordByBookId(bookId);
        if (record == null)
            throw new RecordNotFoundException("Record not found");

        if (record.getBorrowStatus() == BorrowStatus.RETURNED)
            throw new RuntimeException("This book has already been returned");

        User user = userRepo.getUserById(userId);
        Book book = bookrepo.getBookById(bookId);

        int daysLate = calculateDaysLate(record.getDueDate(), LocalDate.now());
        double fineRate = getFineRate(user.getMembershipType());
        double fine = calculateFine(daysLate, fineRate);

        if (fine > 0) {
            user.setOutstandingFine(user.getOutstandingFine() + fine);
            updateSuspensionStatus(user);
        }

        book.setBookStatus(BookStatus.AVAILABLE);
        record.setBorrowStatus(BorrowStatus.RETURNED);
        record.setReturnDate(LocalDate.now());

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

        if(!(user.getMembershipType() == MembershipType.STAFF))
            throw new RuntimeException("Not authorized, this is only for staff members");

        for (BorrowRecord record : borrowRecords.getAllBorrowRecords())
            if(record != null)
                System.out.println(record);
    }

    public void deleteBorrowRecord(String recordId){
        BorrowRecord record = borrowRecords.getRecordByTransactionId(recordId);

        if (record == null)
            throw new RuntimeException("Record not found");

        if (record.getBorrowStatus() == BorrowStatus.BORROWED)
            throw new RuntimeException("Cannot delete a transaction that is active/borrowed");

        Book[] books = bookrepo.getAllBooks();
        for (Book book : books) {
            if (book != null && book.getBookId().equalsIgnoreCase(record.getBookId()) &&
                    book.getBookStatus() == BookStatus.BORROWED)
                throw new RuntimeException("Cannot delete a transaction that is active/borrowed");
        }

        borrowRecords.deleteBorrowRecord(recordId);
    }

    public String libraryStatistics(){
        int totalBooks = bookStatistics()[0];
        int totalAvailableBooks = bookStatistics()[1];
        int totalBorrowedBooks = bookStatistics()[2];
        int totalUsers = userStatistics()[0];
        int totalActiveUsers = userStatistics()[1];
        int totalInactiveUsers = userStatistics()[2];
        int totalOverdueBooks = (int)borrowRecordStatistics()[0];
        double totalFines = borrowRecordStatistics()[1];

        return "Total Books:\t\t\t"+totalBooks+"\n"+
                "Available Books:\t\t"+totalAvailableBooks+"\n"+
                "Borrowed Books:\t\t\t"+totalBorrowedBooks+"\n"+
                "Total Members:\t\t\t"+totalUsers+"\n"+
                "Active Members:\t\t\t"+totalActiveUsers+"\n"+
                "Inactive Members:\t\t"+totalInactiveUsers+"\n"+
                "Overdue Books:\t\t\t"+totalOverdueBooks+"\n"+
                "Total Fines:\t\t\t#"+totalFines;
    }

    private double[] borrowRecordStatistics(){
        BorrowRecord[] records = borrowRecords.getAllBorrowRecords();
        Book[] books = bookrepo.getAllBooks();
        User[] users = userRepo.getAllUsers();

        int overdueBooks = 0;
        double totalFines = 0;

        for (BorrowRecord record : records) {
            if (record == null) continue;

            for (Book book : books) {
                if (book == null) continue;

                if (record.getBookId().equalsIgnoreCase(book.getBookId())) {
                    int days = calculateDaysLate(record.getDueDate(), record.getReturnDate());
                    if (days > 0) overdueBooks++;
                    else break;

                    for (User user : users) {
                        if (user == null) continue;

                        if (record.getUserId().equalsIgnoreCase(user.getId())) {
                            double fine = calculateFine(days, getFineRate(user.getMembershipType()));

                            totalFines += fine;
                            break;
                        }
                    }
                }
            }
        }

        return new double[]{overdueBooks, totalFines};
    }

    private int[] userStatistics(){
        User[] users = userRepo.getAllUsers();
        int counter = 0;
        int activeUser = 0;
        int inActiveUser = 0;

        for(User user : users){
            if(user != null){
                counter++;
                if (user.getStatus() == MemberStatus.ACTIVE) activeUser++;
                if(user.getStatus() != MemberStatus.ACTIVE) inActiveUser++;
            }
        }

        return new int[]{counter, activeUser, inActiveUser};
    }

    private int[] bookStatistics(){
        Book[] books = bookrepo.getAllBooks();
        int counter = 0;
        int availableBookCount = 0;
        int borrowedBookCount = 0;

        for(Book book : books){
            if(book != null){
                counter++;
                if (book.getBookStatus() == BookStatus.AVAILABLE) availableBookCount++;
                if(book.getBookStatus() == BookStatus.BORROWED) borrowedBookCount++;
            }
        }
        return new int[]{counter, availableBookCount, borrowedBookCount};
    }

    private void updateSuspensionStatus(User user){
        if(user.getOutstandingFine() >= SUSPENSION_FINE_THRESHOLD){
            user.setStatus(MemberStatus.SUSPENDED);
        }else if (user.getStatus() == MemberStatus.SUSPENDED){
            user.setStatus(MemberStatus.ACTIVE);
        }
    }
}