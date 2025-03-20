package com.zeilvoortgang.usermanagement.exception;

public class DuplicateAuthorizationGroupException extends RuntimeException {
    public DuplicateAuthorizationGroupException(String message) {
        super(message);
    }
}