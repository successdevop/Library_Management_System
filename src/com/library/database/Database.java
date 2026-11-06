package com.library.database;

import com.library.app_files.Config;
import com.library.enums.BookStatus;
import com.library.enums.MemberStatus;
import com.library.enums.MembershipType;
import com.library.models.Book;
import com.library.models.Users;
import com.library.utils.LibraryUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    public static Users appendUserToFile(String user_file_storage, Users user) {

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(user_file_storage, true))){
            String line = LibraryUtils.generateUserStringValue(user);

            bw.write(line);
            bw.newLine();

            return user;
        }catch (IOException ex){
            System.err.println("Error writing User to file: "+ex.getMessage());
        }

        return null;
    }

    public static Book appendBookToFile(String book_file_storage, Book book){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(book_file_storage, true))){
            String line = LibraryUtils.generateBookStringValue(book);

            bw.write(line);
            bw.newLine();

            return book;
        }catch (IOException ex){
            System.err.println("Error adding book to file: "+ex.getMessage());
        }

        return null;
    }

    public static Users updateUserFile(String storage_file, Users user, String newValues){
        Path originalFile = Paths.get(storage_file);
        Path tempFile = Paths.get("users_temp_file.txt");

        try(
                BufferedReader br = Files.newBufferedReader(originalFile);
                BufferedWriter bw = Files.newBufferedWriter(tempFile)
        ){
            String line;

            while((line = br.readLine()) != null){
                String[] fields = line.split("\\|");

                if(fields.length > 0 && fields[0].strip().equals(user.getId())){
                    bw.write(newValues);
                }else{
                    bw.write(line);
                }

                bw.newLine();
            }

        }catch(IOException ex){
            System.out.println("updating user file: "+ex.getMessage());
        }

        try{
            Files.move(tempFile, originalFile, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);

            return user;
        } catch (IOException ex){
            System.out.println("Swapping user file: "+ex.getMessage());
        }

        return null;
    }

    public static Book updateBookFile(String storage_file, Book book, String newValues){
        Path tempFile = Paths.get("book_temp_file.txt");
        Path originalFile = Paths.get(storage_file);

        try(BufferedReader reader = Files.newBufferedReader(originalFile);
        BufferedWriter writer = Files.newBufferedWriter(tempFile)){
            String line;

            while((line = reader.readLine()) != null){
                String[] values = line.split("\\|");

                if (values.length > 0 && values[0].strip().equals(book.getBookId())){
                    writer.write(newValues);
                }else{
                    writer.write(line);
                }

                writer.newLine();
            }
        }catch(IOException ex){
            System.err.println("updating book file: "+ex.getMessage());
        }

        try{
            Files.move(tempFile, originalFile, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);

            return book;
        } catch (IOException ex){
            System.err.println("swapping book file: "+ex.getMessage());
        }

        return null;
    }

    public static void deleteFromUserFile(String storage_file, String id){
        Path tempFile = Paths.get("user_temp_file");
        Path originalFile = Paths.get(storage_file);

        try(BufferedReader reader = Files.newBufferedReader(originalFile);
        BufferedWriter writer = Files.newBufferedWriter(tempFile)){
            String line;
            while((line = reader.readLine()) != null){
                String[] values = line.split("\\|");

                if (values.length > 0 && values[0].strip().equals(id)){
                    continue;
                }else{
                    writer.write(line);
                }

                writer.newLine();
            }
        } catch (IOException ex){
            System.err.println("deleting: "+ex.getMessage());
        }

        try{
            Files.move(tempFile, originalFile, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex){
            System.err.println("swapping user file: "+ex.getMessage());
        }
    }

    public static void deleteFromBookFile(String storage_file, String id){
        Path temp = Paths.get("book_temp_file.txt");
        Path originalFile = Paths.get(storage_file);

        try(BufferedReader reader = Files.newBufferedReader(originalFile); BufferedWriter writer = Files.newBufferedWriter(temp)){
            String line;

            while((line = reader.readLine()) != null){
                String[] values = line.split("\\|");

                if(values.length > 0 && values[0].strip().equals(id)){
                    continue;
                }else{
                    writer.write(line);
                }

                writer.newLine();
            }
        }catch (IOException ex){
            System.err.println("deleting: "+ex.getMessage());
        }

        try{
            Files.move(temp, originalFile, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException ex){
            System.err.println("swapping book file: "+ex.getMessage());
        }
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

                    Book book = new Book(title, author, category, publication_year);
                    book.setBookId(id);
                    book.setBookISBN(isbn);
                    book.setBookStatus(status);
                    bks.add(book);
                }
            }
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
        } catch (IOException | NumberFormatException ex){
            System.err.println("Error loading Users to array: "+ex.getMessage());
        }

        return usr.toArray(users);
    }
}