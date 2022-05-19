package com.api.busTime.services;

import com.api.busTime.dtos.CreateBusDTO;
import com.api.busTime.dtos.UpdateBusDTO;
import com.api.busTime.models.BusModel;

public interface BusService {
    
    BusModel create(CreateBusDTO createBusDTO, Long userId, String accessToken);

    //BusModel findBus(CreateBusDTO createBusDTO, String accessToken,);
    
    BusModel update(Long busId,UpdateBusDTO updateBusDTO, Long userId, String accessToken);
    
    String delete(Long busId, Long userId, String accessToken);

}
