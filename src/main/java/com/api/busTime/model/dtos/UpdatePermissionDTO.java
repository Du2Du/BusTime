package com.api.busTime.model.dtos;

import com.api.busTime.model.entities.UserRoles;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdatePermissionDTO {
    
    @NotNull UserRoles permissionGroup;
}
