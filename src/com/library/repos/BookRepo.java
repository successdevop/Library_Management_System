package com.library.repos;

import com.library.app_files.Config;
import com.library.models.Book;

public class BookRepo{
    private final Book[] books;
    private final String book_file;

    public BookRepo(String book_file){
        this.book_file = book_file;
        this.books = new Book[Config.MAX_BOOKS];
    }

    public Book saveBooks(Book book){
        return book;
    }
}