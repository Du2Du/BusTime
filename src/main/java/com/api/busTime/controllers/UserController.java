package com.api.busTime.controllers;

import com.api.busTime.model.dtos.CreateUserDTO;
import com.api.busTime.model.dtos.UpdateUserDTO;
import com.api.busTime.model.entities.Bus;
import com.api.busTime.model.entities.User;
import com.api.busTime.model.bo.UsersBO;
import org.springframework.data.jpa.repository.Query;
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

    @GetMapping("")
    public ResponseEntity<List<User>> findAll() {
        return this.usersBO.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return this.usersBO.findById(id);
    }

    @GetMapping("/me")
    public User me() {
        return this.usersBO.me();
    }

    @PostMapping
    public User create(@RequestBody @Validated CreateUserDTO createUserDTO) {
        return this.usersBO.create(createUserDTO);
    }

            @GetMapping("/favorite-bus/{id}")
    public List<Bus> favoriteBus(@PathVariable("id") Long userId, @RequestParam("bus") Long busId) {
        return this.usersBO.favoriteBus(busId, userId);
    }  
    
    @GetMapping("/change-admin/{id}")
    public ResponseEntity<User> setAdminUser(@PathVariable("id") Long userId, @RequestParam("admin") boolean isAdmin) {
        return this.usersBO.setAdminUser(userId, isAdmin);
    }
    
    @GetMapping("/desfavorite-bus/{id}")
    public List<Bus> desfavoriteBus(@PathVariable("id") Long userId, @RequestParam("bus") Long busId) {
        return this.usersBO.desfavoriteBus(busId, userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Validated UpdateUserDTO updateUserDTO) {
        return this.usersBO.update(id, updateUserDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return this.usersBO.delete(id);
    }
}
