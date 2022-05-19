package com.api.busTime.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateBusDTO {
    
    @NotBlank
    private String line;
    @NotBlank
    @JsonFormat(pattern = "HH:mm")
    private String hour;
    @NotBlank
    private Float ticketPrice;
    @NotBlank
    private String inicialRoute;
    @NotBlank
    private String finalRoute;
    @NotBlank
    private Integer busNumber;
    @NotBlank
    private Long idUserAdmin;
}
