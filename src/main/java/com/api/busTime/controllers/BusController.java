package com.api.busTime.controllers;

import com.api.busTime.dtos.CreateBusDTO;
import com.api.busTime.dtos.CreateUserDTO;
import com.api.busTime.dtos.UpdateBusDTO;
import com.api.busTime.dtos.UpdateUserDTO;
import com.api.busTime.models.BusModel;
import com.api.busTime.services.BusService;
import com.api.busTime.services.UsersService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/bus")
public class BusController {

    private final BusService busService;
    
    private final UsersService usersService;

    public BusController(BusService busService, UsersService usersService) {
        this.busService = busService; this.usersService = usersService;
    }


    @PostMapping
    public BusModel create(@RequestBody @Validated CreateBusDTO createBusDTO, @CookieValue(name = "accessToken", required = true) String accessToken) {
        Long userId = this.usersService.me().getId();
        return this.busService.create(createBusDTO, userId, accessToken);
    } 
    
    @PutMapping("/{id}")
    public BusModel update(@PathVariable Long id, @RequestBody @Validated UpdateBusDTO updateBusDTO, @CookieValue(name = "accessToken", required = true) String accessToken) {
        Long userId = this.usersService.me().getId();
        return this.busService.update(id, updateBusDTO, userId, accessToken);
    }
    
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, @CookieValue(name = "accessToken", required = true) String accessToken){
        Long userId = this.usersService.me().getId();
        return this.busService.delete(id, userId, accessToken);
    }
}
