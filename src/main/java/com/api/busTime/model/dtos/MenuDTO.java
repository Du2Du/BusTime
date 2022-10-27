package com.api.busTime.model.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MenuDTO {

    @NotBlank
    private Long id;
    @NotBlank
    private String url;
    @NotBlank
    private String iconName;
    @NotBlank
    private String permissionName;
}
