package com.api.busTime.model.bo.impl;

import com.api.busTime.model.bo.BusBO;
import com.api.busTime.model.bo.MenuBO;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dao.MenuDAO;
import com.api.busTime.model.dtos.MenuDTO;
import com.api.busTime.model.dtos.UserDTO;
import com.api.busTime.model.entities.Menu;
import com.api.busTime.model.entities.Permission;
import com.api.busTime.model.entities.UserRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MenuBOImpl implements MenuBO {

    @Autowired
    private MenuDAO menuDAO;

    @Autowired
    private UsersBO usersBO;

    @Override
    public ResponseEntity<List<MenuDTO>> listMenus() {
        List<Menu> menuList = menuDAO.findAll();

        List<MenuDTO> menuDTOList = menuList.stream().map(menu -> {
            MenuDTO menuDTO = new MenuDTO();
            BeanUtils.copyProperties(menu, menuDTO);

            if (menu.getPermissionName().equals("")) return menuDTO;
            if (!usersBO.verifyPermission(menu.getPermissionName())) return null;

            return menuDTO;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        return ResponseEntity.ok(menuDTOList);
    }
}
