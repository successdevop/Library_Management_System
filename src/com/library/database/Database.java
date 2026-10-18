package com.library.database;

import com.library.app_files.Config;
import com.library.enums.BookStatus;
import com.library.enums.MemberStatus;
import com.library.enums.MembershipType;
import com.library.models.Book;
import com.library.models.Librarian;
import com.library.models.LibraryMember;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Database{
    private final Book[] books;
    private final Librarian[] librarian;
    private final LibraryMember[] members;

    public Database(String[] storage_files){
        this.books = new Book[Config.MAX_BOOKS];
        this.librarian = new Librarian[Config.MAX_STAFF];
        this.members = new LibraryMember[Config.MAX_MEMBERS];
        this.loadDatabase(storage_files);
    }

    public Book[] getBooksDatabase(){
        return this.books;
    }

    public Librarian[] getLibrariansDatabase(){
        return this.librarian;
    }

    public LibraryMember[] getLibraryMembersDatabase(){
        return this.members;
    }

    private void loadDatabase(String[] storage_files){
        this.loadBooks(storage_files[0]);
        this.loadLibrarians(storage_files[1]);
        this.loadLibraryMembers(storage_files[2]);
    }

    private Book[] loadBooks(String book_file_storage){
        Arrays.fill(this.books, null);
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

        return bks.toArray(this.books);
    }

    private Librarian[] loadLibrarians(String file_storage){
        Arrays.fill(this.librarian, null);
        List<Librarian> lib = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(file_storage))){
            String line;
            while((line = br.readLine()) != null){
                String[] values = line.split("\\|");
                String id = values[0].trim();
                String firstName = values[1].trim();
                String lastName = values[2].trim();
                String email = values[3].trim();
                String phoneNo = values[4].trim();
                Date dateJoined = Date.from(Instant.parse(values[5].trim()));
                MemberStatus status = MemberStatus.valueOf(values[6].trim());
                MembershipType membershipType = MembershipType.valueOf(values[7].trim());

                lib.add(new Librarian(firstName, lastName, email, phoneNo));
            }
            System.out.println("Library staff loaded");
        } catch (IOException | NumberFormatException ex){
            System.err.println("Error loading Librarians to array: "+ex.getMessage());
        }

        return lib.toArray(this.librarian);
    }

    private LibraryMember[] loadLibraryMembers(String file_storage){
        Arrays.fill(this.librarian, null);

        List<LibraryMember> mem = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(file_storage))){
            String line;
            while((line = br.readLine()) != null){
                String[] values = line.split("\\|");
                String id = values[0].trim();
                String firstName = values[1].trim();
                String lastName = values[2].trim();
                String email = values[3].trim();
                String phoneNo = values[4].trim();
                Date dateJoined = Date.from(Instant.parse(values[5].trim()));
                MemberStatus status = MemberStatus.valueOf(values[6].trim());
                MembershipType membershipType = MembershipType.valueOf(values[7].trim());

                mem.add(new LibraryMember(firstName, lastName, email, phoneNo, membershipType));
            }
            System.out.println("Library members loaded");
        } catch (IOException | NumberFormatException ex){
            System.err.println("Error loading Library members to array: "+ex.getMessage());
        }

        return mem.toArray(this.members);
    }
}