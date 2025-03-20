package com.zeilvoortgang.usermanagement.exception;

public class DuplicateTeamException extends RuntimeException {
    public DuplicateTeamException(String message) {
        super(message);
    }
}