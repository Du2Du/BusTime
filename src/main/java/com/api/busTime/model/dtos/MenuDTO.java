package com.api.busTime.model.dtos;

import com.api.busTime.model.entities.UserRoles;
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
    private UserRoles permissionGroupName;
}
