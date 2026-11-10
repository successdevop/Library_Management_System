package com.library.exceptions;

public class RecordNotFoundException extends RuntimeException{
    private final String message;

    public RecordNotFoundException(String msg){
        this.message = msg;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}