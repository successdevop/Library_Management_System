package com.library.api;

import com.library.api.auth.Auth;
import com.library.api.book.BookController;
import com.library.api.user.UserController;
import com.library.exceptions.*;
import com.library.models.Book;
import com.library.models.Users;
import com.library.utils.LibraryUtils;

import java.util.Objects;
import java.util.Scanner;

import static com.library.utils.AuthUtils.hashPassword;


public class Controller{
    private final Scanner scanner = new Scanner(System.in);
    private final Auth authController = new Auth();
    private final UserController userController = new UserController();
    private final BookController bookController = new BookController();

    public void displayMainMenu(){
        System.out.println("========================================");
        System.out.println("WELCOME TO THE UNIVERSITY LIBRARY SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Are you new here? Sign up.");
        System.out.println("2. Login");
        System.out.println("3. Forgot password");
    }

    public void displayPersonalMenu(String membershipType){
        if (membershipType.equalsIgnoreCase("staff")){
            System.out.println("1. Add Book.");
            System.out.println("2. View Book Details");
            System.out.println("3. View All Books");
            System.out.println("4. Update Books");
            System.out.println("5. Delete Books");
            System.out.println("6. Search Book");
            System.out.println("7. Borrow Book");
            System.out.println("8. Return Book");
            System.out.println("9. View Borrowing Records");
            System.out.println("10. View All Borrowing Records");
            System.out.println("11. View Personal Profile");
            System.out.println("12. View Member Profile");
            System.out.println("13. View All Members");
            System.out.println("14. Update Personal Profile");
            System.out.println("15. Update Member Profile");
            System.out.println("16. Delete Personal Profile");
            System.out.println("17. Delete Member Profile");
            System.out.println("18. View Library Statistics");
            System.out.println("0. Logout");
        }else{
            System.out.println("1. View Book Details");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. View Borrowing Records");
            System.out.println("7. View Personal Profile");
            System.out.println("8. Update Personal Profile");
            System.out.println("9. Delete Personal Profile");
            System.out.println("0. Logout");
        }
    }

