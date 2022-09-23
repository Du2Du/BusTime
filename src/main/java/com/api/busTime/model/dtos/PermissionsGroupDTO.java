package com.api.busTime.model.dtos;

import com.api.busTime.model.entities.Permission;
import com.api.busTime.model.entities.UserRoles;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PermissionsGroupDTO {
    @NotNull
    private int id;

    @NotNull
    private UserRoles name;

    @NotNull
    private List<Permission> permissionList;
}
