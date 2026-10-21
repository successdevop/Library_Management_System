package com.library.utils;

import com.library.app_files.Config;
import com.library.exceptions.InvalidNameLengthException;

public class LibraryUtils{
    public static String generateId(){
        return "";
    }

    public static String validateStringLength(String name){
        if (name == null || name.strip().length() < Config.MIN_STRING_LENGTH){
            throw new InvalidNameLengthException("name must be at least 3 characters long");
        }
        return name;
    }
}