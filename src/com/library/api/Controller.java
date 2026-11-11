package com.library.api;

import com.library.api.auth.Auth;
import com.library.api.book.BookController;
import com.library.api.borrow_records.BorrowRecordController;
import com.library.api.user.UserController;
import com.library.enums.MembershipType;
import com.library.exceptions.*;
import com.library.models.Book;
import com.library.models.User;
import com.library.utils.MethodUtils;
import com.library.utils.ValidationUtils;

import java.util.Objects;

import static com.library.utils.AuthUtils.hashPassword;
import static com.library.utils.InputHelper.*;


public class Controller{
    private final Auth authController = new Auth();
    private final UserController userController = new UserController();
    private final BookController bookController = new BookController();
    private final BorrowRecordController borrowRecordController = new BorrowRecordController();

    public void displayMainMenu(){
        System.out.println("========================================");
        System.out.println("WELCOME TO THE UNIVERSITY LIBRARY SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Are you new here? Sign up.");
        System.out.println("2. Login");
        System.out.println("3. Forgot password");
    }

    public void displayPersonalMenu(){
        System.out.println("1. Add Book.");
        System.out.println("2. View Book Details");
        System.out.println("3. View All Books");
        System.out.println("4. Update Book");
        System.out.println("5. Delete Book");
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
        System.out.println("19. Pay Outstanding Fine");
        System.out.println("20. Delete Borrowing Record");
        System.out.println("0. Logout");
    }

    public void featuresCall(){
        int choice = readInt("\nSelect an option (0 - 19): ", 0, 20);

        switch(choice){
            case 1:
                addBook();
                break;
            case 2:
                viewBookDetails();
                break;
            case 3:
                viewAllBooks();
                break;
            case 4:
                updatedBook();
                break;
            case 5:
                deleteBook();
                break;
            case 6:
                searchBook();
                break;
            case 7:
                borrowBook();
                break;
            case 8:
                returnBook();
                break;
            case 9:
                viewBorrowingRecords();
                break;
            case 10:
                viewAllBorrowingRecord();
                break;
            case 11:
                viewPersonalProfile();
                break;
            case 12:
                viewMemberProfile();
                break;
            case 13:
                viewAllMembers();
                break;
            case 14:
                updatePersonalProfile();
                break;
            case 15:
                updateMemberProfile();
                break;
            case 16:
                deletePersonalProfile();
                break;
            case 17:
                deleteMemberProfile();
                break;
            case 18:
                viewLibraryStatistics();
                break;
            case 19:
                payFine();
                break;
            case 20:
                deleteBorrowRecords();
                break;
            case 0:
                logout();
                break;
            default:
                System.out.println("Unexpected value: " + choice);
        }
    }

    public void personalDashBoard(User user){
        String first_name = user.getFirstName().toUpperCase();

        System.out.println("========================================");
        System.out.println("    "+first_name+" DASHBOARD            ");
        System.out.println("========================================");

        System.out.println(MethodUtils.greet()+" "+first_name+", Welcome to your dashboard\n");

            displayPersonalMenu();
            featuresCall();
    }

    public void addBook(){
        String bookTitle = ValidationUtils.validateStringLength("Enter book title: ");

        String author = ValidationUtils.validateStringLength("Enter book author: ");

        String category = ValidationUtils.validateStringLength("Enter book category: ");

        System.out.print("Enter book publication year: \n");
        short publicationYear = (short)validateNumber();

        Book book = bookController.addBook(bookTitle, author, category, publicationYear);
        if (book != null) {
            System.out.println("Book added successfully");
        }else{
            System.out.println("Adding new book failed, please try again later!!!");
        }
    }

