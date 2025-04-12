package com.ecommerce.ecom.ExceptionHandler;

public class ResourceNotFoundException extends RuntimeException{
    private String fieldName;
    private String fieldMessage;
    private Long fieldId;

    public ResourceNotFoundException(String fieldName, Long fieldId) {
        super(String.format("%s not found with id: %d", fieldName, fieldId));
        this.fieldName = fieldName;
        this.fieldId = fieldId;
    }

    public ResourceNotFoundException(String fieldMessage){
        super(fieldMessage);
        this.fieldMessage = fieldMessage;
    }
}
