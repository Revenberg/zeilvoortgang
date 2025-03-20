package com.zeilvoortgang.usermanagement.exception;

public class DuplicateAuthorizationException extends RuntimeException {
    public DuplicateAuthorizationException(String message) {
        super(message);
    }
}