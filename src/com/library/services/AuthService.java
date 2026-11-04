package com.library.services;

import com.library.enums.MemberStatus;
import com.library.enums.MembershipType;
import com.library.exceptions.*;
import com.library.models.Users;
import com.library.repos.UserRepo;

import java.time.LocalDate;

import static com.library.utils.AuthUtils.hashPassword;
import static com.library.utils.AuthUtils.verifyPassword;
import static com.library.utils.LibraryUtils.generateUserId;


public class AuthService{
    private final UserRepo userRepo;
    private static Users currentUser;

    public AuthService(){
        this.userRepo = new UserRepo();
    }

    public Users registerMember(String firstName, String lastName, String email, String phoneNo,
                                MembershipType membershipType, String password){

        Users user = userRepo.getUserByEmail(email);
        if (user != null){
            throw new UserAlreadyExistException();
        }

        String phoneNumber = userRepo.getUserByPhoneNumber(phoneNo);
        if (phoneNumber != null){
            throw new PhoneNumberAlreadyExistsException();
        }

        String hashedPassword = hashPassword(password);

        Users newUser = new Users(firstName, lastName, email, phoneNo, membershipType, hashedPassword);
        newUser.setId(generateUserId());
        newUser.setDateJoined(LocalDate.now());
        newUser.setStatus(MemberStatus.ACTIVE);

        return userRepo.addUser(newUser);
    }

    public Users loginMember(String email, String password){
        Users user = userRepo.getUserByEmail(email);

        if (user == null)
            throw new InvalidCredentialException("Invalid email or password");

        if (!verifyPassword(password, user.getPassword())) {
            throw new InvalidCredentialException("Invalid email or password");
        }

        if (user.getStatus().toString().equalsIgnoreCase("inactive")){
            throw new InactiveAccountException("Account is inactive, please contact customer support/agents");
        }

        if (user.getStatus().toString().equalsIgnoreCase("suspended")){
            throw new InactiveAccountException("Account is suspended, please contact customer support/agents");
        }

        if (user.getStatus().toString().equalsIgnoreCase("expired"))
            throw new InactiveAccountException("Account is expired, please contact customer support/agents");

        currentUser = user;
        return currentUser;
    }

    public void logout(){
        if (currentUser != null){
            System.out.println(currentUser.getFirstName()+" logged out successfully");
            currentUser = null;
        }
    }

    public boolean forgotPassword(String email, String phoneNumber){
        Users user = userRepo.getUserByEmail(email);
        String phoneNo = userRepo.getUserByPhoneNumber(phoneNumber);

        if (user == null || phoneNo == null)
            throw new InvalidCredentialException("Incorrect email or phone number");

        return true;
    }
}