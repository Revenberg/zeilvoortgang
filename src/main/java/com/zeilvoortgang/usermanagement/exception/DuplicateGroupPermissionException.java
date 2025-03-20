package com.zeilvoortgang.usermanagement.exception;

public class DuplicateGroupPermissionException extends RuntimeException {
    public DuplicateGroupPermissionException(String message) {
        super(message);
    }
}