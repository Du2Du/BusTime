package com.api.busTime.services;

import com.api.busTime.dtos.CreateBusDTO;
import com.api.busTime.dtos.UpdateBusDTO;
import com.api.busTime.models.BusModel;

import java.util.List;

public interface BusService {
    
    BusModel create(CreateBusDTO createBusDTO, Long userId);

    //BusModel findBus(CreateBusDTO createBusDTO);
    
    BusModel update(Long busId,UpdateBusDTO updateBusDTO, Long userId);
    
    List<BusModel> listAll();
    
    String delete(Long busId, Long userId);

}
