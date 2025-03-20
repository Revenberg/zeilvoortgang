package com.zeilvoortgang.usermanagement.exception;

public class DuplicateAuthorizationRoleException extends RuntimeException {
    public DuplicateAuthorizationRoleException(String message) {
        super(message);
    }
}