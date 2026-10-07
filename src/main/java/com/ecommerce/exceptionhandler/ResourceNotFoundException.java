package com.ecommerce.exceptionhandler;

public class ResourceNotFoundException extends RuntimeException{
    private static final String message = "Resource not found for field: %s";

    public ResourceNotFoundException(String field){
        super(String.format(message,field));
    }
}
