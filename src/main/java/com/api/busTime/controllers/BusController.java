package com.api.busTime.controllers;

import com.api.busTime.dtos.CreateBusDTO;
import com.api.busTime.dtos.UpdateBusDTO;
import com.api.busTime.models.BusModel;
import com.api.busTime.services.BusService;
import com.api.busTime.services.UsersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/bus")
public class BusController {

    private final BusService busService;
    
    private final UsersService usersService;

    public BusController(BusService busService, UsersService usersService) {
        this.busService = busService; this.usersService = usersService;
    }

    @GetMapping("/{id}")
    public BusModel getById(@PathVariable Long id){
        return this.busService.getById(id);
    }
    
    @GetMapping("/user/{id}")
    public Stream<BusModel> findBusForUser(Pageable pageable, @PathVariable Long id){
        return this.busService.findBusForUser(pageable, id);
    }

    @PostMapping
    public BusModel create(@RequestBody @Validated CreateBusDTO createBusDTO) {
        return this.busService.create(createBusDTO);
    } 
    
    @PutMapping("/{id}")
    public BusModel update(@PathVariable Long id, @RequestBody @Validated UpdateBusDTO updateBusDTO) {
        return this.busService.update(id, updateBusDTO);
    }
    
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        return this.busService.delete(id);
    }
    
    @GetMapping
    public Page<BusModel> listAll(Pageable pageable){
        return this.busService.listAll(pageable);
    }
    
}
