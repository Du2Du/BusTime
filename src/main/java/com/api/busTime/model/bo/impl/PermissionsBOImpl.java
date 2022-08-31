package com.api.busTime.model.bo.impl;

import com.api.busTime.model.bo.PermissionsBO;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dao.PermissionsGroupDAO;
import com.api.busTime.model.entities.PermissionsGroup;
import com.api.busTime.model.entities.User;
import com.api.busTime.model.entities.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionsBOImpl implements PermissionsBO {

    @Autowired
    private PermissionsGroupDAO permissionsGroupDAO;

    @Autowired
    private UsersBO userBO;

    @Override
    public ResponseEntity<List<PermissionsGroup>> findAll() {
        User user = userBO.me();
        if (user.getPermissionsGroup().getName() != UserRoles.SUPER_ADMINISTRATOR)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        
        List<PermissionsGroup> permissionsGroups = permissionsGroupDAO.findAll();

        return ResponseEntity.ok(permissionsGroups);
    }

    @Override
    public ResponseEntity<PermissionsGroup> findById(int id) {
        User user = userBO.me();

        if (user.getPermissionsGroup().getName() != UserRoles.SUPER_ADMINISTRATOR)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();


        PermissionsGroup permissionsGroup = permissionsGroupDAO.findById(id);

        return ResponseEntity.ok(permissionsGroup);
    }
}
