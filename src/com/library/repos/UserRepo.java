package com.library.repos;

import com.library.database.Database;
import com.library.models.Users;

import java.util.Arrays;

public class UserRepo{

    protected void addUser(Users user){
        Users[] users = Database.getUsersDatabase();
        for (int i = 0; i < users.length; i++){
            if(users[i] == null){
                users[i] = user;
            }
        }

        System.out.println(Arrays.toString(users));
    }
}