package com.zeilvoortgang.usermanagement.exception;

public class DuplicateTeamPermissionException extends RuntimeException {
    public DuplicateTeamPermissionException(String message) {
        super(message);
    }
}