package com.library.api.auth;

import com.library.enums.MembershipType;
import com.library.exceptions.*;
import com.library.models.Users;
import com.library.services.AuthService;


public class Auth{
    private static AuthService authService = null;

    public Auth(){
        authService = new AuthService();
    }

    // GET ME
    public Users getMe(){
        return authService.getMe();
    }

    // REGISTER MEMBER
    public boolean signUpAccount(String firstName, String lastName, String email, String phoneNo, String membershipType,
                                String password){

        return authService.registerMember(firstName, lastName, email, phoneNo,
                MembershipType.valueOf(membershipType.toUpperCase().trim()), password);
    }

    // LOGIN MEMBER
    public Users signInAccount(String email, String password){
        return authService.loginMember(email, password);
    }

    // LOGOUT MEMBER
    public void logout(){
        authService.logout();
    }

    // FORGOT PASSWORD
    public Users forgotPassword(String email, String phoneNumber){
        return authService.forgotPassword(email, phoneNumber);
    }
}