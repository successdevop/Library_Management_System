package com.library.interfaces;

import com.library.models.Book;

public interface SearchAble{
    Book[] searchBook(String title);
    Book[] searchBook(String title, String author);
    Book[] searchBook(String title, String author, String category);
}