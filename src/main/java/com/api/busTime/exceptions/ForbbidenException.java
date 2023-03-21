package com.api.busTime.exceptions;

public class ForbbidenException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ForbbidenException(String message) {
        super(message);
    }
}
