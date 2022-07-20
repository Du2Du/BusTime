package com.api.busTime.exceptions;

public class Forbbiden extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public Forbbiden(String message) {
        super(message);
    }
}
