package com.api.busTime.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private SuccessFailure status;
    private String message;

    public enum SuccessFailure {
        SUCCESS,
        FAILURE
    }
}
