package com.api.busTime.model.bo.impl;

import com.api.busTime.model.bo.LogMessageBO;
import com.api.busTime.model.bo.PermissionsBO;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dao.PermissionsGroupDAO;
import com.api.busTime.model.dtos.CreateLogMessageDTO;
import com.api.busTime.model.dtos.PermissionsGroupDTO;
import com.api.busTime.model.entities.PermissionsGroup;
import com.api.busTime.model.entities.User;
import com.api.busTime.model.entities.UserRoles;
import com.api.busTime.utils.AdminVerify;
import com.api.busTime.utils.RequisitionStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionsBOImpl implements PermissionsBO {

    @Autowired
    private PermissionsGroupDAO permissionsGroupDAO;

    @Autowired
    private LogMessageBO logMessageBO;
    
    @Autowired
    private UserBOImpl userBO;
    
    @Override
    public ResponseEntity<List<PermissionsGroupDTO>> findAll() {

        List<PermissionsGroup> permissionsGroups = permissionsGroupDAO.findAll();
        List<PermissionsGroupDTO> permissionsGroupDTOS;
        CreateLogMessageDTO createLogMessageDTO = new CreateLogMessageDTO();
        createLogMessageDTO.setUrl("/api/v1/permissions");
        createLogMessageDTO.setRequisitionStatus(RequisitionStatus.SUCCESS.getValue());
        createLogMessageDTO.setMethod("GET");
        createLogMessageDTO.setMessage("Usuário requisitou todos os registros de permissão");
        createLogMessageDTO.setForm(userBO.me().toString());
        
        permissionsGroupDTOS = permissionsGroups.stream().map((per) -> {
            PermissionsGroupDTO permissionsGroupDTO = new PermissionsGroupDTO();
            BeanUtils.copyProperties(per, permissionsGroupDTO);
            return permissionsGroupDTO;
        }).collect(Collectors.toList());

        logMessageBO.create(createLogMessageDTO);
        
        return ResponseEntity.ok(permissionsGroupDTOS);
    }

    @Override
    public ResponseEntity<PermissionsGroupDTO> findById(int id) {

        PermissionsGroup permissionsGroup = permissionsGroupDAO.findById(id);
        PermissionsGroupDTO permissionsGroupDTO = new PermissionsGroupDTO();
        CreateLogMessageDTO createLogMessageDTO = new CreateLogMessageDTO();
        createLogMessageDTO.setUrl("/api/v1/permissions/{id}");
        createLogMessageDTO.setRequisitionStatus(RequisitionStatus.SUCCESS.getValue());
        createLogMessageDTO.setMethod("GET");
        createLogMessageDTO.setMessage("Usuário requisitou uma permissão pelo ID");
        createLogMessageDTO.setForm(userBO.me().toString());
        
        BeanUtils.copyProperties(permissionsGroup, permissionsGroupDTO);

        return ResponseEntity.ok(permissionsGroupDTO);
    }
}
