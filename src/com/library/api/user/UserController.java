package com.library.api.user;

import com.library.services.UserService;

import java.util.Scanner;


public class UserController{
    private static UserService userService = null;
    private static final Scanner scanner = new Scanner(System.in);

    private UserController(){
        userService = new UserService();
    }

    public static void getInstance(){
        new UserController();
    }

//    public static String viewUserProfile(){
//        String id = "";
//
//        System.out.println("Please enter your Id: ");
//        id = scanner.nextLine();
//
//         userService.viewProfile(id);
//
//    }
}