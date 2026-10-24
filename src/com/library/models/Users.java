package com.library.models;

import com.library.enums.MemberStatus;
import com.library.enums.MembershipType;

import java.time.LocalDate;


public class Users{
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private LocalDate dateJoined;
    private MemberStatus status;
    private MembershipType membershipType;

    public Users(String firstName, String lastName, String email, String phoneNo, MembershipType membershipType){
        this.id = getId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.dateJoined = getDateJoined();
        this.status = getStatus();
        this.membershipType = membershipType;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String vals){
        this.id = vals;
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

    public LocalDate getDateJoined(){
        return this.dateJoined;
    }

    public void setDateJoined(LocalDate date){
        this.dateJoined = date;
    }

    public MembershipType getMembershipType(){
        return this.membershipType;
    }

    public void setMembershipType(MembershipType memberType){
        this.membershipType = memberType;
    }

    @Override
    public String toString(){
        return "User<(id: "+this.id+", firstName: "+this.firstName+", email: "+this.email+", " +
                "userType: "+this.membershipType+", status: "+getStatus()+")>";
    }
}