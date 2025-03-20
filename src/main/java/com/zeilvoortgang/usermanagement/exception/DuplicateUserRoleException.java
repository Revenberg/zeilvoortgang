package com.zeilvoortgang.usermanagement.exception;

public class DuplicateUserRoleException extends RuntimeException {
    public DuplicateUserRoleException(String message) {
        super(message);
    }
}