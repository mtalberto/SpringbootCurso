package com.curso.javaspringboot.controllers;

import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.curso.javaspringboot.services.ApiError;

public class CustomExceptionHandler  extends ResponseEntityExceptionHandler {
 @SuppressWarnings("null")
@ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR, 
            ex.getLocalizedMessage(), 
            Collections.singletonList("Error occurred"));
        return new ResponseEntity<Object>(
            apiError, new HttpHeaders(), apiError.getStatus());
    }
}
