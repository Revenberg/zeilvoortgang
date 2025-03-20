package com.zeilvoortgang.education.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class EducationGlobalExceptionHandler {
/*
    @ExceptionHandler(DuplicateLeerlijnException.class)
    public ResponseEntity<String> handleIllegalArgumentException(DuplicateLeerlijnException ex, WebRequest request) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateLesStatussenException.class)
    public ResponseEntity<String> handleIllegalArgumentException(DuplicateLesStatussenException ex, WebRequest request) {
        return ResponseEntity
                .status(409)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateLesSequenceException.class)
    public ResponseEntity<String> handleDuplicateLesSequenceException(DuplicateLesSequenceException ex, WebRequest request) {
        return ResponseEntity
                .status(409)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateLesException.class)
    public ResponseEntity<String> handleDuplicateLesException(DuplicateLesException ex, WebRequest request) {
        return ResponseEntity
                .status(409)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateLesCursistenException.class)
    public ResponseEntity<String> handleDuplicateLesCursistenException(DuplicateLesCursistenException ex, WebRequest request) {
        return ResponseEntity
                .status(409)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateLesMethodiekenException.class)
    public ResponseEntity<String> handleDuplicateLesMethodiekenException(DuplicateLesMethodiekenException ex, WebRequest request) {
        return ResponseEntity
                .status(409)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateLesTrainersException.class)
    public ResponseEntity<String> handleDuplicateLesTrainersException(DuplicateLesTrainersException ex, WebRequest request) {
        return ResponseEntity
                .status(409)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateUserLevelsException.class)
    public ResponseEntity<String> handleDuplicateUserLevelsException(DuplicateUserLevelsException ex, WebRequest request) {
        return ResponseEntity
                .status(409)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateVoortgangStatussenException.class)
    public ResponseEntity<String> handleDuplicateVoortgangStatussenException(DuplicateVoortgangStatussenException ex, WebRequest request) {
        return ResponseEntity
                .status(409)
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex, WebRequest request) {
        return ResponseEntity
                .status(500)
                .body(ex.getMessage());
    }
 */
    }