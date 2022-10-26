package com.api.busTime.model.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateLineBusDTO extends CreateLineBusDTO {
    
    @NotBlank
    private Long id;
}
