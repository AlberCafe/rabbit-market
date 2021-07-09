package com.albercafe.rabbitmarket.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) { super(message); }
}
