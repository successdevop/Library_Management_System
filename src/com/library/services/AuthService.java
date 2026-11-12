package com.library.services;

import com.library.enums.MemberStatus;
import com.library.enums.MembershipType;
import com.library.exceptions.*;
import com.library.models.User;
import com.library.repos.UserRepo;

import java.time.LocalDate;

import static com.library.utils.AuthUtils.hashPassword;
import static com.library.utils.AuthUtils.verifyPassword;
import static com.library.utils.MethodUtils.generateUserId;


public class AuthService{
    private final UserRepo userRepo;
    private User currentUser;

    public AuthService(){
        this.userRepo = new UserRepo();
    }

    public User getMe(){
        return this.currentUser;
    }

    public boolean registerMember(String firstName, String lastName, String email, String phoneNo,
                                MembershipType membershipType, String password){

        User user = userRepo.getUserByEmail(email);
        if (user != null){
            throw new UserAlreadyExistException();
        }

        String phoneNumber = userRepo.getUserByPhoneNumber(phoneNo);
        if (phoneNumber != null){
            throw new PhoneNumberAlreadyExistsException();
        }

        String hashedPassword = hashPassword(password);

        User newUser = new User(generateUserId(), firstName, lastName, email, phoneNo, LocalDate.now(),
                MemberStatus.ACTIVE, membershipType, hashedPassword, 0.0);

        userRepo.addUser(newUser);

        return true;
    }

    public User loginMember(String email, String password){
        User user = userRepo.getUserByEmail(email);

        if (user == null)
            throw new InvalidCredentialException("Invalid email or password");

        if (!verifyPassword(password, user.getPassword())) {
            throw new InvalidCredentialException("Invalid email or password");
        }

        if (isInactive(user)){
            throw new InactiveAccountException("Account is "+user.getStatus()+", please contact customer support/agents");
        }

        this.currentUser = user;
        return this.currentUser;
    }

    public void logout(){
        if (this.currentUser != null){
            System.out.println(this.currentUser.getFirstName()+" logged out successfully");
            this.currentUser = null;
        }
    }

    public User forgotPassword(String email, String phoneNumber){
        User user = userRepo.getUserByEmail(email);
        String phoneNo = userRepo.getUserByPhoneNumber(phoneNumber);

        if (user == null || !user.getPhoneNo().equalsIgnoreCase(phoneNo))
            throw new InvalidCredentialException("Incorrect email or phone number");

        return user;
    }

    private boolean isInactive(User user){
        return user.getStatus() == MemberStatus.INACTIVE || user.getStatus() == MemberStatus.EXPIRED;
    }
}