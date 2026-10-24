package com.library;

import com.library.api.Controller;
import com.library.api.auth.Auth;
import com.library.app_files.Config;
import com.library.database.Database;

import static com.library.api.auth.Auth.signInAccount;
import static com.library.api.auth.Auth.signUpAccount;


public class Main{
    public static void main(String[] args){
        Database.getInstance(Config.DATABASE_FILE);
        Auth.getInstance();


        try{
//            System.out.println(signUpAccount());
            signInAccount();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}