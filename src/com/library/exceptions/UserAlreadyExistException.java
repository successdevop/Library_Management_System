package com.library.exceptions;

public class UserAlreadyExistException extends RuntimeException{

    @Override
    public String getMessage(){
        return "User already exists";
    }
}