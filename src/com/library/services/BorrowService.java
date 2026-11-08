package com.library.services;

import com.library.exceptions.BookNotAvailableException;
import com.library.exceptions.BookNotFoundException;
import com.library.interfaces.BorrowRecordInterface;
import com.library.models.Book;
import com.library.models.BorrowRecord;
import com.library.repos.BookRepo;
import com.library.repos.BorrowRepo;

public class BorrowService{
    private final BorrowRecordInterface borrowRecords;
    private final BookRepo bookrepo;

    public BorrowService(){
        this.borrowRecords = new BorrowRepo();
        this.bookrepo = new BookRepo();
    }

    public void borrowBook(String title, String author){
        Book book = bookrepo.getABookByTitleAndAuthor(title, author);
        if (book == null)
            throw new BookNotFoundException("Book not found");

        if (book.getBookStatus().toString().equalsIgnoreCase("available"))
            throw new BookNotAvailableException("Book not available");


    }
}