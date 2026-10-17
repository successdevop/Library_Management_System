package com.library.models;

import java.util.Date;

public abstract class User{
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private Date dateJoined;

    protected User(String id, String firstName, String lastName, String email, String phoneNo, Date dateJoined){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.dateJoined = dateJoined;
    }

    public abstract void displayInformation();
}