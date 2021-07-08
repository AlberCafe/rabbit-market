package com.albercafe.rabbitmarket.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String message) { super(message); }
}
