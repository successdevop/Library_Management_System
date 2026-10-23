package com.library;

import com.library.api.Controller;
import com.library.app_files.Config;
import com.library.database.Database;


public class Main{
    public static void main(String[] args){
        Database.getInstance(Config.DATABASE_FILE);
        Controller.getInstance();


        try{
            System.out.println(Controller.signUpAccount());
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}