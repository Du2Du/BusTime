package com.api.busTime.controllers;

import com.api.busTime.model.dtos.BusDTO;
import com.api.busTime.model.dtos.CreateBusDTO;
import com.api.busTime.model.dtos.UpdateBusDTO;
import com.api.busTime.model.bo.BusBO;
import com.api.busTime.utils.AdminVerify;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private final BusBO busBO;
    

    public BusController(BusBO busBO) {
        this.busBO = busBO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusDTO> getById(@PathVariable Long id) {
        return this.busBO.getById(id);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<BusDTO>> findBusForUser(@PathVariable Long id) {
        return this.busBO.findBusForUser(id);
    }

    @GetMapping("/line")
    public Page<BusDTO> listForLine(@RequestParam(name = "line") String line, Pageable pageable) {
        return this.busBO.listForLine(line, pageable);
    }

    @AdminVerify
    @PostMapping
    public ResponseEntity<BusDTO> create(@RequestBody @Validated CreateBusDTO createBusDTO) {
        
        return this.busBO.create(createBusDTO);
    }

    @AdminVerify
    @PutMapping("/{id}")
    public ResponseEntity<BusDTO> update(@PathVariable Long id, @RequestBody @Validated UpdateBusDTO updateBusDTO) {
        return this.busBO.update(id, updateBusDTO);
    }

    @AdminVerify
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return this.busBO.delete(id);
    }

    @GetMapping
    public Page<BusDTO> listAll(Pageable pageable) {
        return this.busBO.listAll(pageable);
    }

}
