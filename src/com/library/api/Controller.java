package com.library.api;

import com.library.enums.MembershipType;
import com.library.exceptions.InvalidEmailException;
import com.library.exceptions.InvalidMembershipTypeException;
import com.library.exceptions.InvalidNameLengthException;
import com.library.exceptions.InvalidPhoneNumberException;
import com.library.services.UserService;
import com.library.utils.LibraryUtils;

import java.util.Scanner;

public class Controller{
    private static UserService userService = null;
    private static final Scanner scanner = new Scanner(System.in);

    private Controller(){
        userService = new UserService();
    }

    public static void getInstance(){
        new Controller();
    }

    public static void signUpAccount(){
        String firstName = "";
        String lastName = "";
        String email = "";
        String phoneNo = "";
        String membershipType = "";
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

                regFlag = true;
            } catch(InvalidNameLengthException | InvalidEmailException | InvalidPhoneNumberException |
                   InvalidMembershipTypeException ex){
                System.out.println("❌ Error: " + ex.getMessage());
                System.out.println("Please try again.\n\n");
            }
        }

        // enforce lower case
        email = email.toLowerCase();

        userService.registerMember(firstName, lastName, email, phoneNo,
                MembershipType.valueOf(membershipType.toUpperCase().trim()));

        scanner.close();
    }
}