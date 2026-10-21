package com.library.exceptions;

import java.util.regex.Pattern;

public class ValidatePhoneNumber extends RuntimeException{
    private static final String NIGERIA_PHONE_REGEX = "^((?:\\+?234)[789][01]\\d{8})$|^(0[789][01]\\d{8})$";
    private static final Pattern PATTERN = Pattern.compile(NIGERIA_PHONE_REGEX);

    private String phoneNo;

    public ValidatePhoneNumber(String phoneNo){
        validateNumber(phoneNo);
    }

    private void validateNumber(String num){
        if(num == null) return;
    }
}