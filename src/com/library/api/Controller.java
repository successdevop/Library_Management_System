package com.library.api;

import com.library.api.auth.Auth;
import com.library.api.book.BookController;
import com.library.api.user.UserController;
import com.library.exceptions.*;
import com.library.models.Book;
import com.library.models.Users;
import com.library.utils.LibraryUtils;

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
            System.out.println("4. Search Book");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. View Borrowing Records");
            System.out.println("8. View All Borrowing Records");
            System.out.println("9. View Personal Profile");
            System.out.println("10. View Member Details");
            System.out.println("11. View All Members");
            System.out.println("12. View Library Statistics");
            System.out.println("0. Logout");
        }else{
            System.out.println("1. View Book Details");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. View Borrowing Records");
            System.out.println("7. View Profile");
            System.out.println("0. Logout");
        }
    }

    public void featuresCall(String membershipType, String id){

        if (membershipType.equalsIgnoreCase("staff")){
            System.out.print("\nSelect an option (0 - 12): ");

            switch(scanner.nextLine()){
                case "1":
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
                    break;
                case "2":
                    System.out.print("Enter book title: \n");
                    String title = LibraryUtils.validateStringLength(scanner.nextLine());

                    System.out.print("Enter book author: \n");
                    String bookAuthor = LibraryUtils.validateStringLength(scanner.nextLine());

                    System.out.println("\n========================");
                    System.out.println("   BOOK DETAILS         ");
                    System.out.println("========================");

                    System.out.println(bookController.viewBookDetails(title, bookAuthor));
                    break;
                case "3":
                    System.out.println("\n=================================");
                    System.out.println("       LIBRARY BOOKS          ");
                    System.out.println("=================================");

                    bookController.viewAllBooks();
                    break;
                case "4":
                    System.out.println("Search Book");
                    break;
                case "5":
                    System.out.println("Borrow Book");
                    break;
                case "6":
                    System.out.println("Return Book");
                    break;
                case "7":
                    System.out.println("View Borrowing Records");
                    break;
                case "8":
                    System.out.println("View All Borrowing Records");
                    break;
                case "9":
                    System.out.println("\n========================");
                    System.out.println("   USER PROFILE         ");
                    System.out.println("========================");

                    System.out.println(userController.viewUserProfile(id));
                    break;
                case "10":
                    System.out.print("Enter member Id: ");

                    System.out.println("\n========================");
                    System.out.println("   USER PROFILE         ");
                    System.out.println("========================");

                    System.out.println(userController.viewUserProfile(scanner.nextLine()));
                    break;
                case "11":
                    System.out.println("\n=================================");
                    System.out.println("        LIBRARY MEMBERS          ");
                    System.out.println("=================================");

                    userController.viewAllUsers();
                    break;
                case "12":
                    System.out.println("View Library Statistics");
                    break;
                case "0":
                    authController.logout();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + scanner.nextLine());
            }
        }else {
            System.out.print("\nSelect an option (0 - 7): ");

            switch(scanner.nextLine()){
                case "1":
                    System.out.print("Enter book title: \n");
                    String title = LibraryUtils.validateStringLength(scanner.nextLine());

                    System.out.print("Enter book author: \n");
                    String bookAuthor = LibraryUtils.validateStringLength(scanner.nextLine());

                    System.out.println("\n========================");
                    System.out.println("   BOOK DETAILS         ");
                    System.out.println("========================");

                    System.out.println(bookController.viewBookDetails(title, bookAuthor));
                    break;
                case "2":
                    System.out.println("\n=================================");
                    System.out.println("       LIBRARY BOOKS          ");
                    System.out.println("=================================");

                    bookController.viewAllBooks();
                    break;
                case "3":
                    System.out.println("Search Book");
                    break;
                case "4":
                    System.out.println("Borrow Book");
                    break;
                case "5":
                    System.out.println("Return Book");
                    break;
                case "6":
                    System.out.println("View Borrowing Records");
                    break;
                case "7":
                    System.out.println("\n========================");
                    System.out.println("   USER PROFILE         ");
                    System.out.println("========================");

                    System.out.println(userController.viewUserProfile(id));
                    break;
                case "0":
                    authController.logout();
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

        Users user = authController.signUpAccount(firstName, lastName, email, phoneNo, membershipType, password);

        if (user != null){
            System.out.println("\nCongratulations "+user.getFirstName()+", your registration is successful\n");

            try{
                Thread.sleep(3000);
                clearConsole();
            }catch(InterruptedException ex){
                System.out.println("ERROR: "+ex.getMessage());
            }

            personalDashBoard(user);
        }else{

            System.out.println("User registration failed, please try again later");
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
            System.out.print("Choose a new password, make sure it is at least 8 characters long" );
            password = LibraryUtils.validatePassword(scanner.nextLine());

            System.out.print("Confirm password: " );
            confirmPassword = LibraryUtils.validatePassword(scanner.nextLine());

            if (!password.equals(confirmPassword))
                throw new InvalidCredentialException("Password mismatch, check your passwords");

            String hashedPassword = hashPassword(password);
            user.setPassword(hashedPassword);
        }


    }

    public static void clearConsole() {
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }
}