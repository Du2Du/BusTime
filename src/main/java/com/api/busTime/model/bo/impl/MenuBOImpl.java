package com.api.busTime.model.bo.impl;

import com.api.busTime.model.bo.MenuBO;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dao.MenuDAO;
import com.api.busTime.model.dtos.MenuDTO;
import com.api.busTime.model.entities.Menu;
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

    private MenuDTO formatEntityToDto(Menu menu) {
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(menu, menuDTO);
        return menuDTO;
    }

    private List<MenuDTO> formatListEntityToListDto() {
        List<Menu> menuList = menuDAO.findAll();
        return menuList.stream().map(menu -> {
            MenuDTO menuDTO = formatEntityToDto(menu);
            if (menu.getPermissionName().equals("")) return menuDTO;
            if (!usersBO.verifyPermission(menu.getPermissionName())) return null;
            return menuDTO;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<List<MenuDTO>> listMenus() {
        return ResponseEntity.ok(formatListEntityToListDto());
    }
}
