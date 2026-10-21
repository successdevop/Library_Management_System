package com.library.api;

import com.library.utils.LibraryUtils;

import java.util.Scanner;

public class Controller{
    private static final Scanner scanner = new Scanner(System.in);

    public static void signUpAccount(){
        System.out.println("=== User Registration ===");
        System.out.println();

        while(true){
            System.out.print("Enter firstName (minimum 3 characters): ");
            String firstName = LibraryUtils.validateStringLength(scanner.nextLine());

            System.out.print("Enter lastName (minimum 3 characters): ");
            String lastName = LibraryUtils.validateStringLength(scanner.nextLine());

            System.out.print("Enter a valid email: ");
            String email = LibraryUtils.validateStringLength(scanner.nextLine());

            System.out.print("Enter a valid phone number: ");
            String phoneNo = LibraryUtils.validateStringLength(scanner.nextLine());

            System.out.print("Enter firstName (minimum 3 characters): ");
            String membershipType = LibraryUtils.validateStringLength(scanner.nextLine());
        }
    }
}