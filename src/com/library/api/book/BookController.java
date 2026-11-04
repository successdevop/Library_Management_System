package com.library.api.book;

import com.library.models.Book;
import com.library.services.BookService;


public class BookController{
    private final BookService bookService;

    public BookController(){
        this.bookService = new BookService();
    }

    // ADD A NEW BOOK
    public Book addBook(String bookTitle, String author, String category, short publicationYear){
        return bookService.addBook(bookTitle, author, category, publicationYear);
    }

    // VIEW ALL BOOKS
    public void viewAllBooks(){
        bookService.viewAllBooks();
    }

    // VIEW A BOOK DETAIL
    public String viewBookDetails(String title, String author){
        return bookService.viewBookDetails(title, author);
    }
}