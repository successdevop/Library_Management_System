package com.library.repos;

import com.library.database.Database;
import com.library.models.Book;

import static com.library.app_files.Config.BOOKS_STORAGE_FILE;


public class BookRepo{
    private final Book[] books = Database.getBooksDatabase();

    public Book addBook(Book book){
        Book bookResult = Database.appendBookToFile(BOOKS_STORAGE_FILE, book);

        if(bookResult == null)
            return null;

        for(int i = 0; i < books.length; i++){
            if (books[i] == null){
                books[i] = bookResult;
                break;
            }
        }

        return bookResult;
    }

    public Book editBook(Book book, String bookNewValues){
        Book bookObj = Database.updateBookFile(BOOKS_STORAGE_FILE, book, bookNewValues);

        if (bookObj == null)
            return null;

        for (int i = 0; i < books.length; i++){
            if(books[i] != null && books[i].getBookId().equals(book.getBookId())){
                books[i] = bookObj;
                break;
            }
        }

        return bookObj;
    }

    public Book getABookByTitleAndAuthor(String title, String author){

        for (Book book : books){
            if (book != null && book.getTitle().equalsIgnoreCase(title) &&
                    book.getAuthor().equalsIgnoreCase(author))

                return book;
        }

        return null;
    }

    public Book getBookById(String id){
        for (Book book : books) {
            if (book != null && book.getBookId().equalsIgnoreCase(id)) {
                return book;
            }
        }

        return null;
    }

    public void deleteBook(String id){
        Database.deleteFromBookFile(BOOKS_STORAGE_FILE, id);

        for (Book book : books){
            if(book != null && book.getBookId().equals(id)){
                book = null;
                break;
            }
        }
    }

    public Book[] getAllBooks(){
        return books;
    }
}