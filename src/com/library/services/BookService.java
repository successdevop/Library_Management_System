package com.library.services;

import com.library.enums.BookStatus;
import com.library.exceptions.BookAlreadyExistsException;
import com.library.models.Book;
import com.library.repos.BookRepo;

import static com.library.utils.LibraryUtils.generateBookId;
import static com.library.utils.LibraryUtils.generateBookIsbnNumber;

public class BookService{
    private final BookRepo bookRepo;

    public BookService(){
        this.bookRepo = new BookRepo();
    }

    public Book addBook(String book_title, String author, String category, short publication_year){

        boolean isExists = bookRepo.checkBook(book_title, author);
        if (isExists)
            throw new BookAlreadyExistsException("Book already exists");

        Book newBook = new Book(book_title, author, category, publication_year);
        newBook.setBookId(generateBookId());
        newBook.setBookISBN(generateBookIsbnNumber());
        newBook.setBookStatus(BookStatus.AVAILABLE);

        return bookRepo.addBook(newBook);
    }
}