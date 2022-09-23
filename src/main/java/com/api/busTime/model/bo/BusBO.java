package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.BusDTO;
import com.api.busTime.model.dtos.CreateBusDTO;
import com.api.busTime.model.dtos.UpdateBusDTO;
import com.api.busTime.model.entities.Bus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BusBO {
    
    ResponseEntity<BusDTO> getById(Long busId);

    ResponseEntity<BusDTO> create(CreateBusDTO createBusDTO);

    ResponseEntity<BusDTO> update(Long busId, UpdateBusDTO updateBusDTO);

    ResponseEntity<List<BusDTO>> findBusForUser(Long userId);
            
    Page<BusDTO> listAll(Pageable pageable);

    Page<BusDTO> listForLine(String line, Pageable pageable);

    ResponseEntity<String> delete(Long busId);

}
