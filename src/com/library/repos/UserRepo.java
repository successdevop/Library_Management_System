package com.library.repos;

import com.library.database.Database;
import com.library.models.Users;

public class UserRepo{
    protected void addUser(Users user){
        Users[] users = Database.getUsersDatabase();
        users[users.length -1] = user;
    }
}