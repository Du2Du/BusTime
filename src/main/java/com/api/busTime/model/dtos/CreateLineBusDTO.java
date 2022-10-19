package com.api.busTime.model.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateLineBusDTO {
    @NotBlank
    private String lineName;
}
