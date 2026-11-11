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

    // GET A BOOK BY TITLE AND AUTHOR
    public Book getABookByTitleAndAuthor(String title, String author){
        return bookService.getABook(title, author);
    }

    // EDIT A BOOK
    public Book editBook(Book book, String newValues){
        return bookService.editBook(book, newValues);
    }

    // DELETE A BOOK
    public void deleteBook(String id){
        bookService.deleteABook(id);
    }

    // SEARCH BOOK BY TITLE
    public Book[] searchBook(String title){
        return bookService.searchBook(title);
    }

    // SEARCH BOOK BY TITLE AND AUTHOR
    public Book[] searchBook(String title, String author){
        return bookService.searchBook(title, author);
    }

    // SEARCH BOOK BY TITLE, AUTHOR AND CATEGORY
    public Book[] searchBook(String title, String author, String category){
        return bookService.searchBook(title, author, category);
    }
}