package com.example.pos.exception;

import com.example.pos.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleDataNotFoundException(DataNotFoundException ex){
        ex.printStackTrace();
        return ResponseEntity.ok(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleServerException(Exception ex){
        ex.printStackTrace();
        return ResponseEntity.ok(ApiResponse.error(ex.getMessage()));
    }
}
