package com.albercafe.rabbitmarket.exception;

public class UserProfileNotFoundException extends RuntimeException{
    public UserProfileNotFoundException(String message) { super(message); }
}
