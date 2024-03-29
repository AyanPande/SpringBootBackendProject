package com.electronic.store.exceptions;

import com.electronic.store.dtos.ApiResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message(ex.getMessage()).success(true).status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(responseMessage,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String,Object> response = new HashMap<>();
        allErrors.stream().forEach(objectError -> {
            String message = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field,message);
        });
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponseMessage> badApiRequestExceptionHandler(BadApiRequestException ex) {
        ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).success(false).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}