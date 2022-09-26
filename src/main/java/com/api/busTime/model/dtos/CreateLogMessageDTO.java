package com.api.busTime.model.dtos;

import com.api.busTime.utils.RequisitionStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateLogMessageDTO {
    
    @NotBlank
    private String message;
    @NotBlank
    private String requisitionStatus;
    @NotBlank
    private String method;
    @NotBlank
    private String url;
    @NotBlank
    private String form;
}
