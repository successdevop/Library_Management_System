package com.library.api.user;

import com.library.services.UserService;

import java.util.Scanner;


public class UserController{
    private static UserService userService = null;
    private static final Scanner scanner = new Scanner(System.in);

    public UserController(){
        userService = new UserService();
    }

    // VIEW USER PROFILE
    public String viewUserProfile(String id){
         return userService.userProfile(id);
    }

    // VIEW ALL USERS
    public void viewAllUsers(){
        userService.getAllUsers();
    }
}