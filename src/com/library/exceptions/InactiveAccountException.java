package com.library.exceptions;

public class InactiveAccountException extends RuntimeException{
    private final String message;

    public InactiveAccountException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}