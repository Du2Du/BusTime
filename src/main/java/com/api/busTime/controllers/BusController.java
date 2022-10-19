package com.api.busTime.controllers;

import com.api.busTime.model.bo.BusBO;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dtos.*;
import com.api.busTime.utils.AdminVerify;
import com.api.busTime.utils.ValidationType;
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
    private BusBO busBO;

    @Autowired
    private UsersBO userBO;
    

    public BusController(BusBO busBO) {
        this.busBO = busBO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusDTO> getById(@PathVariable Long id) {
        return this.busBO.getById(id);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<BusDTO>> findBusForUser(@PathVariable Long id) {
        return this.busBO.findBusForUser(id, userBO);
    }

    @GetMapping("/line")
    public Page<BusDTO> listBusForLine(@RequestParam(name = "line") String line, Pageable pageable) {
        return this.busBO.listBusForLine(line, pageable);
    }

    @AdminVerify
    @PostMapping
    public ResponseEntity<BusDTO> create(@RequestBody @Validated CreateBusDTO createBusDTO) {
        
        return this.busBO.create(createBusDTO);
    }

    @AdminVerify
    @PutMapping("/{id}")
    public ResponseEntity<BusDTO> update(@PathVariable Long id, @RequestBody @Validated UpdateBusDTO updateBusDTO) {
        return this.busBO.update(id, updateBusDTO, userBO);
    }

    @AdminVerify
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return this.busBO.delete(id, userBO);
    }

    @GetMapping
    public Page<BusDTO> listAll(Pageable pageable) {
        return this.busBO.listAll(pageable);
    }

    @AdminVerify(validationType = ValidationType.SUPER_ADMIN)
    @GetMapping
    public ResponseEntity<List<StatisticsDTO>> listLineStatistics() {
        return this.busBO.listBusStatistics();
    }
}
