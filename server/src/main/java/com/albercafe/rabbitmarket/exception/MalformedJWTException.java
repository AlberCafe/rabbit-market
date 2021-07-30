package com.albercafe.rabbitmarket.exception;

public class MalformedJWTException extends RuntimeException {
    public MalformedJWTException(String message) { super(message); }
}
