package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.PermissionsGroupDTO;
import com.api.busTime.model.entities.PermissionsGroup;
import com.api.busTime.model.entities.UserRoles;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PermissionsBO {

    ResponseEntity<List<PermissionsGroupDTO>> findAll();

    ResponseEntity<PermissionsGroupDTO> findByName(UserRoles permissionName);

    ResponseEntity<PermissionsGroupDTO> findById(int id);
}
