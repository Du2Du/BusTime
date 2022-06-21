package com.api.busTime.services;

import com.api.busTime.dtos.CreateBusDTO;
import com.api.busTime.dtos.UpdateBusDTO;
import com.api.busTime.models.BusModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;

import java.util.List;
import java.util.stream.Stream;

public interface BusService {
    
    BusModel getById(Long busId);
    
    BusModel create(CreateBusDTO createBusDTO);

    BusModel update(Long busId,UpdateBusDTO updateBusDTO);

    List<BusModel> findBusForUser(Long userId);
            
    Page<BusModel> listAll(Pageable pageable);

    Page<BusModel> listForLine(String line, Pageable pageable);
    
    String delete(Long busId);

}
