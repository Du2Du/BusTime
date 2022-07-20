package com.api.busTime.controllers;

import com.api.busTime.model.dtos.CreateUserDTO;
import com.api.busTime.model.dtos.UpdateUserDTO;
import com.api.busTime.model.entities.UserModel;
import com.api.busTime.model.bo.UsersService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/users")
public class UserController {

    private final UsersService userService;

    public UserController(UsersService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserModel findById(@PathVariable Long id){
        return this.userService.findById(id);
    }

    @GetMapping("/me")
    public UserModel me() {
        return this.userService.me();
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
