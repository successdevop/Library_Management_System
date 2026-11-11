package com.library;

import com.library.api.Controller;
import com.library.app_files.Config;
import com.library.database.Database;

import static com.library.api.Controller.clearConsole;
import static com.library.utils.InputHelper.readInt;


public class Main{
    public static void main(String[] args) {
        Database.getInstance(Config.DATABASE_FILE);
        Controller controller = new Controller();

        boolean running = true;

        while(running){
            controller.displayMainMenu();
            int choice = readInt("\nSelect an option (0 - 3)", 0, 3);

            try{
                switch (choice){
                    case 1:
                        clearConsole();
                        controller.registerMember();
                        break;

                    case 2:
                        clearConsole();
                        controller.loginMember();
                        break;

                    case 3:
                        clearConsole();
                        controller.forgotPassword();
                        break;

                    case 0:
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid number option");
                }
            }catch(Exception ex){
                System.out.println("\nERROR: "+ex.getMessage()+"\n");
            }
        }
    }
}