package com.zeilvoortgang.usermanagement.exception;

public class DuplicateGroupException extends RuntimeException {
    public DuplicateGroupException(String message) {
        super(message);
    }
}