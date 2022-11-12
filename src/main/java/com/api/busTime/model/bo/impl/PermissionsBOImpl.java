package com.api.busTime.model.bo.impl;

import com.api.busTime.model.bo.PermissionsBO;
import com.api.busTime.model.dao.PermissionsGroupDAO;
import com.api.busTime.model.dtos.PermissionsGroupDTO;
import com.api.busTime.model.entities.PermissionsGroup;
import com.api.busTime.model.entities.UserRoles;
import com.api.busTime.utils.FormatEntityToDTO;
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

    private PermissionsGroupDTO formatEntityToDto(PermissionsGroup permissionsGroup) {
        PermissionsGroupDTO permissionsGroupDTO = new PermissionsGroupDTO();
        BeanUtils.copyProperties(permissionsGroup, permissionsGroupDTO);
        return permissionsGroupDTO;
    }

    @Override
    public ResponseEntity<List<PermissionsGroupDTO>> findAll() {
        return ResponseEntity.ok(FormatEntityToDTO.formatListEntityToListDTO(permissionsGroupDAO.findAll(), PermissionsGroupDTO::new));
    }

    @Override
    public ResponseEntity<PermissionsGroupDTO> findByName(UserRoles permissionName) {
        PermissionsGroup permissionsGroup = this.permissionsGroupDAO.findByName(permissionName);
        return ResponseEntity.ok(formatEntityToDto(permissionsGroup));
    }

    @Override
    public ResponseEntity<PermissionsGroupDTO> findById(int id) {
        PermissionsGroup permissionsGroup = permissionsGroupDAO.findById(id);
        return ResponseEntity.ok(formatEntityToDto(permissionsGroup));
    }
}
