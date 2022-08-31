package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.CreateBusDTO;
import com.api.busTime.model.dtos.UpdateBusDTO;
import com.api.busTime.model.entities.Bus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BusBO {
    
    ResponseEntity<Bus> getById(Long busId);

    ResponseEntity<Bus> create(CreateBusDTO createBusDTO);

    ResponseEntity<Bus> update(Long busId, UpdateBusDTO updateBusDTO);

    ResponseEntity<List<Bus>> findBusForUser(Long userId);
            
    Page<Bus> listAll(Pageable pageable);

    Page<Bus> listForLine(String line, Pageable pageable);

    ResponseEntity<String> delete(Long busId);

}
