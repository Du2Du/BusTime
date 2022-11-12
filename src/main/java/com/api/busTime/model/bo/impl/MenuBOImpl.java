package com.api.busTime.model.bo.impl;

import com.api.busTime.model.bo.MenuBO;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dao.MenuDAO;
import com.api.busTime.model.dtos.MenuDTO;
import com.api.busTime.model.entities.Menu;
import com.api.busTime.utils.FormatEntityToDTO;
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

    private List<MenuDTO> formatListEntityToListDto() {
        List<Menu> menuList = menuDAO.findAll();
        return menuList.stream().map(menu -> {
            MenuDTO menuDTO = FormatEntityToDTO.formatEntityToDto(menu, MenuDTO::new);
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