    public void featuresCall(String membershipType, String id){

        if (membershipType.equalsIgnoreCase("staff")){
            System.out.print("\nSelect an option (0 - 18): ");

            switch(scanner.nextLine()){
                case "1":
                    addBook();
                    break;
                case "2":
                    viewBookDetails();
                    break;
                case "3":
                    viewAllBooks();
                    break;
                case "4":
                    updatedBook();
                    break;
                case "5":
                    deleteBook();
                    break;
                case "6":
                    searchBook();
                    break;
                case "7":
                    borrowBook();
                    break;
                case "8":
                    returnBook();
                    break;
                case "9":
                    viewBorrowingRecords();
                    break;
                case "10":
                    viewAllBorrowingRecord();
                    break;
                case "11":
                    viewPersonalProfile(id);
                    break;
                case "12":
                    viewMemberProfile();
                    break;
                case "13":
                    viewAllMembers();
                    break;
                case "14":
                    updatePersonalProfile();
                    break;
                case "15":
                    updateMemberProfile();
                    break;
                case "16":
                    deletePersonalProfile(id);
                    break;
                case "17":
                    deleteMemberProfile();
                    break;
                case "18":
                    viewLibraryStatistics();
                    break;
                case "0":
                    logout();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + scanner.nextLine());
            }
        }else {
            System.out.print("\nSelect an option (0 - 9): ");

            switch(scanner.nextLine()){
                case "1":
                    viewBookDetails();
                    break;
                case "2":
                    viewAllBooks();
                    break;
                case "3":
                    searchBook();
                    break;
                case "4":
                    borrowBook();
                    break;
                case "5":
                    returnBook();
                    break;
                case "6":
                    viewBorrowingRecords();
                    break;
                case "7":
                    viewPersonalProfile(id);
                    break;
                case "8":
                    updatePersonalProfile();
                    break;
                case "9":
                    deletePersonalProfile(id);
                    break;
                case "0":
                    logout();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + scanner.nextLine());
            }
        }
    }

    public void personalDashBoard(Users user){
        if (user == null)
            return;

        String first_name = user.getFirstName();

        System.out.println("========================================");
        System.out.println(" "+first_name.toUpperCase()+" DASHBOARD ");
        System.out.println("========================================");

        System.out.println(LibraryUtils.greet()+" "+first_name+", Welcome to your dashboard\n");

        String membershipType = user.getMembershipType().toString().toLowerCase();
        String userId = user.getId();

        displayPersonalMenu(membershipType);

        featuresCall(membershipType, userId);
    }

    public void addBook(){
        String result;

        System.out.print("Enter book title: \n");
        String bookTitle = LibraryUtils.validateStringLength(scanner.nextLine());

        System.out.print("Enter book author: \n");
        String author = LibraryUtils.validateStringLength(scanner.nextLine());

        System.out.print("Enter book category: \n");
        String category = LibraryUtils.validateStringLength(scanner.nextLine());

        System.out.print("Enter book publication year: \n");
        short publicationYear = Short.parseShort(scanner.nextLine());

        Book book = bookController.addBook(bookTitle, author, category, publicationYear);
        if (book != null) {
            result = "Book added successfully";
        }else{
            result = "Adding new book failed, please try again later!!!";
        }

        System.out.println(result);
    }

    public void viewBookDetails(){
        System.out.print("Enter book title: \n");
        String title = LibraryUtils.validateStringLength(scanner.nextLine());

        System.out.print("Enter book author: \n");
        String bookAuthor = LibraryUtils.validateStringLength(scanner.nextLine());

        String viewBookInfo = bookController.viewBookDetails(title, bookAuthor);

        if (viewBookInfo != null){
            System.out.println("\n========================");
            System.out.println("   BOOK DETAILS         ");
            System.out.println("========================");

            System.out.println(viewBookInfo);
        }
    }

    public void viewAllBooks(){
        System.out.println("\n=================================");
        System.out.println("       LIBRARY BOOKS          ");
        System.out.println("=================================");

        bookController.viewAllBooks();
    }

    public void updatedBook(){
        System.out.print("Enter book title: \n");
        String title = LibraryUtils.validateStringLength(scanner.nextLine());

        System.out.print("Enter book author: \n");
        String bookAuthor = LibraryUtils.validateStringLength(scanner.nextLine());

        Book book = bookController.getABookByTitleAndAuthor(title, bookAuthor);

        System.out.println("What do you want to edit?: ");

        System.out.println("1. Book title");
        System.out.println("2. Author");
        System.out.println("3. Category");
        System.out.println("4. Publication year");
        System.out.println("5. Title and Author");
        System.out.println("6. Title and Category");
        System.out.println("7. Title and Publication Year");
        System.out.println("8. Author and Category");
        System.out.println("9. Author and Publication Year");
        System.out.println("10. Category and Publication Year");
        System.out.println("11. Title, Author and Category");
        System.out.println("12. Title, Author and Publication Year");
        System.out.println("13. Author, Category and Publication Year");
        System.out.println("14. Title, Author, Category and Publication Year");

        System.out.print("\nSelect an option (1 - 14): ");

        switch(scanner.nextLine()){
            case "1":
                System.out.print("Enter book title: \n");
                String bookTitle1 = LibraryUtils.validateStringLength(scanner.nextLine());
                book.setTitle(bookTitle1);
                break;
            case "2":
                System.out.print("Enter book author: \n");
                String author1 = LibraryUtils.validateStringLength(scanner.nextLine());
                book.setAuthor(author1);
                break;
            case "3":
                System.out.print("Enter book category: \n");
                String category1 = LibraryUtils.validateStringLength(scanner.nextLine());
                book.setCategory(category1);
                break;
            case "4":
                System.out.print("Enter book publication year: \n");
                short publicationYear1 = Short.parseShort(scanner.nextLine());
                book.setPublicationYear(publicationYear1);
                break;
            case "5":
                System.out.print("Enter book title: \n");
                String bookTitle2 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter book author: \n");
                String author2 = LibraryUtils.validateStringLength(scanner.nextLine());

                book.setTitle(bookTitle2);
                book.setAuthor(author2);
                break;
            case "6":
                System.out.print("Enter book title: \n");
                String bookTitle3 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter book category: \n");
                String category3 = LibraryUtils.validateStringLength(scanner.nextLine());

                book.setTitle(bookTitle3);
                book.setCategory(category3);
                break;
            case "7":
                System.out.print("Enter book title: \n");
                String bookTitle4 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter book publication year: \n");
                short publicationYear2 = Short.parseShort(scanner.nextLine());

                book.setTitle(bookTitle4);
                book.setPublicationYear(publicationYear2);
                break;
            case "8":
                System.out.print("Enter book author: \n");
                String author3 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter book category: \n");
                String category2 = LibraryUtils.validateStringLength(scanner.nextLine());

                book.setAuthor(author3);
                book.setCategory(category2);
                break;
            case "9":
                System.out.print("Enter book author: \n");
                String author4 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter book publication year: \n");
                short publicationYear3 = Short.parseShort(scanner.nextLine());

                book.setAuthor(author4);
                book.setPublicationYear(publicationYear3);
                break;
            case "10":
                System.out.print("Enter book category: \n");
                String category4 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter book publication year: \n");
                short publicationYear4 = Short.parseShort(scanner.nextLine());

                book.setCategory(category4);
                book.setPublicationYear(publicationYear4);
                break;
            case "11":
                System.out.print("Enter book title: \n");
                String bookTitle5 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter book author: \n");
                String author5 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter book category: \n");
                String category5 = LibraryUtils.validateStringLength(scanner.nextLine());

                book.setTitle(bookTitle5);
                book.setAuthor(author5);
                book.setCategory(category5);
                break;
            case "12":
                System.out.print("Enter book title: \n");
                String bookTitle6 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter book author: \n");
                String author6 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter book publication year: \n");
                short publicationYear5 = Short.parseShort(scanner.nextLine());

                book.setTitle(bookTitle6);
                book.setAuthor(author6);
                book.setPublicationYear(publicationYear5);
                break;
            case "13":
                System.out.print("Enter book author: \n");
                String author7 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter book category: \n");
                String category6 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter book publication year: \n");
                short publicationYear6 = Short.parseShort(scanner.nextLine());

                book.setAuthor(author7);
                book.setCategory(category6);
                book.setPublicationYear(publicationYear6);
                break;
            case "14":
                System.out.print("Enter book title: \n");
                String bookTitle = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter book author: \n");
                String author = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter book category: \n");
                String category = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter book publication year: \n");
                short publicationYear = Short.parseShort(scanner.nextLine());

                book.setTitle(bookTitle);
                book.setAuthor(author);
                book.setCategory(category);
                book.setPublicationYear(publicationYear);
                break;
            default:
                System.out.println("Unexpected value: " + scanner.nextLine());
        }

        String values = LibraryUtils.generateBookStringValue(book);
        Book bookObj = bookController.editBook(book, values);

        if (bookObj != null){
            System.out.println("\nBook update successful\n");
        }else{
            System.out.println("\nERROR: Profile update failed\n");
        }
    }

    public void deleteBook(){
        System.out.println("Enter book id: ");
        String bookId = scanner.nextLine();

        System.out.println("Are you sure you want to delete book? Y or N: ");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("Y")){
            bookController.deleteBook(bookId);
        }else{
            System.out.println("Book delete cancelled!!");
        }
    }

    public void searchBook(){
        System.out.println("========================================");
        System.out.println("    SEARCH BOOK   ");
        System.out.println("========================================");

        System.out.println("1. Search by Title");
        System.out.println("1. Search by Title and Author");
        System.out.println("1. Search by Title and Author, category");

        System.out.println();

        System.out.print("Enter choice: ");
        switch(scanner.nextLine()){
            case "1":
                System.out.print("Enter book title: ");
                Book[] books = bookController.searchBook(scanner.nextLine());
                for (Book book : books){
                    if (book != null)
                        System.out.println(book);
                }
                break;

            case "2":
                System.out.print("Enter book title: ");
                String bookTitle = scanner.nextLine();

                System.out.print("Enter book author: ");
                String bookAuthor = scanner.nextLine();

                Book[] books1 = bookController.searchBook(bookTitle, bookAuthor);
                for (Book book : books1){
                    System.out.println(book);
                }
                break;

            case "3":
                System.out.print("Enter book title: ");
                String bookTitle1 = scanner.nextLine();

                System.out.print("Enter book author: ");
                String bookAuthor1 = scanner.nextLine();

                System.out.print("Enter book category: ");
                String bookCategory = scanner.nextLine();

                Book[] books2 = bookController.searchBook(bookTitle1, bookAuthor1, bookCategory);
                for (Book book : books2){
                    System.out.println(book);
                }
                break;

            default:
                System.out.println("Invalid option: "+scanner.nextLine());
        }
    }

    public void borrowBook(){
        System.out.print("Enter book title: ");
        String title = LibraryUtils.validateStringLength(scanner.nextLine());

        System.out.println("Enter book author: ");
        String author = LibraryUtils.validateStringLength(scanner.nextLine());


    }

    public void returnBook(){}

    public void viewBorrowingRecords(){}

    public void viewAllBorrowingRecord(){}

    public void viewPersonalProfile(String id){
        String viewProfileInfo = userController.viewUserProfile(id);

        if (viewProfileInfo != null){
            System.out.println("\n========================");
            System.out.println("   USER PROFILE         ");
            System.out.println("========================");

            System.out.println(viewProfileInfo);
        }
    }

    public void viewMemberProfile(){
        System.out.print("Enter member Id: ");
        String viewMemberProfileInfo = userController.viewUserProfile(scanner.nextLine());

        if (viewMemberProfileInfo != null){
            System.out.println("\n========================");
            System.out.println("   MEMBER PROFILE         ");
            System.out.println("========================");

            System.out.println(viewMemberProfileInfo);
        }
    }

    public void viewAllMembers(){
        System.out.println("\n=================================");
        System.out.println("        LIBRARY MEMBERS          ");
        System.out.println("=================================");

        userController.viewAllUsers();
    }

    public void updatePersonalProfile(){
        Users user = authController.getMe();

        System.out.println("========================================");
        System.out.println(" "+user.getFirstName()+" EDIT DASHBOARD ");
        System.out.println("========================================");

        System.out.println("What do you want to edit?: ");

        System.out.println("1. FirstName");
        System.out.println("2. LastName");
        System.out.println("3. Email");
        System.out.println("4. Phone Number");
        System.out.println("5. FirstName and LastName");
        System.out.println("6. FirstName and Email");
        System.out.println("7. FirstName and Phone Number");
        System.out.println("8. LastName and Email");
        System.out.println("9. LastName and Phone Number");
        System.out.println("10. Email and Phone Number");
        System.out.println("11. FirstName, LastName and Email");
        System.out.println("12. FirstName, LastName and Phone Number");
        System.out.println("13. LastName, Email and Phone Number");
        System.out.println("14. FirstName, LastName, Email and Phone Number");

        System.out.print("\nSelect an option (1 - 14): ");

        switch(scanner.nextLine()){
            case "1":
                System.out.print("Enter firstName (minimum 3 characters): ");
                String firstName = LibraryUtils.validateStringLength(scanner.nextLine());
                user.setFirstName(firstName);
                break;
            case "2":
                System.out.print("Enter lastName (minimum 3 characters): ");
                String lastName = LibraryUtils.validateStringLength(scanner.nextLine());
                user.setLastName(lastName);
                break;
            case "3":
                System.out.print("Enter a valid email: ");
                String email = LibraryUtils.validateEmail(scanner.nextLine().toLowerCase());
                user.setEmail(email);
                break;
            case "4":
                System.out.print("Enter a valid phone number: ");
                String phoneNo = LibraryUtils.validatePhoneNumber(scanner.nextLine());
                user.setPhoneNo(phoneNo);
                break;
            case "5":
                System.out.print("Enter firstName (minimum 3 characters): ");
                String firstName1 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter lastName (minimum 3 characters): ");
                String lastName1 = LibraryUtils.validateStringLength(scanner.nextLine());

                user.setFirstName(firstName1);
                user.setLastName(lastName1);
                break;
            case "6":
                System.out.print("Enter firstName (minimum 3 characters): ");
                String firstName2 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter a valid email: ");
                String email2 = LibraryUtils.validateEmail(scanner.nextLine().toLowerCase());

                user.setFirstName(firstName2);
                user.setEmail(email2);
                break;
            case "7":
                System.out.print("Enter firstName (minimum 3 characters): ");
                String firstName3 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter a valid phone number: ");
                String phoneNo3 = LibraryUtils.validatePhoneNumber(scanner.nextLine());

                user.setFirstName(firstName3);
                user.setPhoneNo(phoneNo3);
                break;
            case "8":
                System.out.print("Enter lastName (minimum 3 characters): ");
                String lastName3 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter a valid email: ");
                String email3 = LibraryUtils.validateEmail(scanner.nextLine().toLowerCase());

                user.setLastName(lastName3);
                user.setEmail(email3);
                break;
            case "9":
                System.out.print("Enter lastName (minimum 3 characters): ");
                String lastName4 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter a valid phone number: ");
                String phoneNo4 = LibraryUtils.validatePhoneNumber(scanner.nextLine());

                user.setLastName(lastName4);
                user.setPhoneNo(phoneNo4);
                break;
            case "10":
                System.out.print("Enter a valid email: ");
                String email5 = LibraryUtils.validateEmail(scanner.nextLine().toLowerCase());

                System.out.print("Enter a valid phone number: ");
                String phoneNo5 = LibraryUtils.validatePhoneNumber(scanner.nextLine());

                user.setEmail(email5);
                user.setPhoneNo(phoneNo5);
                break;
            case "11":
                System.out.print("Enter firstName (minimum 3 characters): ");
                String firstName4 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter lastName (minimum 3 characters): ");
                String lastName5 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter a valid email: ");
                String email6 = LibraryUtils.validateEmail(scanner.nextLine().toLowerCase());

                user.setFirstName(firstName4);
                user.setLastName(lastName5);
                user.setEmail(email6);
                break;
            case "12":
                System.out.print("Enter firstName (minimum 3 characters): ");
                String firstName5 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter lastName (minimum 3 characters): ");
                String lastName6 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter a valid phone number: ");
                String phoneNo6 = LibraryUtils.validatePhoneNumber(scanner.nextLine());

                user.setFirstName(firstName5);
                user.setLastName(lastName6);
                user.setPhoneNo(phoneNo6);
                break;
            case "13":
                System.out.print("Enter lastName (minimum 3 characters): ");
                String lastName7 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter a valid email: ");
                String email4 = LibraryUtils.validateEmail(scanner.nextLine().toLowerCase());

                System.out.print("Enter a valid phone number: ");
                String phoneNo2 = LibraryUtils.validatePhoneNumber(scanner.nextLine());

                user.setLastName(lastName7);
                user.setEmail(email4);
                user.setPhoneNo(phoneNo2);
                break;
            case "14":
                System.out.print("Enter firstName (minimum 3 characters): ");
                String firstName6 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter lastName (minimum 3 characters): ");
                String lastName2 = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter a valid email: ");
                String email1 = LibraryUtils.validateEmail(scanner.nextLine().toLowerCase());

                System.out.print("Enter a valid phone number: ");
                String phoneNo1 = LibraryUtils.validatePhoneNumber(scanner.nextLine());

                user.setFirstName(firstName6);
                user.setLastName(lastName2);
                user.setEmail(email1);
                user.setPhoneNo(phoneNo1);
                break;
            default:
                System.out.println("Unexpected value: " + scanner.nextLine());
        }

        String values = LibraryUtils.generateUserStringValue(user);
        userController.updateUserProfile(user, values);

        System.out.println("Profile update successful");
    }

    public void updateMemberProfile(){}

    public void deletePersonalProfile(String id){
        System.out.println("Are you sure you want to delete your profile? Y or N: ");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("Y")){
            userController.deleteUserProfile(id);
        }else{
            System.out.println("Profile delete cancelled!!");
        }
    }

    public void deleteMemberProfile(){
        System.out.print("Enter member Id: ");
        userController.deleteUserProfile(scanner.nextLine());
    }

    public void viewLibraryStatistics(){}

    public void logout(){
        authController.logout();
    }

    public void registerMember(){
        System.out.println("========================================");
        System.out.println("        REGISTRATION DASHBOARD          ");
        System.out.println("========================================");

        String firstName = "";
        String lastName = "";
        String email = "";
        String phoneNo = "";
        String membershipType = "";
        String password = "";

        boolean regFlag = false;

        while(!regFlag){
            try{

                System.out.print("Enter firstName (minimum 3 characters): ");
                firstName = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter lastName (minimum 3 characters): ");
                lastName = LibraryUtils.validateStringLength(scanner.nextLine());

                System.out.print("Enter a valid email: ");
                email = LibraryUtils.validateEmail(scanner.nextLine());

                System.out.print("Enter a valid phone number: ");
                phoneNo = LibraryUtils.validatePhoneNumber(scanner.nextLine());

                System.out.print("Enter membership type i.e student, general or staff: ");
                membershipType = LibraryUtils.validateMembershipType(scanner.nextLine());

                System.out.print("Choose a password, make sure it is at least 8 characters long and contains at least " +
                        "one number, capital letter,lower letter and special character e.g /,@,#,?,$,& e.t.c: \n");
                password = LibraryUtils.validatePassword(scanner.nextLine());

                regFlag = true;

            } catch(InvalidNameLengthException | InvalidEmailException | InvalidPhoneNumberException |
                    InvalidMembershipTypeException | InvalidPasswordException ex){
                System.err.println("❌ Error: \n" + ex.getMessage());
                System.out.println("Please try again.\n");
            }
        }

        // enforce lower case
        email = email.toLowerCase();

        boolean isSuccessful = authController.signUpAccount(firstName, lastName, email, phoneNo, membershipType, password);

        if (isSuccessful){
            System.out.println("\nCongratulations "+firstName+", your registration is successful\n");
        }else{
            System.out.println("\nUser registration failed, please try again later\n");
        }
    }

    public void loginMember(){
        System.out.println("========================================");
        System.out.println("            LOGIN DASHBOARD             ");
        System.out.println("========================================");

        String email;
        String password;

        System.out.print("Please enter your email: ");
        email = LibraryUtils.validateEmail(scanner.nextLine());

        System.out.print("Please enter your password: ");
        password = LibraryUtils.validatePassword(scanner.nextLine());

        // enforce lower case
        email = email.toLowerCase();
        Users user = authController.signInAccount(email, password);

        if (user == null){
            System.out.println("Login failed");
        }else{
            System.out.println("Login successful");
            clearConsole();
            personalDashBoard(user);
        }
    }

    public void forgotPassword(){
        System.out.println("========================================");
        System.out.println("          FORGOT PASSWORD             ");
        System.out.println("========================================");

        String email;
        String phoneNo;
        String password;
        String confirmPassword;

        System.out.print("Please enter your email: ");
        email = LibraryUtils.validateEmail(scanner.nextLine());

        System.out.print("Please enter your phone number: ");
        phoneNo = LibraryUtils.validatePhoneNumber(scanner.nextLine());

        // enforce lower case
        email = email.toLowerCase();

        Users user = authController.forgotPassword(email, phoneNo);

        if (user != null){
            System.out.print("Choose a new password, make sure it is at least 8 characters long: ");
            password = LibraryUtils.validatePassword(scanner.nextLine());

            System.out.print("Confirm password: " );
            confirmPassword = LibraryUtils.validatePassword(scanner.nextLine());

            if (!password.equals(confirmPassword))
                throw new InvalidCredentialException("Password mismatch, check your passwords");

            String hashedPassword = hashPassword(password);
            user.setPassword(hashedPassword);

            String newValues = LibraryUtils.generateUserStringValue(user);

            String updated = userController.updateUserProfile(user, newValues);

            System.out.println(Objects.requireNonNullElse(updated, "Password change failed"));
        }
    }

    public static void clearConsole() {
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }
}