package com.library.exceptions;

public class BookNotFoundException extends RuntimeException{
    private final String message;

    public BookNotFoundException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}