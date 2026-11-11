package com.library.services;

import com.library.enums.BookStatus;
import com.library.exceptions.BookAlreadyExistsException;
import com.library.exceptions.BookNotAvailableException;
import com.library.exceptions.BookNotFoundException;
import com.library.models.Book;
import com.library.repos.BookRepo;

import java.util.Arrays;
import java.util.Objects;

import static com.library.utils.MethodUtils.*;


public class BookService{
    private final BookRepo bookRepo;

    public BookService(){
        this.bookRepo = new BookRepo();
    }

    public Book addBook(String book_title, String author, String category, short publication_year){

        if (bookRepo.getABookByTitleAndAuthor(book_title, author) != null)
            throw new BookAlreadyExistsException("Book already exists");

        Book newBook = new Book(generateBookId(), book_title, author, category, generateBookIsbnNumber(),
                publication_year, BookStatus.AVAILABLE);

        return bookRepo.addBook(newBook);
    }

    public void viewAllBooks(){
        Book[] books = bookRepo.getAllBooks();

        for (Book book : books) {
            if (book == null)
                continue;

            System.out.println(book);
        }
    }

    public String viewBookDetails(String title, String author){
        Book book = bookRepo.getABookByTitleAndAuthor(title, author);

        if (book == null)
            throw new BookNotFoundException("Book not found");

        return (
                "Book_Id: "+book.getBookId()+"\n" +
                        "Title: "+book.getTitle()+"\n" +
                        "Author: "+book.getAuthor()+"\n" +
                        "Category: "+book.getCategory()+"\n" +
                        "ISBN: "+book.getBookISBN()+"\n" +
                        "Publication Year: "+book.getPublicationYear()+"\n" +
                        "Status: "+book.getBookStatus()+"\n"
                );
    }

    public Book editBook(Book book, String newValues){
        return bookRepo.editBook(book, newValues);
    }

    public Book getABook(String title, String author){
        Book book = bookRepo.getABookByTitleAndAuthor(title, author);
        if (book == null)
            throw new BookNotFoundException("Book not found");

        return book;
    }

    public Book getABookById(String id){
        Book book = bookRepo.getBookById(id);
        if (book == null)
            throw new BookNotFoundException("Book not found");

        return book;
    }

    public void deleteABook(String bookId){
        Book book = bookRepo.getBookById(bookId);
        if (book == null)
            throw new BookNotFoundException("Book not found");

        if (book.getBookStatus() == BookStatus.BORROWED){
            throw new BookNotAvailableException("Cannot delete a borrowed book");
        }

        if(checkBookAndRecordStatus(bookId))
            throw new RuntimeException("Cannot delete a borrowed book");

        bookRepo.deleteBook(bookId);
    }

    public Book[] searchBook(String title){
        Book[] books = bookRepo.searchBook(title);

        if(books.length == 0)
            throw new BookNotFoundException("No books found");

        return books;
    }

    public Book[] searchBook(String title, String author){
        Book[] books = bookRepo.searchBook(title, author);
        if(books.length == 0)
            throw new BookNotFoundException("No books found");

        return books;
    }

    public Book[] searchBook(String title, String author, String category){
        Book[] books = bookRepo.searchBook(title, author, category);
        if(books.length == 0)
            throw new BookNotFoundException("No books found");

        return books;
    }
}