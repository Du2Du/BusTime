package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.PermissionsGroupDTO;
import com.api.busTime.model.entities.PermissionsGroup;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PermissionsBO {

    ResponseEntity<List<PermissionsGroupDTO>> findAll();

    ResponseEntity<PermissionsGroupDTO> findById(int id);
}
