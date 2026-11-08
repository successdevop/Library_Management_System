package com.library.api.user;

import com.library.models.Users;
import com.library.services.UserService;


public class UserController{
    private static UserService userService = null;

    public UserController(){
        userService = new UserService();
    }

    // VIEW USER PROFILE
    public String viewUserProfile(String id){
         return userService.viewUserProfile(id);
    }

    // VIEW ALL USERS
    public void viewAllUsers(){
        userService.getAllUsers();
    }

    // EDIT/UPDATE USER
    public String updateUserProfile(Users user, String userNewValues){
        return userService.editUserProfile(user, userNewValues);
    }

    // DELETE USER
    public String deleteUserProfile(String id){
        return userService.deleteUserProfile(id);
    }
}