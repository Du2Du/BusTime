package com.api.busTime.controllers;

import com.api.busTime.model.bo.impl.PermissionsBOImpl;
import com.api.busTime.model.entities.PermissionsGroup;
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

    @GetMapping("")
    public List<PermissionsGroup> findAll(){
      return permissionsBOImpl.findAll();
    };

    @GetMapping("/{id}")
    public PermissionsGroup findById(@PathVariable int id){
        return permissionsBOImpl.findById(id);
    };
}