    public void viewBookDetails(){
        String title = ValidationUtils.validateStringLength("Enter book title: ");

        String bookAuthor = ValidationUtils.validateStringLength("Enter book author: ");

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
        String title = ValidationUtils.validateStringLength("Enter book title: ");

        String bookAuthor = ValidationUtils.validateStringLength("Enter book author: ");

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

        int choice = readInt("Select an option (1 - 14)",1,14);

        switch(choice){
            case 1:
                String bookTitle1 = ValidationUtils.validateStringLength("Enter book title: ");
                book.setTitle(bookTitle1);
                break;
            case 2:
                String author1 = ValidationUtils.validateStringLength("Enter book author: ");
                book.setAuthor(author1);
                break;
            case 3:
                String category1 = ValidationUtils.validateStringLength("Enter book category: ");
                book.setCategory(category1);
                break;
            case 4:
                System.out.print("Enter book publication year: \n");
                short publicationYear1 = (short)validateNumber();
                book.setPublicationYear(publicationYear1);
                break;
            case 5:
                String bookTitle2 = ValidationUtils.validateStringLength("Enter book title: ");

                String author2 = ValidationUtils.validateStringLength("Enter book author: ");

                book.setTitle(bookTitle2);
                book.setAuthor(author2);
                break;
            case 6:
                String bookTitle3 = ValidationUtils.validateStringLength("Enter book title: ");

                String category3 = ValidationUtils.validateStringLength("Enter book category: ");

                book.setTitle(bookTitle3);
                book.setCategory(category3);
                break;
            case 7:
                String bookTitle4 = ValidationUtils.validateStringLength("Enter book title: ");

                System.out.print("Enter book publication year: ");
                short publicationYear2 = (short) validateNumber();

                book.setTitle(bookTitle4);
                book.setPublicationYear(publicationYear2);
                break;
            case 8:
                String author3 = ValidationUtils.validateStringLength("Enter book author: ");

                String category2 = ValidationUtils.validateStringLength("Enter book category: ");

                book.setAuthor(author3);
                book.setCategory(category2);
                break;
            case 9:
                String author4 = ValidationUtils.validateStringLength("Enter book author: ");

                System.out.print("Enter book publication year: \n");
                short publicationYear3 = (short)validateNumber();

                book.setAuthor(author4);
                book.setPublicationYear(publicationYear3);
                break;
            case 10:
                String category4 = ValidationUtils.validateStringLength("Enter book category: ");

                System.out.print("Enter book publication year: \n");
                short publicationYear4 = (short) validateNumber();

                book.setCategory(category4);
                book.setPublicationYear(publicationYear4);
                break;
            case 11:
                String bookTitle5 = ValidationUtils.validateStringLength("Enter book title: ");

                String author5 = ValidationUtils.validateStringLength("Enter book author: ");

                String category5 = ValidationUtils.validateStringLength("Enter book category: ");

                book.setTitle(bookTitle5);
                book.setAuthor(author5);
                book.setCategory(category5);
                break;
            case 12:
                String bookTitle6 = ValidationUtils.validateStringLength("Enter book title: ");

                String author6 = ValidationUtils.validateStringLength("Enter book author: ");

                System.out.print("Enter book publication year: \n");
                short publicationYear5 = (short) validateNumber();

                book.setTitle(bookTitle6);
                book.setAuthor(author6);
                book.setPublicationYear(publicationYear5);
                break;
            case 13:
                String author7 = ValidationUtils.validateStringLength("Enter book author: ");

                String category6 = ValidationUtils.validateStringLength("Enter book category: ");

                System.out.print("Enter book publication year: \n");
                short publicationYear6 = (short) validateNumber();

                book.setAuthor(author7);
                book.setCategory(category6);
                book.setPublicationYear(publicationYear6);
                break;
            case 14:
                String bookTitle = ValidationUtils.validateStringLength("Enter book title: ");

                String author = ValidationUtils.validateStringLength("Enter book author: ");

                String category = ValidationUtils.validateStringLength("Enter book category: ");

                System.out.print("Enter book publication year: \n");
                short publicationYear = (short)validateNumber();

                book.setTitle(bookTitle);
                book.setAuthor(author);
                book.setCategory(category);
                book.setPublicationYear(publicationYear);
                break;
            default:
                System.out.println("Unexpected value: " + choice);
        }

        String values = MethodUtils.generateBookStringValue(book);
        Book bookObj = bookController.editBook(book, values);

        if (bookObj != null){
            System.out.println("\nBook update successful\n");
        }else{
            System.out.println("\nERROR: Profile update failed\n");
        }
    }

