package com.library.models;

import com.library.enums.MembershipType;


public class Librarian extends User{
    private MembershipType membershipType;

    public Librarian(String firstName, String lastName, String email, String phoneNo){
        super(firstName, lastName, email, phoneNo);
        this.membershipType = MembershipType.STAFF;
    }

    @Override
    public String displayInformation(){
        return "Librarian<(staff_id: "+getId()+", firstName: "+getFirstName()+", email: "+getEmail()+", status: "+getStatus()+")>";
    }
}