package com.library.services;

import com.library.exceptions.UserNotFoundException;
import com.library.models.Users;
import com.library.repos.UserRepo;


public class UserService{
    private final UserRepo userRepo;

    public UserService(){
        this.userRepo = new UserRepo();
    }

    public String userProfile(String userId){
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

    public void getAllUsers(){
        Users[] users = userRepo.getAllUsers();

        for (int i = 0; i < users.length; i++){
            if (users[i] == null)
                continue;

            System.out.println((i+1)+". "+"User<(id: "+users[i].getId()+", firstName: "+users[i].getFirstName()+", email: "+
                    users[i].getEmail()+", " + "userType: "+users[i].getMembershipType()+", status: "+users[i].getStatus()+")>");
        }
    }

    public Users editUserProfile(String userId, String newValues){
        Users user = userRepo.getUserById(userId);

        if (user == null)
            throw new UserNotFoundException("Member not found");

        return userRepo.editUser(userId, newValues);
    }
}