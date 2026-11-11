package com.library.utils;

import com.library.database.Database;
import com.library.enums.BorrowStatus;
import com.library.enums.MembershipType;
import com.library.models.Book;
import com.library.models.BorrowRecord;
import com.library.models.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class MethodUtils{
    private static final User[] users = Database.getUsersDatabase();
    private static final Book[] books = Database.getBooksDatabase();
    private static final BorrowRecord[] records = Database.getBorrowRecordDatabase();

    public static String generateUserId(){
        int maxId = 0;

        for(User user : users){
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

    private static String generateIsbnNumber(){
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

    public static String generateBookIsbnNumber(){

        while(true){
            String isbnNumber = generateIsbnNumber();
            boolean duplicate = false;

            for(Book book : books){
                if (book == null) continue;

                String isbn = book.getBookISBN();
                if(isbn != null && isbn.equals(isbnNumber)) {
                    duplicate = true;
                    break;
                }
            }

            if(!duplicate){
                return isbnNumber;
            }
        }
    }

    public static String generateUserStringValue(User user){
        String id = user.getId();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String phoneNo = user.getPhoneNo();
        String dateJoined = String.valueOf(user.getDateJoined());
        String status = user.getStatus().toString();
        String membershipType = user.getMembershipType().toString();
        String password = user.getPassword();
        String outstandingFine = String.valueOf(user.getOutstandingFine());

        return String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s",
                id, firstName, lastName, email, phoneNo, dateJoined, status, membershipType, password, outstandingFine);
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
        String returnDate = record.getReturnDate() == null ? "" : String.valueOf(record.getReturnDate());
        String status = record.getBorrowStatus().toString();

        return String.format("%s|%s|%s|%s|%s|%s|%s", transactionId, bookId, userId, borrowDate, dueDate, returnDate, status);
    }

    public static int calculateDaysLate(LocalDate dueDate, LocalDate returnDate){
        long totalDays = ChronoUnit.DAYS.between(dueDate, returnDate);
        return (int)totalDays;
    }

    public static double calculateFine(int days, double fineRate){
        return days * fineRate;
    }

    public static int getBorrowingLimit(MembershipType type){
        return switch (type) {
            case STAFF -> 10;
            case STUDENT -> 5;
            default -> 3;
        };
    }

    public static double getFineRate(MembershipType type){
        return switch(type){
            case STUDENT -> 300;
            case STAFF -> 500;
            default -> 700;
        };
    }

    public static int countUserActiveBorrowings(String id){
        int count = 0;

        for(BorrowRecord record : records){
            if(record != null && record.getUserId().equals(id) &&
                    record.getBorrowStatus().toString().equalsIgnoreCase("BORROWED")){
                count++;
            }
        }

        return count;
    }

    public static boolean checkBookAndRecordStatus(String id){
        for (BorrowRecord record : records) {
            if (record != null && record.getBookId().equalsIgnoreCase(id) &&
                    (record.getBorrowStatus() == BorrowStatus.BORROWED))
                return true;
        }

        return false;
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
}