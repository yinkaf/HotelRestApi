package com.xipsoft.hotelrestapi.controller;

import com.xipsoft.hotelrestapi.controller.error.ApiError;
import com.xipsoft.hotelrestapi.controller.error.ValidationError;
import com.xipsoft.hotelrestapi.controller.exception.DataNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestController
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ValidationError validationError = new ValidationError(new Date(), "Validation failed for "+ex.getBindingResult().getObjectName(),errors);
        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError error = new ApiError(new Date(),ex.getMessage(),ex.getMessage(),request.getContextPath());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<?> handleDataNotFound(EmptyResultDataAccessException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        ApiError error = new ApiError(new Date(),ex.getLocalizedMessage(),ex.getMessage(),request.getContextPath());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> handleDataNotFound(DataNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        ApiError error = new ApiError(new Date(),ex.getMessage(),ex.getMessage() + " with id "+ex.getId(),request.getContextPath());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
