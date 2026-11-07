package com.library.utils;

import com.library.app_files.Config;
import com.library.database.Database;
import com.library.exceptions.*;
import com.library.models.Book;
import com.library.models.BorrowRecord;
import com.library.models.Users;

import java.time.LocalTime;
import java.util.Random;
import java.util.Set;

import static com.library.app_files.Config.*;


public class LibraryUtils{
    public static String generateUserId(){
        Users[] users = Database.getUsersDatabase();

        if (users == null)
            throw new IllegalStateException("User database not initialized");

        int maxId = 0;

        for(Users user : users){
            if (user == null)
                continue;

            String userId = user.getId();
            if(userId == null || !userId.matches("U\\d+")){
                throw new IllegalStateException("Invalid user ID found: "+userId);
            }

            int numericId = Integer.parseInt(userId.substring(1));
            if(numericId > maxId)
                maxId = numericId;
        }

        int id = maxId + 1;
        return String.format("U%03d", id);
    }

    public static String generateBookId(){
        Book[] books = Database.getBooksDatabase();

        if (books == null)
            throw new IllegalStateException("Book database not initialised");

        int bookMaxId = 0;

        for (Book book : books){
            if (book == null)
                continue;

            String bookId = book.getBookId();
            if (bookId == null || !bookId.matches("B\\d+"))
                throw new IllegalStateException("Invalid book ID found: "+bookId);

            int numericBookId = Integer.parseInt(bookId.substring(1));
            if(numericBookId > bookMaxId)
                bookMaxId = numericBookId;
        }

        int bookId = bookMaxId + 1;
        return String.format("B%03d", bookId);
    }

    public static String generateTransactionId(){
        BorrowRecord[] records = Database.getBorrowRecordDatabase();

        int maxId = 0;

        for(BorrowRecord record : records){
            if(record == null)
                continue;

            String recordId = record.getTransactionId();

            if(recordId == null || !recordId.matches("T\\d+"))
                throw new IllegalStateException("Invalid transaction ID: "+recordId);

            int intValueOfRecordId = Integer.parseInt(recordId.substring(1));

            if (intValueOfRecordId > maxId)
                maxId = intValueOfRecordId;
        }

        int id = maxId + 1;
        return String.format("T%03d", id);
    }

    public static String generateBookIsbnNumber(){
        StringBuilder sb = new StringBuilder("978");

        for (int i = 0; i < 12; i++){
            sb.append(new Random().nextInt(10));
        }

        // Calculate the checkSum
        int sum = 0;
        for (int i = 0; i < 12; i++){
            int digit = Character.getNumericValue(sb.charAt(i));
            sum += (digit % 2 == 0) ? digit : digit * 3;
        }

        int checkSum = (10 - (sum % 10) % 10);
        sb.append(checkSum);

        return sb.toString();
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

    public static String greet(){
        LocalTime now = LocalTime.now();
        int hour = now.getHour();

        String greeting;

        if (hour <= 12){
            greeting = "Good morning";
        } else if (hour <= 17){
            greeting = "Good afternoon";
        } else{
            greeting = "Good evening";
        }

        return greeting;
    }

    public static String generateUserStringValue(Users user){
        String id = user.getId();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String phoneNo = user.getPhoneNo();
        String dateJoined = String.valueOf(user.getDateJoined());
        String status = user.getStatus().toString();
        String membershipType = user.getMembershipType().toString();
        String password = user.getPassword();

        return String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s",
                id, firstName, lastName, email, phoneNo, dateJoined, status, membershipType, password);
    }

    public static String generateBookStringValue(Book book){
        String id = book.getBookId();
        String title = book.getTitle();
        String author = book.getAuthor();
        String category = book.getCategory();
        String isbn = book.getBookISBN();
        String publicationYear = String.valueOf(book.getPublicationYear());
        String status = book.getBookStatus().toString();

        return String.format("%s|%s|%s|%s|%s|%s|%s", id, title, author, category, isbn, publicationYear, status);
    }

    public static String generateBorrowRecordStringValue(BorrowRecord record){
        String transactionId = record.getTransactionId();
        String bookId = record.getBookId();
        String userId = record.getUserId();
        String borrowDate = String.valueOf(record.getBorrowDate());
        String dueDate = String.valueOf(record.getDueDate());
        String returnDate = String.valueOf(record.getReturnDate());
        String status = record.getBorrowStatus().toString();

        return String.format("%s|%s|%s|%s|%s|%s|%s", transactionId, bookId, userId, borrowDate, dueDate, returnDate, status);
    }
}