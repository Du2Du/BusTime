package com.api.busTime.controllers;

import com.api.busTime.model.dtos.*;
import com.api.busTime.model.entities.Bus;
import com.api.busTime.model.entities.User;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.utils.AdminVerify;
import com.api.busTime.utils.ValidationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/users")
public class UserController {

    private final UsersBO usersBO;

    public UserController(UsersBO usersBO) {
        this.usersBO = usersBO;
    }

    @AdminVerify(validationType = ValidationType.SUPER_ADMIN)
    @GetMapping("")
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        return this.usersBO.findAll(pageable);
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Long id) {
        return this.usersBO.findById(id);
    }

    @GetMapping("/me")
    public UserDTO me() {
        return this.usersBO.me();
    }

    @PostMapping
    public UserDTO create(@RequestBody @Validated CreateUserDTO createUserDTO) {
        return this.usersBO.create(createUserDTO);
    }

    @AdminVerify(validationType = ValidationType.SUPER_ADMIN)
    @PutMapping("/change-admin/{id}")
    public ResponseEntity<UserDTO> setAdminUser(@PathVariable("id") Long userId, @RequestBody @Validated UpdatePermissionDTO updatePermissionDTO) {
        return this.usersBO.setAdminUser(userId, updatePermissionDTO);
    }
    
    @GetMapping("/favorite-buses")
    public ResponseEntity<List<BusDTO>> listFavoriteBuses(){
        return this.usersBO.listFavoriteBuses();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody @Validated UpdateUserDTO updateUserDTO) {
        return this.usersBO.update(id, updateUserDTO);
    }

    @AdminVerify(validationType = ValidationType.SUPER_ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return this.usersBO.delete(id);
    }
}
