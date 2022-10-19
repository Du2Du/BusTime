package com.api.busTime.model.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StatisticsDTO {
    @NotBlank
    private int savedQuantity;
    @NotBlank String lineName;
}
