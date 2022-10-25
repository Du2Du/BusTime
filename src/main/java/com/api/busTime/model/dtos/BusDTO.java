package com.api.busTime.model.dtos;

import com.api.busTime.model.entities.LineBus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BusDTO{
    
    @NotBlank
    private Long id;
    @NotNull
    private LineBus lineBus;
    @NotBlank
    @JsonFormat(pattern = "HH:mm")
    private String hour;
    @NotNull
    private Float ticketPrice;
    @NotBlank
    private String inicialRoute;
    @NotBlank
    private String finalRoute;
    @NotNull
    private Integer busNumber;
    @NotNull
    private Long idUserAdmin;
    
}
