package com.api.busTime.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateLogMessageDTO {
    
    @NotNull
    private String requisitionStatus;
    @NotBlank
    private String method;
    @NotBlank
    private String url;
    private String userForm;

    public CreateLogMessageDTO(String method, String url) {
        this.method = method;
        this.url = url;
    }
}
