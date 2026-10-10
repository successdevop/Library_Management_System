package com.library.models;

import com.library.enums.BookEnums;

import java.util.Date;

public class Book{
    private String book_id;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private Date publication_year;
    private BookEnums status;

    public Book(String title, String author, String category, Date publication_year){
        this.title = title;
        this.author = author;
        this.category = category;
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

    public void setPublicationYear(Date year){
        this.publication_year = new Date();
    }

    public Date getPublicationYear(){
        return this.publication_year;
    }

    public void setBookStatus(BookEnums status){
        this.status = status;
    }

    public BookEnums getBookStatus(){
        return this.status;
    }
}