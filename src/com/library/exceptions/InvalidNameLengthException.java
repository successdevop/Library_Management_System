package com.library.exceptions;

public class InvalidNameLengthException extends RuntimeException{
    private final String msg;

    public InvalidNameLengthException(String message){
        this.msg = message;
    }

    @Override
    public String getMessage(){
        return this.msg;
    }
}