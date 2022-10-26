package com.api.busTime.controllers;

import com.api.busTime.model.bo.MenuBO;
import com.api.busTime.model.dtos.MenuDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/menus")
public class MenuController {
    
    @Autowired
    private MenuBO menuBO;
    
    @GetMapping
    public ResponseEntity<List<MenuDTO>> listAllMenus(){
        return menuBO.listMenus();
    }
}