    public void deleteBook(){
        MembershipType membershipType = authController.getMe().getMembershipType();
        if(!(membershipType == MembershipType.STAFF))
            throw new RuntimeException("unauthorized, only staff members can delete book");

        String bookId = readNonEmptyString("Enter book id: ");

        String answer = readNonEmptyString("Are you sure you want to delete book? Y or N: ");

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
        System.out.println("2. Search by Title and Author");
        System.out.println("3. Search by Title and Author, category");

        int choice = readInt("Enter choice: ", 1, 3);

        switch(choice){
            case 1:
                Book[] books = bookController.searchBook(ValidationUtils.validateStringLength("Enter book title: "));
                for (Book book : books){
                    if (book != null)
                        System.out.println(book);
                }
                break;

            case 2:
                String bookTitle = ValidationUtils.validateStringLength("Enter book title: ");

                String bookAuthor = ValidationUtils.validateStringLength("Enter book author: ");

                Book[] books1 = bookController.searchBook(bookTitle, bookAuthor);
                for (Book book : books1){
                    if (book != null)
                        System.out.println(book);
                }
                break;

            case 3:
                String bookTitle1 = ValidationUtils.validateStringLength("Enter book title: ");

                String bookAuthor1 = ValidationUtils.validateStringLength("Enter book author: ");

                String bookCategory = ValidationUtils.validateStringLength("Enter book category: ");

                Book[] books2 = bookController.searchBook(bookTitle1, bookAuthor1, bookCategory);
                for (Book book : books2){
                    if (book != null)
                        System.out.println(book);
                }
                break;

            default:
                System.out.println("Invalid option: "+choice);
        }
    }

    public void borrowBook(){
        User user = authController.getMe();

        if(user.getStatus().name().equalsIgnoreCase("SUSPENDED")){
            System.out.println("Outstanding fines must be paid before borrowing");
            return;
        }

        String id = ValidationUtils.validateStringLength("Enter book id: ");

        boolean isBorrowed = borrowRecordController.borrowBook(user.getId(), id);
        if(isBorrowed){
            System.out.println("Book borrowed successfully");
        }else{
            System.out.println("Book borrow failed");
        }
    }

    public void returnBook(){
        String id = ValidationUtils.validateStringLength("Enter book id: ");

        boolean isBorrowed = borrowRecordController.returnBook(id);
        if(isBorrowed){
            System.out.println("Book returned successfully");
        }else{
            System.out.println("Book return failed");
        }
    }

    public void viewBorrowingRecords(){
        String userId = authController.getMe().getId();
        borrowRecordController.viewPersonalBorrowingRecords(userId);
    }

    public void viewAllBorrowingRecord(){
        String userId = authController.getMe().getId();
        borrowRecordController.viewAllBorrowRecords(userId);
    }

    public void deleteBorrowRecords(){
        User user = authController.getMe();

        if (user.getMembershipType() != MembershipType.STAFF)
            throw new RuntimeException("Not authorized, for staff members only");

        String recordId = readNonEmptyString("Enter record id: ");
        borrowRecordController.deleteBorrowRecords(recordId);
    }

    public void viewPersonalProfile(){
        String id = authController.getMe().getId();
        String viewProfileInfo = userController.viewUserProfile(id);

        if (viewProfileInfo != null){
            System.out.println("\n========================");
            System.out.println("   USER PROFILE         ");
            System.out.println("========================");

            System.out.println(viewProfileInfo);
        }
    }

