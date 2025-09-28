package com.example.sciencecalc.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResource(NoResourceFoundException ex) {
        // log at WARN so it's visible but not classified as an internal server error
        log.warn("Static resource not found: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(Map.of("error", "Not Found"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String,String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.error("Parameter type mismatch", ex);
        String param = ex.getName();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Invalid value for parameter '" + param + "'"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException ex) {
        log.error("Bad request", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleOther(Exception ex) {
        log.error("Unhandled exception", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Internal server error"));
    }
}
