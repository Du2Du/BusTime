package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.MenuDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MenuBO {

    ResponseEntity<List<MenuDTO>> listMenus(); 
}
