package com.zeilvoortgang.usermanagement.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
 
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<String> handleIllegalArgumentException(DuplicateUserException ex, WebRequest request) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateTeamException.class)
    public ResponseEntity<String> handleIllegalArgumentException(DuplicateTeamException ex, WebRequest request) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateUserTeamException.class)
    public ResponseEntity<String> handleIllegalArgumentException(DuplicateUserTeamException ex, WebRequest request) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateGroupPermissionException.class)
    public ResponseEntity<String> handleIllegalArgumentException(DuplicateGroupPermissionException ex, WebRequest request) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicatePermissionException.class)
    public ResponseEntity<String> handleIllegalArgumentException(DuplicatePermissionException ex, WebRequest request) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateRoleException.class)
    public ResponseEntity<String> handleIllegalArgumentException(DuplicateRoleException ex, WebRequest request) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateGroupException.class)
    public ResponseEntity<String> handleIllegalArgumentException(DuplicateGroupException ex, WebRequest request) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }
    
    @ExceptionHandler(DuplicateOrganizationException.class)
    public ResponseEntity<String> handleIllegalArgumentException(DuplicateOrganizationException ex, WebRequest request) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }
}