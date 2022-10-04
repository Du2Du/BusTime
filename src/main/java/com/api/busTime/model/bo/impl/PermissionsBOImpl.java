package com.api.busTime.model.bo.impl;

import com.api.busTime.model.bo.PermissionsBO;
import com.api.busTime.model.dao.PermissionsGroupDAO;
import com.api.busTime.model.dtos.PermissionsGroupDTO;
import com.api.busTime.model.entities.PermissionsGroup;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionsBOImpl implements PermissionsBO {

    @Autowired
    private PermissionsGroupDAO permissionsGroupDAO;
    
    @Override
    public ResponseEntity<List<PermissionsGroupDTO>> findAll() {

        List<PermissionsGroup> permissionsGroups = permissionsGroupDAO.findAll();
        List<PermissionsGroupDTO> permissionsGroupDTOS;
        
        permissionsGroupDTOS = permissionsGroups.stream().map((per) -> {
            PermissionsGroupDTO permissionsGroupDTO = new PermissionsGroupDTO();
            BeanUtils.copyProperties(per, permissionsGroupDTO);
            return permissionsGroupDTO;
        }).collect(Collectors.toList());
        
        return ResponseEntity.ok(permissionsGroupDTOS);
    }

    @Override
    public ResponseEntity<PermissionsGroupDTO> findById(int id) {

        PermissionsGroup permissionsGroup = permissionsGroupDAO.findById(id);
        PermissionsGroupDTO permissionsGroupDTO = new PermissionsGroupDTO();
        
        BeanUtils.copyProperties(permissionsGroup, permissionsGroupDTO);

        return ResponseEntity.ok(permissionsGroupDTO);
    }
}
