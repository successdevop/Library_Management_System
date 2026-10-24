package com.library.exceptions;

public class InvalidPasswordException extends RuntimeException{
    private final String message;

    public InvalidPasswordException(String msg){
        this.message = msg;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}