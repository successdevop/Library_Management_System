package com.library.models;

import com.library.enums.MemberStatus;

import java.util.Date;

public abstract class User{
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private Date dateJoined;
    private MemberStatus status;

    protected User(String firstName, String lastName, String email, String phoneNo){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.status = MemberStatus.ACTIVE;
    }

    public String getId(){
        return this.id;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPhoneNo(){
        return this.phoneNo;
    }

    public void setPhoneNo(String phoneNo){
        this.phoneNo = phoneNo;
    }

    public MemberStatus getStatus(){
        return this.status;
    }

    public void setStatus(MemberStatus status){
        this.status = status;
    }

    public Date getDateJoined(){
        return this.dateJoined;
    }

    public abstract String displayInformation();
}