    public void viewMemberProfile(){
        if(authController.getMe().getMembershipType() != MembershipType.STAFF)
            throw new RuntimeException("Unauthorized, only a staff is permitted");

        String viewMemberProfileInfo = userController.viewUserProfile(readNonEmptyString("Enter member Id: "));

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
        User user = authController.getMe();

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

        int choice = readInt("\nSelect an option (1 - 14): ", 1, 14);

        switch(choice){
            case 1:
                String firstName = ValidationUtils.validateStringLength("Enter firstName (minimum 3 characters): ");
                user.setFirstName(firstName);
                break;
            case 2:
                String lastName = ValidationUtils.validateStringLength("Enter lastName (minimum 3 characters): ");
                user.setLastName(lastName);
                break;
            case 3:
                String email = ValidationUtils.validateEmail(readNonEmptyString("Enter a valid email: "));
                user.setEmail(email);
                break;
            case 4:
                String phoneNo = ValidationUtils.validatePhoneNumber(readNonEmptyString("Enter a valid phone number: "));
                user.setPhoneNo(phoneNo);
                break;
            case 5:
                String firstName1 = ValidationUtils.validateStringLength("Enter firstName (minimum 3 characters): ");

                String lastName1 = ValidationUtils.validateStringLength("Enter lastName (minimum 3 characters): ");

                user.setFirstName(firstName1);
                user.setLastName(lastName1);
                break;
            case 6:
                String firstName2 = ValidationUtils.validateStringLength("Enter firstName (minimum 3 characters): ");

                String email2 = ValidationUtils.validateEmail(readNonEmptyString("Enter a valid email: "));

                user.setFirstName(firstName2);
                user.setEmail(email2);
                break;
            case 7:
                String firstName3 = ValidationUtils.validateStringLength("Enter firstName (minimum 3 characters): ");

                String phoneNo3 = ValidationUtils.validatePhoneNumber(readNonEmptyString("Enter a valid phone number: "));

                user.setFirstName(firstName3);
                user.setPhoneNo(phoneNo3);
                break;
            case 8:
                String lastName3 = ValidationUtils.validateStringLength("Enter lastName (minimum 3 characters): ");

                String email3 = ValidationUtils.validateEmail(readNonEmptyString("Enter a valid email: "));

                user.setLastName(lastName3);
                user.setEmail(email3);
                break;
            case 9:
                String lastName4 = ValidationUtils.validateStringLength("Enter lastName (minimum 3 characters): ");

                String phoneNo4 = ValidationUtils.validatePhoneNumber(readNonEmptyString("Enter a valid phone number: "));

                user.setLastName(lastName4);
                user.setPhoneNo(phoneNo4);
                break;
            case 10:
                String email5 = ValidationUtils.validateEmail(readNonEmptyString("Enter a valid email: "));

                String phoneNo5 = ValidationUtils.validatePhoneNumber(readNonEmptyString("Enter a valid phone number: "));

                user.setEmail(email5);
                user.setPhoneNo(phoneNo5);
                break;
            case 11:
                String firstName4 = ValidationUtils.validateStringLength("Enter firstName (minimum 3 characters): ");

                String lastName5 = ValidationUtils.validateStringLength("Enter lastName (minimum 3 characters): ");

                String email6 = ValidationUtils.validateEmail(readNonEmptyString("Enter a valid email: "));

                user.setFirstName(firstName4);
                user.setLastName(lastName5);
                user.setEmail(email6);
                break;
            case 12:
                String firstName5 = ValidationUtils.validateStringLength("Enter firstName (minimum 3 characters): ");

                String lastName6 = ValidationUtils.validateStringLength("Enter lastName (minimum 3 characters): ");

                String phoneNo6 = ValidationUtils.validatePhoneNumber(readNonEmptyString("Enter a valid phone number: "));

                user.setFirstName(firstName5);
                user.setLastName(lastName6);
                user.setPhoneNo(phoneNo6);
                break;
            case 13:
                String lastName7 = ValidationUtils.validateStringLength("Enter lastName (minimum 3 characters): ");

                String email4 = ValidationUtils.validateEmail(readNonEmptyString("Enter a valid email: "));

                String phoneNo2 = ValidationUtils.validatePhoneNumber(readNonEmptyString("Enter a valid phone number: "));

                user.setLastName(lastName7);
                user.setEmail(email4);
                user.setPhoneNo(phoneNo2);
                break;
            case 14:
                String firstName6 = ValidationUtils.validateStringLength("Enter firstName (minimum 3 characters): ");

                String lastName2 = ValidationUtils.validateStringLength("Enter lastName (minimum 3 characters): ");

                String email1 = ValidationUtils.validateEmail(readNonEmptyString("Enter a valid email: "));

                String phoneNo1 = ValidationUtils.validatePhoneNumber(readNonEmptyString("Enter a valid phone number: "));

                user.setFirstName(firstName6);
                user.setLastName(lastName2);
                user.setEmail(email1);
                user.setPhoneNo(phoneNo1);
                break;
            default:
                System.out.println("Unexpected value: " + choice);
        }

        String values = MethodUtils.generateUserStringValue(user);
        userController.updateUserProfile(user, values);

        System.out.println("Profile update successful");
    }

