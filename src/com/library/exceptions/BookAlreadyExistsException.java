package com.library.exceptions;

public class BookAlreadyExistsException extends RuntimeException{
    private final String message;

    public BookAlreadyExistsException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}