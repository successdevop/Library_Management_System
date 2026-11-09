package com.library.services;

import com.library.enums.BookStatus;
import com.library.enums.BorrowStatus;
import com.library.exceptions.BookNotAvailableException;
import com.library.exceptions.BookNotFoundException;
import com.library.exceptions.BorrowLimitExceededException;
import com.library.interfaces.BorrowRecordInterface;
import com.library.models.Book;
import com.library.models.BorrowRecord;
import com.library.models.Users;
import com.library.repos.BookRepo;
import com.library.repos.BorrowRepo;
import com.library.repos.UserRepo;

import java.time.LocalDate;

import static com.library.app_files.Config.BORROW_PERIOD;
import static com.library.app_files.Config.getBorrowingLimit;
import static com.library.utils.LibraryUtils.generateBookStringValue;
import static com.library.utils.LibraryUtils.generateTransactionId;

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
        Users user = userRepo.getUserById(userId);

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


}