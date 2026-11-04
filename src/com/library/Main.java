package com.library;

import com.library.api.Controller;
import com.library.app_files.Config;
import com.library.database.Database;

import java.util.Scanner;
import static com.library.api.Controller.clearConsole;


public class Main{
    public static void main(String[] args) {
        Database.getInstance(Config.DATABASE_FILE);
        Controller controller = new Controller();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while(running){
            controller.displayMainMenu();
            System.out.print("\nSelect an option (0 - 2): ");

            try{
                switch (scanner.nextLine()){
                    case "1":
                        clearConsole();
                        controller.registerMember();
                        break;

                    case "2":
                        controller.loginMember();
                        break;

                    case "0":
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid number option");
                }
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }

        scanner.close();
    }
}