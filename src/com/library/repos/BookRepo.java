package com.library.repos;

import com.library.database.Database;
import com.library.models.Book;

import static com.library.app_files.Config.BOOKS_STORAGE_FILE;

public class BookRepo{
    private static final Book[] books = Database.getBooksDatabase();

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

    public boolean checkBook(String title, String author){
        for (Book value : books) {
            if (value.getTitle().equalsIgnoreCase(title) && value.getAuthor().equalsIgnoreCase(author))
                return true;
        }

        return false;
    }
}