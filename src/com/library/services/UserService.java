package com.library.services;

import com.library.enums.MembershipType;
import com.library.exceptions.PhoneNumberAlreadyExistsException;
import com.library.exceptions.UserAlreadyExistException;
import com.library.models.Users;
import com.library.repos.UserRepo;

public class UserService{
    private final UserRepo userRepo;

    public UserService(){
        this.userRepo = new UserRepo();
    }

    public Users registerMember(String firstName, String lastName, String email, String phoneNo, MembershipType membershipType){
        Users user = userRepo.getUserByEmail(email);
        if (user != null){
            throw new UserAlreadyExistException();
        }

        String phoneNumber = userRepo.getUserByPhoneNumber(phoneNo);
        if (phoneNumber != null){
            throw new PhoneNumberAlreadyExistsException();
        }

        Users newUser = new Users(firstName, lastName, email, phoneNo, membershipType);

        return userRepo.addUser(newUser);
    }
}