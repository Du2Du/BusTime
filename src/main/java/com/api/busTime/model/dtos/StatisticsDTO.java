package com.api.busTime.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class StatisticsDTO {
    @NotBlank
    private int savedQuantity;
    @NotBlank String lineName;
}
