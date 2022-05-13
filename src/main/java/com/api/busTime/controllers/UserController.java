package com.api.busTime.controllers;

import com.api.busTime.dtos.CreateUserDTO;
import com.api.busTime.dtos.UpdateUserDTO;
import com.api.busTime.models.UserModel;
import com.api.busTime.services.impl.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserModel findById(@PathVariable Long id){
        return this.userService.findById(id);
    }

    @PostMapping
    public UserModel create(@RequestBody @Validated CreateUserDTO createUserDTO){
        return this.userService.create(createUserDTO);
    }

    @PutMapping("/{id}")
    public UserModel update(@PathVariable Long id, @RequestBody @Validated UpdateUserDTO updateUserDTO){
        return this.userService.update(id, updateUserDTO);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        return this.userService.delete(id);
    }
}
