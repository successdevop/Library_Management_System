package com.library.repos;

import com.library.database.Database;
import com.library.models.Users;

import java.util.Arrays;


public class UserRepo{

    public void addUser(Users user){
        Users[] users = Database.getUsersDatabase();

        for (int i = 0; i < users.length; i++){
            if(users[i] == null){
                users[i] = user;
                break;
            }
        }

        System.out.println(Arrays.toString(users));
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