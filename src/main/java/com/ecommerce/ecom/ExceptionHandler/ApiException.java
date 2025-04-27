package com.ecommerce.ecom.ExceptionHandler;

public class ApiException extends RuntimeException{
    private final String fieldName;

    public ApiException(String fieldName) {
        super("Item with name: " + fieldName + " already exist!");
        this.fieldName = fieldName;
    }

    public ApiException(String message, String fieldName){
        super(message);
        this.fieldName = fieldName;
    }


}
