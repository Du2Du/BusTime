package com.api.busTime.services;

import com.api.busTime.dtos.CreateBusDTO;
import com.api.busTime.dtos.UpdateBusDTO;
import com.api.busTime.models.BusModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BusService {
    
    BusModel getById(Long busId);
    
    BusModel create(CreateBusDTO createBusDTO, Long userId);

    BusModel update(Long busId,UpdateBusDTO updateBusDTO, Long userId);
    
    Page<BusModel> listAll(Pageable pageable);
    
    String delete(Long busId, Long userId);

}
