package com.library.database;

import com.library.app_files.Config;
import com.library.enums.BookStatus;
import com.library.enums.MemberStatus;
import com.library.enums.MembershipType;
import com.library.models.Book;
import com.library.models.Users;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database{
    private static Database instance = null;
    private static Book[] books = null;
    private static Users[] users = null;

    private Database(){
        books = new Book[Config.MAX_BOOKS];
        users = new Users[Config.MAX_USERS];
    }

    public static Book[] getBooksDatabase(){
        return books;
    }

    public static Users[] getUsersDatabase(){
        return users;
    }

    public static synchronized Database getInstance(String[] files){
        if (instance == null){
            instance = new Database();
            loadDatabase(files);
        }
        return instance;
    }

    private static void loadDatabase(String[] storage_files){
        loadUsersFromFile(storage_files[0]);
        loadBooksFromFile(storage_files[1]);
    }

    private static Book[] loadBooksFromFile(String book_file_storage){
        Arrays.fill(books, null);
        List<Book> bks = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(book_file_storage))){
            String line;
            while((line = br.readLine()) != null){
                String[] values = line.split("\\|");
                if (values.length >= 3){
                    String id = values[0].trim();
                    String title = values[1].trim();
                    String author = values[2].trim();
                    String category = values[3].trim();
                    String isbn = values[4].trim();
                    short publication_year = Short.parseShort(values[5].trim());
                    BookStatus status = BookStatus.valueOf(values[6].trim());

                    bks.add(new Book(title, author, category, publication_year));
                }
            }
            System.out.println("Books loaded");
        } catch (IOException | NumberFormatException ex){
            System.err.println("Error loading books to array: "+ex.getMessage());
        }

        return bks.toArray(books);
    }

    private static Users[] loadUsersFromFile(String user_file_storage){
        Arrays.fill(users, null);
        List<Users> usr = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(user_file_storage))){
            String line;
            while((line = br.readLine()) != null){
                String[] values = line.split("\\|");
                String id = values[0].trim();
                String firstName = values[1].trim();
                String lastName = values[2].trim();
                String email = values[3].trim();
                String phoneNo = values[4].trim();
                LocalDate dateJoined = LocalDate.parse((values[5].trim()));
                MemberStatus status = MemberStatus.valueOf(values[6].trim());
                MembershipType memberType = MembershipType.valueOf(values[7].trim());
                String password = values[8].trim();

                Users user = new Users(firstName, lastName, email, phoneNo, memberType, password);
                user.setId(id);
                user.setDateJoined(dateJoined);
                user.setStatus(status);
                usr.add(user);
            }
            System.out.println("Users loaded");
        } catch (IOException | NumberFormatException ex){
            System.err.println("Error loading Users to array: "+ex.getMessage());
        }

        return usr.toArray(users);
    }

    public static Users appendUserToFile(String user_file_storage, Users user) {

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(user_file_storage, true))){
            String id = user.getId();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String email = user.getEmail();
            String phoneNo = user.getPhoneNo();
            String dateJoined = String.valueOf(user.getDateJoined());
            String status = user.getStatus().toString();
            String membershipType = user.getMembershipType().toString();
            String password = user.getPassword();

            String line = String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s",
                    id, firstName, lastName, email, phoneNo, dateJoined, status, membershipType, password);

            bw.write(line);
            bw.newLine();

            return user;
        }catch (IOException ex){
            System.err.println("Error writing User to file: "+ex.getMessage());
        }

        return null;
    }
}