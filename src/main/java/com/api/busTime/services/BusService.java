package com.api.busTime.services;

import com.api.busTime.dtos.CreateBusDTO;
import com.api.busTime.dtos.UpdateBusDTO;
import com.api.busTime.models.BusModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;

import java.util.stream.Stream;

public interface BusService {
    
    BusModel getById(Long busId);
    
    BusModel create(CreateBusDTO createBusDTO);

    BusModel update(Long busId,UpdateBusDTO updateBusDTO);

    Stream<BusModel> findBusForUser(Pageable pageable, Long userId);
            
    Page<BusModel> listAll(Pageable pageable);
    
    String delete(Long busId);

}
