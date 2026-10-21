package com.library.exceptions;

public class PhoneNumberAlreadyExistsException extends RuntimeException{

    @Override
    public String getMessage(){
        return "Phone number already exists";
    }
}