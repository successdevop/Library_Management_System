package com.library.exceptions;

public class UserNotFoundException extends RuntimeException{
    private final String message;

    public UserNotFoundException(String msg){
        this.message = msg;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}