package com.library.app_files;

import java.util.regex.Pattern;

public class Config{
    public static final String ALGORITHM = "SHA-256";
    public static final int MIN_STRING_LENGTH = 3;
    public static final int BORROW_PERIOD = 14;
    public static final double FINE_PER_DAY = 500.00;

    private static final String NIGERIA_PHONE_REGEX = "^((?:\\+?234)[789][01]\\d{8})$|^(0[789][01]\\d{8})$";
    public static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(NIGERIA_PHONE_REGEX);

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,20}$";
    public static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    public static final int MAX_BOOKS = 500;
    public static final int MAX_USERS = 1000;

    public static final String USERS_STORAGE_FILE = "users_file.txt";
    public static final String BOOKS_STORAGE_FILE = "books_file.txt";

    public static final String[] DATABASE_FILE = {
            USERS_STORAGE_FILE, BOOKS_STORAGE_FILE
    };
}