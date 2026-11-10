package com.library;

import com.library.api.Controller;
import com.library.app_files.Config;
import com.library.database.Database;

import java.time.LocalDate;
import java.util.Scanner;

import static com.library.api.Controller.clearConsole;


public class Main{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Database.getInstance(Config.DATABASE_FILE);
        Controller controller = new Controller();

        boolean running = true;

        while(running){
            controller.displayMainMenu();
            System.out.print("\nSelect an option (0 - 3): ");

            try{
                switch (scanner.nextLine()){
                    case "1":
                        clearConsole();
                        controller.registerMember();
                        break;

                    case "2":
                        clearConsole();
                        controller.loginMember();
                        break;

                    case "3":
                        clearConsole();
                        controller.forgotPassword();
                        break;

                    case "0":
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid number option");
                }
            }catch(Exception ex){
                System.out.println("\nERROR: "+ex.getMessage()+"\n");
            }
        }

        scanner.close();
    }
}