package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.CreateBusDTO;
import com.api.busTime.model.dtos.UpdateBusDTO;
import com.api.busTime.model.entities.BusModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BusService {
    
    BusModel getById(Long busId);
    
    BusModel create(CreateBusDTO createBusDTO);

    BusModel update(Long busId,UpdateBusDTO updateBusDTO);

    List<BusModel> findBusForUser(Long userId);
            
    Page<BusModel> listAll(Pageable pageable);

    Page<BusModel> listForLine(String line, Pageable pageable);
    
    String delete(Long busId);

}
