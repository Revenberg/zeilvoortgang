package com.zeilvoortgang.usermanagement.exception;

public class DuplicateUserTeamException extends RuntimeException {
    public DuplicateUserTeamException(String message) {
        super(message);
    }
}