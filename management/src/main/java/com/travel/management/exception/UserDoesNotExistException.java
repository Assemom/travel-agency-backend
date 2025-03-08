package com.travel.management.exception;

public class UserDoesNotExistException extends RuntimeException{
    public UserDoesNotExistException() {
        super("user you search for does not exist");
    }
}
