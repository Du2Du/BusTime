package com.api.busTime.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateBusDTO {
    
    @NotNull
    private CreateLineBusDTO lineBus;
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
