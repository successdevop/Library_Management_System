package com.library;

import com.library.api.Controller;

public class Main{
    public static void main(String[] args){
        Controller.getInstance();

        try{
            Controller.signUpAccount();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}