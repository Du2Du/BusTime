package com.api.busTime.controllers;

import com.api.busTime.dtos.CreateUserDTO;
import com.api.busTime.models.UserModel;
import com.api.busTime.services.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/users")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserModel create(@RequestBody @Validated CreateUserDTO createUserDTO){
        System.out.println("sexo");
        return this.userService.create(createUserDTO);
    }
}
