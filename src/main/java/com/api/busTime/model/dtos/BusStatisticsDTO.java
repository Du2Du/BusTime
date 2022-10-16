package com.api.busTime.model.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BusStatisticsDTO {

    @NotBlank
    private Integer busNumber;
    @NotBlank
    private Long busSavedQuantity;
}
