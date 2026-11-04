package com.library.repos;

import com.library.database.Database;
import com.library.models.Users;

import static com.library.app_files.Config.USERS_STORAGE_FILE;


public class UserRepo{
    private final Users[] users = Database.getUsersDatabase();

    public Users addUser(Users user){
        Users userObj =  Database.appendUserToFile(USERS_STORAGE_FILE, user);

        if (userObj == null)
            return null;

        for (int i = 0; i < users.length; i++){
            if(users[i] == null){
                users[i] = userObj;
                break;
            }
        }

        return userObj;
    }

    public Users getUserById(String id){

        for (Users user : users) {
            if (user != null && user.getId().equals(id)) {
                return user;
            }
        }

        return null;
    }

    public Users getUserByEmail(String email){

        for (Users user : users) {
            if (user != null && user.getEmail().equals(email)) {
                return user;
            }
        }

        return null;
    }

    public String getUserByPhoneNumber(String phoneNo){

        for (Users user : users){
            if (user != null && user.getPhoneNo().equals(phoneNo)){
                return user.getPhoneNo();
            }
        }

        return null;
    }

    public Users[] getAllUsers(){
        return users;
    }
}