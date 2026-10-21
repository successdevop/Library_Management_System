package com.library.exceptions;


public class InvalidMembershipTypeException extends RuntimeException{
    private final String membershipType;

    public InvalidMembershipTypeException(String membershipType){
        this.membershipType = membershipType;
    }

    @Override
    public String getMessage(){
        return this.membershipType;
    }
}