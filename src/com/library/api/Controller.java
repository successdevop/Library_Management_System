package com.library.api;

import com.library.api.auth.Auth;
import com.library.api.user.UserController;
import com.library.models.Users;
import com.library.utils.LibraryUtils;

import java.util.Scanner;


public class Controller{
    Scanner scanner = new Scanner(System.in);
    private final Auth authController = new Auth();
    private final UserController userController = new UserController();

    public void displayMainMenu(){
        System.out.println("========================================");
        System.out.println("WELCOME TO THE UNIVERSITY LIBRARY SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Are you new here? Sign up.");
        System.out.println("2. Login");
    }

    public void displayPersonalMenu(String membershipType){
        if (membershipType.equalsIgnoreCase("staff")){
            System.out.println("1. Add Book.");
            System.out.println("2. View Book Details");
            System.out.println("3. View All Books");
            System.out.println("4. Search Book");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. View Borrowing Records");
            System.out.println("8. View All Borrowing Records");
            System.out.println("9. View Personal Profile");
            System.out.println("10. View Member Details");
            System.out.println("11. View All Members");
            System.out.println("12. View Library Statistics");
            System.out.println("0. Logout");
        }else{
            System.out.println("1. View Book Details");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. View Borrowing Records");
            System.out.println("7. View Profile");
            System.out.println("0. Logout");
        }
    }

    public void featuresCall(String membershipType, String id){
        String choice;

        if (membershipType.equalsIgnoreCase("staff")){
            System.out.print("\nSelect an option (0 - 12): ");
            choice = scanner.nextLine();

            switch(choice){
                case "1":
                    System.out.println("Add book");
                    break;
                case "2":
                    System.out.println("View Book Details");
                    break;
                case "3":
                    System.out.println("View All Books");
                    break;
                case "4":
                    System.out.println("Search Book");
                    break;
                case "5":
                    System.out.println("Borrow Book");
                    break;
                case "6":
                    System.out.println("Return Book");
                    break;
                case "7":
                    System.out.println("View Borrowing Records");
                    break;
                case "8":
                    System.out.println("View All Borrowing Records");
                    break;
                case "9":
                    System.out.println(userController.viewUserProfile(id));
                    break;
                case "10":
                    System.out.print("Enter member Id:");
                    choice = scanner.nextLine();
                    System.out.println(userController.viewUserProfile(choice));
                    break;
                case "11":
                    userController.viewAllUsers();
                    break;
                case "12":
                    System.out.println("View Library Statistics");
                    break;
                case "0":
                    authController.logout();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }
        }else {
            System.out.print("\nSelect an option (0 - 7): ");
            choice = scanner.nextLine();

            switch(choice){
                case "1":
                    System.out.println("View Book Details");
                    break;
                case "2":
                    System.out.println("View All Books");
                    break;
                case "3":
                    System.out.println("Search Book");
                    break;
                case "4":
                    System.out.println("Borrow Book");
                    break;
                case "5":
                    System.out.println("Return Book");
                    break;
                case "6":
                    System.out.println("View Borrowing Records");
                    break;
                case "7":
                    System.out.println(userController.viewUserProfile(id));
                    break;
                case "0":
                    authController.logout();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }
        }
        scanner.close();
    }

    public void personalDashBoard(Users user){
        if (user == null)
            return;

        String first_name = user.getFirstName();

        System.out.println("========================================");
        System.out.println(" "+first_name.toUpperCase()+" DASHBOARD ");
        System.out.println("========================================");

        System.out.println(LibraryUtils.greet()+" "+first_name+", Welcome to your dashboard\n");

        String membershipType = user.getMembershipType().name().toLowerCase();
        String userId = user.getId();

        displayPersonalMenu(membershipType);

        featuresCall(membershipType, userId);
    }

    public String registerMember(){
        System.out.println("========================================");
        System.out.println("        REGISTRATION DASHBOARD          ");
        System.out.println("========================================");
        return authController.signUpAccount();
    }

    public Users loginMember(){
        System.out.println("========================================");
        System.out.println("            LOGIN DASHBOARD             ");
        System.out.println("========================================");
        return authController.signInAccount();
    }
}