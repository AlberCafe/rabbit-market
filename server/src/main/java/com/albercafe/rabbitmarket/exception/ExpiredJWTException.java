package com.albercafe.rabbitmarket.exception;

public class ExpiredJWTException extends RuntimeException{
    public ExpiredJWTException(String message) { super(message); }
}
