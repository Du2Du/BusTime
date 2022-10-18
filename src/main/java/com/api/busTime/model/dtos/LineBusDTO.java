package com.api.busTime.model.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LineBusDTO {

    @NotBlank
    private Long id;
    
    @NotBlank
    private String lineName;
    private Long savedQuantity;
}
