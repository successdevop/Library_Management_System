package com.library.repos;

import com.library.database.Database;
import com.library.models.User;

import static com.library.app_files.Config.USERS_STORAGE_FILE;


public class UserRepo{
    private final User[] users = Database.getUsersDatabase();

    public void addUser(User user){
        Database.appendUserToFile(USERS_STORAGE_FILE, user);

        for (int i = 0; i < users.length; i++){
            if(users[i] == null){
                users[i] = user;
                break;
            }
        }
    }

    public void editUser(User user, String userNewValues){
        Database.updateUserFile(USERS_STORAGE_FILE, user, userNewValues);

        for (int i = 0; i < users.length; i++){
            if (users[i].getId().equals(user.getId())){
                users[i] = user;
                break;
            }
        }
    }

    public void deleteUser(String id){
        Database.deleteFromUserFile(USERS_STORAGE_FILE, id);

        for (int i = 0; i < users.length; i++){
            if (users[i] != null && users[i].getId().equals(id)){
                users[i] = null;
                break;
            }
        }
    }

    public User getUserById(String id){

        for (User user : users) {
            if (user != null && user.getId().equals(id)) {
                return user;
            }
        }

        return null;
    }

    public User getUserByEmail(String email){

        for (User user : users) {
            if (user != null && user.getEmail().equals(email)) {
                return user;
            }
        }

        return null;
    }

    public String getUserByPhoneNumber(String phoneNo){

        for (User user : users){
            if (user != null && user.getPhoneNo().equals(phoneNo)){
                return user.getPhoneNo();
            }
        }

        return null;
    }

    public User[] getAllUsers(){
        return users;
    }
}