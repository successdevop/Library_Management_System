package com.library.services;

import com.library.models.Book;
import com.library.repos.BookRepo;

public class BookService{
    private BookRepo book_repo;

    public BookService(BookRepo book_repo){
        this.book_repo = book_repo;
    }

    public Book addBook(String book_title, String author, String category, short publication_year){
        return new Book(book_title, author, category, publication_year);
    }
}