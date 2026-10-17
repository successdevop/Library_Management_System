package com.library.models;

import com.library.enums.MembershipType;

public class LibraryMember extends User{
    private MembershipType membershipType;

    public LibraryMember(String firstName, String lastName, String email, String phoneNo, MembershipType membershipType){
        super(firstName, lastName, email, phoneNo);
        this.membershipType = membershipType;
    }

    @Override
    public String displayInformation(){
        return "LibrarianMember<(member_id: "+getId()+", firstName: "+getFirstName()+", email: "+getEmail()+", status: "+getStatus()+")>";
    }

}