package com.api.busTime.model.bo.impl;

import com.api.busTime.model.bo.PermissionsBO;
import com.api.busTime.model.dao.PermissionDAO;
import com.api.busTime.model.dao.PermissionsGroupDAO;
import com.api.busTime.model.entities.PermissionsGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionsBOImpl implements PermissionsBO {

    @Autowired
    private PermissionsGroupDAO permissionsGroupDAO;

    @Override
    public List<PermissionsGroup> findAll(){
        List<PermissionsGroup> permissionsGroups = permissionsGroupDAO.findAll();

        return permissionsGroups;
    }

    @Override
    public PermissionsGroup findById(int id){
         PermissionsGroup permissionsGroup = permissionsGroupDAO.findById(id);

        return permissionsGroup;
    }
}
