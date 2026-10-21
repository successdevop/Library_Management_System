package com.library.exceptions;

import java.util.regex.Pattern;

public class InvalidPhoneNumberException extends RuntimeException{
    private String phoneNo;

    public InvalidPhoneNumberException(String phoneNo){
        this.phoneNo = phoneNo;
    }

    @Override
    public String getMessage(){
        return this.phoneNo;
    }
}