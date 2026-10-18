package com.library.models;

import com.library.enums.BookStatus;


public class Book{
    private short publication_year;
    private String id;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private BookStatus status;

    public Book(String title, String author, String category, short publication_year){
        this.id = getBookId();
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = getBookISBN();
        this.publication_year = publication_year;
        this.status = BookStatus.AVAILABLE;
    }

    public String getBookId(){
        return this.id;
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

    public void setBookStatus(BookStatus status){
        this.status = status;
    }

    public BookStatus getBookStatus(){
        return this.status;
    }

    @Override
    public String toString(){
        return "Book<(book_id :"+this.id +", book_title: "+this.title+", " +
                "book_author: "+this.author+", book_category: "+this.category+", book_status: "+this.status+")>";
    }
}