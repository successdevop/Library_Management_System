package com.library.models;

import com.library.enums.BookEnums;


public class Book{
    private short publication_year;
    private String book_id;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private BookEnums status;

    public Book(String title, String author, String category, short publication_year){
        this.book_id = getBookId();
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = getBookISBN();
        this.publication_year = publication_year;
        this.status = BookEnums.AVAILABLE;
    }

    public String getBookId(){
        return this.book_id;
    }

    public String getBookISBN(){
        return this.isbn;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getAuthor(){
        return this.author;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return this.category;
    }

    public void setPublicationYear(short year){
        this.publication_year = year;
    }

    public short getPublicationYear(){
        return this.publication_year;
    }

    public void setBookStatus(BookEnums status){
        this.status = status;
    }

    public BookEnums getBookStatus(){
        return this.status;
    }

    @Override
    public String toString(){
        return "Book<(book_id :"+this.book_id+", book_title: "+this.title+", " +
                "book_author: "+this.author+", book_category: "+this.category+", book_status: "+this.status+")>";
    }
}