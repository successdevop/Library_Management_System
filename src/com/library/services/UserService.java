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
            throw new UserNotFoundException();

        System.out.println("\n========================");
        System.out.println("   USER PROFILE         ");
        System.out.println("========================");

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
}