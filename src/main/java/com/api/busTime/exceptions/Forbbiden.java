package com.api.busTime.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class Forbbiden extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public Forbbiden(String message) {
        super(message);
    }
}
