package com.library.repos;

import com.library.database.Database;
import com.library.models.Book;

import java.util.ArrayList;

import static com.library.app_files.Config.BOOKS_STORAGE_FILE;


public class BookRepo{
    private final Book[] books = Database.getBooksDatabase();

    public Book addBook(Book book){
        Database.appendBookToFile(BOOKS_STORAGE_FILE, book);

        for(int i = 0; i < books.length; i++){
            if (books[i] == null){
                books[i] = book;
                break;
            }
        }

        return book;
    }

    public Book editBook(Book book, String bookNewValues){
        Database.updateBookFile(BOOKS_STORAGE_FILE, book, bookNewValues);

        for (int i = 0; i < books.length; i++){
            if(books[i] != null && books[i].getBookId().equals(book.getBookId())){
                books[i] = book;
                break;
            }
        }

        return book;
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

        for (int i = 0; i < books.length; i++){
            if(books[i] != null && books[i].getBookId().equals(id)){
                books[i] = null;
                break;
            }
        }
    }

    public Book[] getAllBooks(){
        return books;
    }

    public Book[] searchBook(String title){
        return searchEngine(title, null, null);
    }

    public Book[] searchBook(String title, String author){
        return searchEngine(title, author, null);
    }

    public Book[] searchBook(String title, String author, String category){
        return searchEngine(title, author, category);
    }

    private Book[] searchEngine(String title, String author, String category){
        ArrayList<Book> bookArray = new ArrayList<>();

        for (Book book : books) {
            if (book == null) continue;

            if (title != null && book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                bookArray.add(book);
            } else if (title != null && author != null &&
                    (book.getTitle().toLowerCase().contains(title.toLowerCase()) ||
                            book.getAuthor().toLowerCase().contains(author.toLowerCase()))) {
                bookArray.add(book);
            } else if (title != null && author != null && category != null &&
                    (book.getTitle().toLowerCase().contains(title.toLowerCase()) ||
                            book.getAuthor().toLowerCase().contains(author.toLowerCase()) ||
                            book.getCategory().toLowerCase().contains(category.toLowerCase()))) {
                bookArray.add(book);
            }
        }

        return bookArray.toArray(new Book[0]);
    }
}