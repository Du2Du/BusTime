package com.api.busTime.model.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BusStatisticsDTO {

    @NotBlank
    private String lineName;
    @NotBlank
    private Long savedQuantity;
}
