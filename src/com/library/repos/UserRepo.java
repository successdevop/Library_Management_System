package com.library.repos;

import com.library.database.Database;
import com.library.models.Users;

import static com.library.app_files.Config.USERS_STORAGE_FILE;


public class UserRepo{

    public Users addUser(Users user){
        Users userResult =  Database.appendUserToFile(USERS_STORAGE_FILE, user);

        if (userResult == null)
            return null;

        Users[] users = Database.getUsersDatabase();
        for (int i = 0; i < users.length; i++){
            if(users[i] == null){
                users[i] = userResult;
                break;
            }
        }

        return userResult;
    }

    public Users getUserByEmail(String email){
        Users[] users = Database.getUsersDatabase();
        Users user = null;

        for (Users userObj : users) {
            if (userObj != null && userObj.getEmail().equals(email)) {
                user = userObj;
                break;
            }
        }

        return user;
    }

    public String getUserByPhoneNumber(String phoneNo){
        String phoneNumber = null;

        for (Users user : Database.getUsersDatabase()){
            if (user != null && user.getPhoneNo().equals(phoneNo)){
                phoneNumber = user.getPhoneNo();
                break;
            }
        }

        return phoneNumber;
    }
}