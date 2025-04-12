package com.ecommerce.ecom.ExceptionHandler;

public class ApiException extends RuntimeException{
    private final String fieldName;

    public ApiException(String fieldName) {
        super("Category with field name: " + fieldName + " already exist!");
        this.fieldName = fieldName;
    }
}
