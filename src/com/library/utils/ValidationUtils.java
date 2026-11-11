package com.library.utils;

import com.library.app_files.Config;
import com.library.exceptions.*;
import java.util.Set;

import static com.library.app_files.Config.*;
import static com.library.utils.InputHelper.readNonEmptyString;


public class ValidationUtils {
    public static String validateStringLength(String prompt){
        while(true){

            String name = readNonEmptyString(prompt);

            if (!(name.strip().length() < Config.MIN_STRING_LENGTH)){
                return name;
            }

            System.out.println("input must be at least 3 characters long");
        }
    }

    public static String validatePhoneNumber(String phoneNumber){
        // Remove common spaces, dashes, or parentheses before processing
        String cleaned = phoneNumber.replaceAll("[\\s\\-()]", "");

        boolean isMatches = PHONE_NUMBER_PATTERN.matcher(cleaned).matches();

        if(!isMatches){
            throw new InvalidPhoneNumberException("Invalid phone number");
        }

        return phoneNumber;
    }

    public static String validateEmail(String email){
        boolean isMatches = EMAIL_PATTERN.matcher(email).matches();
        if(!isMatches){
            throw new InvalidEmailException("Invalid email");
        }

        return email.toLowerCase();
    }

    public static String validatePassword(String password){

        boolean isMatches = PASSWORD_PATTERN.matcher(password).matches();
        if(!isMatches)
            throw new InvalidPasswordException("Invalid password");

        return password;
    }

    public static String validateMembershipType(String membershipType){
        if (!Set.of("student", "general", "staff").contains(membershipType)){
            throw new InvalidMembershipTypeException("Invalid membership type");
        }
        return membershipType;
    }

}