package com.library;

import com.library.api.Controller;

public class Main{
    public static void main(String[] args){

        try{
            Controller.signUpAccount();
            System.out.println("Hello world");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}