package com.library;

import com.library.api.Controller;
import com.library.app_files.Config;
import com.library.database.Database;
import com.library.models.Users;

import java.util.Scanner;


public class Main{
    public static void main(String[] args) throws InterruptedException {
        Database.getInstance(Config.DATABASE_FILE);
        Controller controller = new Controller();


        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        String choice = "";

        while(running){
            controller.displayMainMenu();
            System.out.print("\nSelect an option (1 or 2): ");

            choice = scanner.nextLine();

            // 1. Clear the terminal display
            clearConsole();
            try{
                switch (choice){
                    case "1":
                        System.out.println(controller.registerMember());
                        break;

                    case "2":
                        Users user = controller.loginMember();
                        clearConsole();
                        controller.personalDashBoard(user);
                        break;

                    default:
                        System.out.println("Invalid number option");
                }
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }

            scanner.close();
            running = false;
        }
    }

    public static void clearConsole() {
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }
}