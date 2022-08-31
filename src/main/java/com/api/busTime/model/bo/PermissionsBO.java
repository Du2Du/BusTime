package com.api.busTime.model.bo;

import com.api.busTime.model.entities.PermissionsGroup;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PermissionsBO {

    ResponseEntity<List<PermissionsGroup>> findAll();

    ResponseEntity<PermissionsGroup> findById(int id);
}
