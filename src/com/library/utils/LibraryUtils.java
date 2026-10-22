package com.library.utils;

import com.library.app_files.Config;
import com.library.database.Database;
import com.library.exceptions.InvalidEmailException;
import com.library.exceptions.InvalidMembershipTypeException;
import com.library.exceptions.InvalidNameLengthException;
import com.library.exceptions.InvalidPhoneNumberException;
import com.library.models.Users;

import java.util.Set;

import static com.library.app_files.Config.EMAIL_PATTERN;
import static com.library.app_files.Config.PHONE_NUMBER_PATTERN;


public class LibraryUtils{
    public static String generateUserId(){
        String id = "";

        Users[] users = Database.getUsersDatabase();
        if (users[0] == null){
            id = "U001";
            return id;
        }

        for (int i = 0; i < users.length; i++){
            if (users[i] == null){
                id = users[i - 1].getId();
                break;
            }
        }

        String newId = id.substring(1);
        int x = Integer.parseInt(newId) + 1;
        System.out.println(x);
        return "";
    }

    public static String validateStringLength(String name){
        if (name == null || name.strip().length() < Config.MIN_STRING_LENGTH){
            throw new InvalidNameLengthException("name must be at least 3 characters long");
        }
        return name;
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

        return email;
    }

    public static String validateMembershipType(String membershipType){
        if (!Set.of("student", "general", "staff").contains(membershipType)){
            throw new InvalidMembershipTypeException("Invalid membership type");
        }
        return membershipType;
    }
}