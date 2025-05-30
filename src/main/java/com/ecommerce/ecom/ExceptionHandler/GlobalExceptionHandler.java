package com.ecommerce.ecom.ExceptionHandler;
import com.ecommerce.ecom.Payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e){
            Map<String, String> response = new HashMap<>();

            e.getBindingResult().getFieldErrors().forEach( err -> {
                String fieldName = err.getField();
                String fieldMessage = err.getDefaultMessage();
                response.put(fieldName, fieldMessage);
            });

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException e){
        String response = e.getMessage();
        APIResponse apiResponse = new APIResponse(response, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<APIResponse> myApiException(ApiException e){
        String response = e.getMessage();
        APIResponse apiResponse = new APIResponse(response, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