    public void updateMemberProfile(){}

    public void deletePersonalProfile(){
        String id = authController.getMe().getId();

        String answer = readNonEmptyString("Are you sure you want to delete your profile? Y or N: ");

        if (answer.equalsIgnoreCase("Y")){
            System.out.println(userController.deleteUserProfile(id));
        }else{
            System.out.println("Profile delete cancelled!!");
        }
    }

    public void deleteMemberProfile(){
        String memberId = readNonEmptyString("Enter member Id: ");

        String answer = readNonEmptyString("Are you sure you want to delete member profile? Y or N: ");

        if (answer.equalsIgnoreCase("Y")){
            System.out.println(userController.deleteUserProfile(memberId));
        }else{
            System.out.println("Profile delete cancelled!!");
        }
    }

    public void payFine(){
        User user = authController.getMe();

        if(user.getOutstandingFine() <= 0.0)
            throw new RuntimeException("You don't have any outstanding debt");

        System.out.println("Enter amount: ");
        double answer = validateNumber();

        borrowRecordController.payFine(user.getId(), answer);
    }

    public void viewLibraryStatistics(){
        System.out.println("========================================");
        System.out.println("        LIBRARY STATISTICS          ");
        System.out.println("========================================");

        System.out.println(borrowRecordController.viewLibraryStatistics());
    }

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

                firstName = ValidationUtils.validateStringLength("Enter firstName (minimum 3 characters): ");

                lastName = ValidationUtils.validateStringLength("Enter lastName (minimum 3 characters): ");

                email = ValidationUtils.validateEmail(readNonEmptyString("Enter a valid email: "));

                phoneNo = ValidationUtils.validatePhoneNumber(readNonEmptyString("Enter a valid phone number: "));

                membershipType = ValidationUtils.validateMembershipType(readNonEmptyString("Enter membership type i.e student, general or staff: "));

                password = ValidationUtils.validatePassword(readNonEmptyString(
                        "Choose a password, make sure it is at least 8 characters long and contains at least " +
                                "one number, capital letter,lower letter and special character e.g /,@,#,?,$,& e.t.c: "
                ));

                regFlag = true;

            } catch(InvalidNameLengthException | InvalidEmailException | InvalidPhoneNumberException |
                    InvalidMembershipTypeException | InvalidPasswordException ex){
                System.err.println("❌ Error: \n" + ex.getMessage());
                System.out.println("Please try again.\n");
            }
        }

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

        email = ValidationUtils.validateEmail(readNonEmptyString("Please enter your email: "));

        password = ValidationUtils.validatePassword(readNonEmptyString("Please enter your password: "));

        User user = authController.signInAccount(email, password);

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

        email = ValidationUtils.validateEmail(readNonEmptyString("Please enter your email: "));

        phoneNo = ValidationUtils.validatePhoneNumber(readNonEmptyString("Please enter your phone number: "));

        User user = authController.forgotPassword(email, phoneNo);

        if (user != null){
            password = ValidationUtils.validatePassword(readNonEmptyString("Choose a new password, make sure it is at least 8 characters long: "));

            confirmPassword = ValidationUtils.validatePassword(readNonEmptyString("Confirm password: " ));

            if (!password.equals(confirmPassword))
                throw new InvalidCredentialException("Password mismatch, check your passwords");

            String hashedPassword = hashPassword(password);
            user.setPassword(hashedPassword);

            String newValues = MethodUtils.generateUserStringValue(user);

            String updated = userController.updateUserProfile(user, newValues);

            System.out.println(Objects.requireNonNullElse(updated, "Password change failed"));
        }
    }

    public static void clearConsole() {
        try{
            Thread.sleep(3000);
        }catch(InterruptedException ex){
            System.out.println(ex.getMessage());
        }

        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }
}