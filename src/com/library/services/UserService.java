package com.library.services;

import com.library.database.Database;
import com.library.enums.MemberStatus;
import com.library.enums.MembershipType;
import com.library.exceptions.PhoneNumberAlreadyExistsException;
import com.library.exceptions.UserAlreadyExistException;
import com.library.exceptions.UserNotFoundException;
import com.library.models.Users;
import com.library.repos.UserRepo;

import java.time.LocalDate;

import static com.library.utils.LibraryUtils.generateUserId;

public class UserService{
    private final UserRepo userRepo;

    public UserService(){
        this.userRepo = new UserRepo();
    }

    public String viewProfile(String userId){
        Users user = userRepo.getUserById(userId);

        if (user == null)
            throw new UserNotFoundException();

        return "User Profile\n" +
                "Id: "+user.getId()+"\n" +
                "FirstName: "+user.getFirstName()+"\n" +
                "LastName: "+user.getLastName()+"\n" +
                "Email: "+user.getEmail()+"\n" +
                "PhoneNo: "+user.getPhoneNo()+"\n" +
                "Registration Date: "+user.getDateJoined()+"\n" +
                "Status: "+user.getStatus()+"\n" +
                "Membership Type: "+user.getMembershipType();
    }
}