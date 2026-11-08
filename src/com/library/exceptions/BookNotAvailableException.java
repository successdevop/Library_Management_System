package com.library.exceptions;

public class BookNotAvailableException extends RuntimeException{
    private final String message;

    public BookNotAvailableException(String msg){
        this.message = msg;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}