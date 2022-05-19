package com.api.busTime.controllers;

import com.api.busTime.dtos.CreateUserDTO;
import com.api.busTime.dtos.UpdateUserDTO;
import com.api.busTime.models.UserModel;
import com.api.busTime.services.UsersService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/users")
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
