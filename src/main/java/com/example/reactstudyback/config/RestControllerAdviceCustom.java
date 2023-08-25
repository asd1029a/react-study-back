package com.example.reactstudyback.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * RestControllerAdvice.java
 * Class 설명을 작성하세요.
 *
 * @author kjm
 * @since 2023.08.24
 */
@RestControllerAdvice
@CrossOrigin(originPatterns = "http://localhost:3001")
public class RestControllerAdviceCustom {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> exceptionA(Throwable ex) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("오류",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }
}
