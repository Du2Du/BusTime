package com.api.busTime.exceptions;

public class TooManyRequests extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TooManyRequests(String message) {
        super(message);
    }

    {
    }
}
