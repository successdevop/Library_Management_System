package com.library.services;

import com.library.exceptions.UserNotFoundException;
import com.library.models.Users;
import com.library.repos.UserRepo;


public class UserService{
    private final UserRepo userRepo;

    public UserService(){
        this.userRepo = new UserRepo();
    }

    public Users getAUserById(String id){
        Users user = userRepo.getUserById(id);

        if (user == null)
            throw new UserNotFoundException("Member not found");

        return user;
    }

    public String viewUserProfile(String userId){
        Users user = userRepo.getUserById(userId);

        if (user == null)
            throw new UserNotFoundException("Member not found");

        return (
                "Id: "+user.getId()+"\n" +
                        "FirstName: "+user.getFirstName()+"\n" +
                        "LastName: "+user.getLastName()+"\n" +
                        "Email: "+user.getEmail()+"\n" +
                        "PhoneNo: "+user.getPhoneNo()+"\n" +
                        "Registration Date: "+user.getDateJoined()+"\n" +
                        "Status: "+user.getStatus()+"\n" +
                        "Membership Type: "+user.getMembershipType()
                );

    }

    public void viewAllUsers(){
        Users[] users = userRepo.getAllUsers();

        for (int i = 0; i < users.length; i++){
            if (users[i] == null)
                continue;

            System.out.println((i+1)+". "+users[i]);
        }
    }

    public String editUserProfile(Users user, String newValues){
        userRepo.editUser(user, newValues);
        return "Profile update successful";
    }

    public String deleteUserProfile(String id){
        userRepo.deleteUser(id);
        return id +", Deleted";
    }
}