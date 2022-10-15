package com.api.busTime.model.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BusDTO extends CreateBusDTO{
    
    @NotBlank
    private Long id;
    
    
}
