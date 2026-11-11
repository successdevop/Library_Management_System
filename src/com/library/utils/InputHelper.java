package com.library.utils;

import java.util.Scanner;

public class InputHelper{
    private static final Scanner scanner = new Scanner(System.in);

    public static String readString(String prompt){
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

    public static String readNonEmptyString(String prompt){
        while(true){
            String value = readString(prompt);
            if(!value.isEmpty()) return value;
            System.out.println("This field cannot be empty");
        }
    }

    public static int readInt(String prompt, int min, int max){
        while(true){
            String value = readNonEmptyString(prompt);
            try{
                int number = Integer.parseInt(value);
                if(number >= min && number <= max) return number;
                System.out.println("Enter a number between "+min+" and "+max);
            }catch(NumberFormatException ex){
                System.err.println("Please enter a valid whole number");
            }
        }
    }
}