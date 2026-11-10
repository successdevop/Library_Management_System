package com.library.services;

import com.library.exceptions.UserNotFoundException;
import com.library.models.User;
import com.library.repos.UserRepo;

import static com.library.utils.MethodUtils.countUserActiveBorrowings;


public class UserService{
    private final UserRepo userRepo;

    public UserService(){
        this.userRepo = new UserRepo();
    }

    public String viewUserProfile(String userId){
        User user = userRepo.getUserById(userId);

        if (user == null)
            throw new UserNotFoundException("Member not found");

        return (
                "Id: "+user.getId()+"\n" +
                        "FirstName: "+user.getFirstName()+"\n" +
                        "LastName: "+user.getLastName()+"\n" +
                        "Email: "+user.getEmail()+"\n" +
                        "PhoneNo: "+user.getPhoneNo()+"\n" +
                        "Registration Date: "+user.getDateJoined()+"\n" +
                        "Status: "+user.getStatus()+"\n" +
                        "Membership Type: "+user.getMembershipType()+"\n"+
                        "Outstanding Fine: "+user.getOutstandingFine()
                );
    }

    public void viewAllUsers(){
        User[] users = userRepo.getAllUsers();

        for (int i = 0; i < users.length; i++){
            if (users[i] == null)
                continue;

            System.out.println((i+1)+". "+users[i]);
        }
    }

    public String editUserProfile(User user, String newValues){
        userRepo.editUser(user, newValues);
        return "Profile update successful";
    }

    public String deleteUserProfile(String id){
        User user = userRepo.getUserById(id);

        if (user.getOutstandingFine() > 0.0)
            throw new RuntimeException("A member with an unpaid fine cannot be deleted");

        if (countUserActiveBorrowings(user.getId()) > 0)
            throw new RuntimeException("A member with an unreturned book cannot be deleted.");

        userRepo.deleteUser(id);
        return id +", Deleted";
    }
}