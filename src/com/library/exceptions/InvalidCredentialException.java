package com.library.exceptions;

public class InvalidCredentialException extends RuntimeException{
    private final String message;

    public InvalidCredentialException(String msg){
        this.message = msg;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}