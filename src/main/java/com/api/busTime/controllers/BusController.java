package com.api.busTime.controllers;

import com.api.busTime.model.dtos.CreateBusDTO;
import com.api.busTime.model.dtos.UpdateBusDTO;
import com.api.busTime.model.entities.Bus;
import com.api.busTime.model.bo.BusBO;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.utils.AdminVerify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/bus")
public class BusController {

    private final BusBO busBO;
    
    private final UsersBO usersBO;

    public BusController(BusBO busBO, UsersBO usersBO) {
        this.busBO = busBO; this.usersBO = usersBO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bus> getById(@PathVariable Long id){
        return this.busBO.getById(id);
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Bus>> findBusForUser(@PathVariable Long id){
        return this.busBO.findBusForUser(id);
    }
    
    @GetMapping("/line")
    public Page<Bus> listForLine(@RequestParam(name = "line") String line, Pageable pageable){
        return this.busBO.listForLine(line, pageable);
    }

    @AdminVerify
    @PostMapping
    public ResponseEntity<Bus> create(@RequestBody @Validated CreateBusDTO createBusDTO) {
        return this.busBO.create(createBusDTO);
    }

    @AdminVerify
    @PutMapping("/{id}")
    public ResponseEntity<Bus> update(@PathVariable Long id, @RequestBody @Validated UpdateBusDTO updateBusDTO) {
        return this.busBO.update(id, updateBusDTO);
    }

    @AdminVerify
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return this.busBO.delete(id);
    }
    
    @GetMapping
    public Page<Bus> listAll(Pageable pageable){
        return this.busBO.listAll(pageable);
    }
    
}
