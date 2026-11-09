package com.library.exceptions;

public class BorrowLimitExceededException extends RuntimeException{
    private final String message;

    public BorrowLimitExceededException(String msg){
        this.message = msg;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}