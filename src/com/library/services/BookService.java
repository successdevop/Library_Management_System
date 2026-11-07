package com.library.services;

import com.library.enums.BookStatus;
import com.library.exceptions.BookAlreadyExistsException;
import com.library.exceptions.BookNotFoundException;
import com.library.models.Book;
import com.library.repos.BookRepo;

import java.util.Arrays;
import java.util.Objects;

import static com.library.utils.LibraryUtils.generateBookId;
import static com.library.utils.LibraryUtils.generateBookIsbnNumber;


public class BookService{
    private final BookRepo bookRepo;

    public BookService(){
        this.bookRepo = new BookRepo();
    }

    public Book addBook(String book_title, String author, String category, short publication_year){

        if (bookRepo.getABookByTitleAndAuthor(book_title, author) != null)
            throw new BookAlreadyExistsException("Book already exists");

        Book newBook = new Book(book_title, author, category, publication_year);
        newBook.setBookId(generateBookId());
        newBook.setBookISBN(generateBookIsbnNumber());
        newBook.setBookStatus(BookStatus.AVAILABLE);

        return bookRepo.addBook(newBook);
    }

    public void viewAllBooks(){
        Book[] books = bookRepo.getAllBooks();

        for (int i = 0; i < books.length; i++){
            if (books[i] == null)
                continue;

            System.out.println(i+1+". Book<(book_id :"+books[i].getBookId() +", book_title: "+books[i].getTitle()+", "
                    +"book_author: "+books[i].getAuthor()+", book_category: "+books[i].getCategory()+", book_status: "
                    +books[i].getBookStatus()+")>");
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

    public void deleteABook(String id){
        Book book = bookRepo.getBookById(id);
        if (book == null)
            throw new BookNotFoundException("Book not found");

        bookRepo.deleteBook(id);
    }

    public Book[] searchBook(String title){
        Book[] books = bookRepo.searchBook(title);
        boolean hasObject = Arrays.stream(books).anyMatch(Objects::nonNull);

        if(!hasObject)
            throw new BookNotFoundException("No books found");

        return books;
    }

    public Book[] searchBook(String title, String author){
        Book[] books = bookRepo.searchBook(title, author);
        boolean hasObject = Arrays.stream(books).anyMatch(Objects::nonNull);

        if(!hasObject)
            throw new BookNotFoundException("No books found");

        return books;
    }

    public Book[] searchBook(String title, String author, String category){
        Book[] books = bookRepo.searchBook(title, author, category);
        boolean hasObject = Arrays.stream(books).anyMatch(Objects::nonNull);

        if(!hasObject)
            throw new BookNotFoundException("No books found");

        return books;
    }
}