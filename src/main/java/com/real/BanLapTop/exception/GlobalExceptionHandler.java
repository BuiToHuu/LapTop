package com.real.BanLapTop.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.HashMap;

import org.springframework.context.ApplicationContextException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationContextException.class)
    public ResponseEntity<Map<String, Object>> handleAppException(ApplicationContextException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Not Found");
        response.put("message", exception.getMessage()); 
        
        return ResponseEntity.status(404).body(response);
    }
}