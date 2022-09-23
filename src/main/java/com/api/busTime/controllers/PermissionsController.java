package com.api.busTime.controllers;

import com.api.busTime.model.bo.impl.PermissionsBOImpl;
import com.api.busTime.model.dtos.PermissionsGroupDTO;
import com.api.busTime.model.entities.PermissionsGroup;
import com.api.busTime.utils.AdminVerify;
import com.api.busTime.utils.ValidationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/permissions")
public class PermissionsController {

    @Autowired
    private PermissionsBOImpl permissionsBOImpl;

    @AdminVerify(validationType = ValidationType.SUPER_ADMIN)
    @GetMapping("")
    public ResponseEntity<List<PermissionsGroupDTO>> findAll(){
      return permissionsBOImpl.findAll();
    };

    @AdminVerify(validationType = ValidationType.SUPER_ADMIN)
    @GetMapping("/{id}")
    public ResponseEntity<PermissionsGroupDTO> findById(@PathVariable int id){
        return permissionsBOImpl.findById(id);
    };
}
