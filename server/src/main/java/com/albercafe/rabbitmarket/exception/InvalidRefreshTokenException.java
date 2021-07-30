package com.albercafe.rabbitmarket.exception;

public class InvalidRefreshTokenException extends RuntimeException{
    public InvalidRefreshTokenException(String message) { super(message); }
}
