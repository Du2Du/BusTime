package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.CreateBusDTO;
import com.api.busTime.model.dtos.UpdateBusDTO;
import com.api.busTime.model.entities.Bus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BusBO {
    
    Bus getById(Long busId);
    
    Bus create(CreateBusDTO createBusDTO);

    Bus update(Long busId, UpdateBusDTO updateBusDTO);

    List<Bus> findBusForUser(Long userId);
            
    Page<Bus> listAll(Pageable pageable);

    Page<Bus> listForLine(String line, Pageable pageable);
    
    String delete(Long busId);

}
