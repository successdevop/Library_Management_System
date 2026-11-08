package com.library.database;

import com.library.app_files.Config;
import com.library.enums.BookStatus;
import com.library.enums.BorrowStatus;
import com.library.enums.MemberStatus;
import com.library.enums.MembershipType;
import com.library.models.Book;
import com.library.models.BorrowRecord;
import com.library.models.Users;
import com.library.utils.LibraryUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Arrays;


public class Database{
    private static Database instance = null;
    private static Book[] books = null;
    private static Users[] users = null;
    private static BorrowRecord[] borrowRecords = null;

    private Database(){
        books = new Book[Config.MAX_BOOKS];
        users = new Users[Config.MAX_USERS];
        borrowRecords = new BorrowRecord[Config.MAX_BORROW_RECORDS];
    }

    public static Book[] getBooksDatabase(){
        return books;
    }

    public static Users[] getUsersDatabase(){
        return users;
    }

    public static BorrowRecord[] getBorrowRecordDatabase(){return borrowRecords;}

    public static void appendUserToFile(String user_file_storage, Users user) {

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(user_file_storage, true))){
            String line = LibraryUtils.generateUserStringValue(user);

            bw.write(line);
            bw.newLine();

        }catch (IOException ex){
            System.err.println("Appending user to file: "+ex.getMessage());
        }
    }

    public static void appendBookToFile(String book_file_storage, Book book){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(book_file_storage, true))){
            String line = LibraryUtils.generateBookStringValue(book);

            bw.write(line);
            bw.newLine();

        }catch (IOException ex){
            System.err.println("Appending book to file: "+ex.getMessage());
        }
    }

    public static void appendBorrowRecordToFile(String storage_file, BorrowRecord record) {

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(storage_file, true))){

            String line = LibraryUtils.generateBorrowRecordStringValue(record);
            writer.write(line);
            writer.newLine();

        }catch(IOException ex){
            System.out.println("Appending borrowRecord to file: "+ex.getMessage());
        }
    }

    public static void updateBorrowRecordFile(String storage_file, BorrowRecord record, String newValues){
        Path temp = Paths.get("borrow_record_temp.txt");
        Path originalFile = Paths.get(storage_file);

        try(BufferedReader reader = Files.newBufferedReader(originalFile);
        BufferedWriter writer = Files.newBufferedWriter(temp)){
            String line;

            while((line = reader.readLine()) != null){
                String[] values = line.split("\\|");

                if(values.length > 0 && values[0].strip().equals(record.getTransactionId())){
                    writer.write(newValues);
                }else{
                    writer.write(line);
                }

                writer.newLine();
            }
        }catch (IOException ex){
            System.out.println("Updating BorrowRecord File: "+ex.getMessage());
        }

        try{
            Files.move(temp, originalFile, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException ex){
            System.out.println("swaping BorrowRecord File: "+ex.getMessage());
        }
    }

    public static void updateUserFile(String storage_file, Users user, String newValues){
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
            System.out.println("Updating User File: "+ex.getMessage());
        }

        try{
            Files.move(tempFile, originalFile, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex){
            System.out.println("Swapping user file: "+ex.getMessage());
        }
    }

    public static void updateBookFile(String storage_file, Book book, String newValues){
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
            System.err.println("Updating Book File: "+ex.getMessage());
        }

        try{
            Files.move(tempFile, originalFile, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex){
            System.err.println("swapping book file: "+ex.getMessage());
        }
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
            System.err.println("Deleting User from File: "+ex.getMessage());
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
            System.err.println("Deleting Book from File: "+ex.getMessage());
        }

        try{
            Files.move(temp, originalFile, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException ex){
            System.err.println("swapping book file: "+ex.getMessage());
        }
    }

    public static void deleteFromBorrowRecordFile(String storage_file, String id){
        Path temp = Paths.get("borrow_record_temp.txt");
        Path originalFile = Paths.get(storage_file);

        try(BufferedReader reader = Files.newBufferedReader(originalFile);
            BufferedWriter writer = Files.newBufferedWriter(temp)){
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
            System.out.println("Deleting BorrowRecord from File: "+ex.getMessage());
        }

        try{
            Files.move(temp, originalFile, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException ex){
            System.out.println("swaping BorrowRecord File: "+ex.getMessage());
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
        loadBorrowRecordsFromFile(storage_files[2]);
    }

    private static void loadBooksFromFile(String book_file_storage){
        int count = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(book_file_storage))){
            String line;
            while((line = br.readLine()) != null && count < books.length){
                String[] values = line.split("\\|", -1);
                if (values.length != 7) continue;

                String id = values[0].trim();
                String title = values[1].trim();
                String author = values[2].trim();
                String category = values[3].trim();
                String isbn = values[4].trim();
                short publication_year = Short.parseShort(values[5].trim());
                BookStatus status = BookStatus.valueOf(values[6].trim());

                books[count++] = new Book(id,title, author, category, isbn, publication_year, status);
            }
            System.out.println(count+" books loaded");
        } catch (IOException | NumberFormatException ex){
            System.err.println("Error loading books to array: "+ex.getMessage());
        }
    }

    private static void loadUsersFromFile(String user_file_storage){
        int count = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(user_file_storage))){
            String line;
            while((line = br.readLine()) != null && count < users.length){
                String[] values = line.split("\\|", -1);
                if (values.length != 10) continue;

                String id = values[0].trim();
                String firstName = values[1].trim();
                String lastName = values[2].trim();
                String email = values[3].trim();
                String phoneNo = values[4].trim();
                LocalDate dateJoined = LocalDate.parse((values[5].trim()));
                MemberStatus status = MemberStatus.valueOf(values[6].trim());
                MembershipType memberType = MembershipType.valueOf(values[7].trim());
                String password = values[8].trim();
                double outstandingFine = Double.parseDouble(values[9].trim());

                users[count++] = new Users(id, firstName, lastName, email, phoneNo, dateJoined, status,
                        memberType, password, outstandingFine);
            }
            System.out.println(count+" users loaded");
        } catch (IOException | NumberFormatException ex){
            System.err.println("Error loading Users to array: "+ex.getMessage());
        }
    }

    private static void loadBorrowRecordsFromFile(String storage_file){
        int count = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(storage_file))){
            String line;

            while((line = reader.readLine()) != null && count < borrowRecords.length){
                String[] values = line.split("\\|", -1);
                if (values.length != 7) continue;

                String t_id = values[0].trim();
                String b_id = values[1].trim();
                String u_id = values[2].trim();
                LocalDate b_date = LocalDate.parse(values[3].trim());
                LocalDate d_date = LocalDate.parse(values[4].trim());
                LocalDate r_date = LocalDate.parse(values[5].trim());
                BorrowStatus status = BorrowStatus.valueOf(values[6].trim());

                borrowRecords[count++] = new BorrowRecord(t_id, b_id, u_id, b_date, d_date,
                        r_date, status);
            }

            System.out.println(count+" BorrowRecords loaded");
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}