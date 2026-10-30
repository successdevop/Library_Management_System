package com.library.api.auth;

import com.library.enums.MembershipType;
import com.library.exceptions.*;
import com.library.models.Users;
import com.library.services.AuthService;
import com.library.utils.LibraryUtils;

import java.util.Scanner;


public class Auth{
    private static AuthService authService = null;
    private static final Scanner scanner = new Scanner(System.in);
    private static Users currentUser = null;

    public Auth(){
        authService = new AuthService();
    }

    public Users getCurrentUser(){
        return currentUser;
    }

    // REGISTER MEMBER
    public String signUpAccount(){
        String firstName = "";
        String lastName = "";
        String email = "";
        String phoneNo = "";
        String membershipType = "";
        String password = "";

        boolean regFlag = false;

        System.out.println("=== User Registration ===");
        System.out.println();

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
                        "one number, capital letter,lower letter and special character e.g /,@,#,?,$,& e.t.c: ");
                password = LibraryUtils.validatePassword(scanner.nextLine());

                regFlag = true;

                scanner.close();
            } catch(InvalidNameLengthException | InvalidEmailException | InvalidPhoneNumberException |
                    InvalidMembershipTypeException | InvalidPasswordException ex){
                System.err.println("❌ Error: " + ex.getMessage());
                System.out.println("Please try again.\n");
            }
        }

        // enforce lower case
        email = email.toLowerCase();

        Users user = authService.registerMember(firstName, lastName, email, phoneNo,
                MembershipType.valueOf(membershipType.toUpperCase().trim()), password);

        if (user != null)
            return "Congratulations "+user.getFirstName()+", your registration is successful";

        return "User registration failed, please try again later";
    }

    // LOGIN MEMBER
    public Users signInAccount(){
        String email = "";
        String password = "";

        System.out.print("Please enter your email: ");
        email = LibraryUtils.validateEmail(scanner.nextLine());

        System.out.print("Please enter your password: ");
        password = LibraryUtils.validatePassword(scanner.nextLine());

        // enforce lower case
        email = email.toLowerCase();
        Users user = authService.loginMember(email, password);

        if (user == null)
            System.out.println("Login failed");

        currentUser = user;
        System.out.println("Login successful");

        return currentUser;
    }

    // REGISTER MEMBER
    public void logout(){
        if (currentUser != null){
            System.out.println(currentUser.getFirstName()+" logged out successfully");
            currentUser = null;
        }
    }
}