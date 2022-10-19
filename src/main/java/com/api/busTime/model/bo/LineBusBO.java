package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.CreateLineBusDTO;
import com.api.busTime.model.dtos.LineBusDTO;
import com.api.busTime.model.dtos.UpdateLineBusDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LineBusBO {

    ResponseEntity<LineBusDTO> getById(Long lineId);

    ResponseEntity<LineBusDTO> create(CreateLineBusDTO lineBusDTO);

    ResponseEntity<LineBusDTO> update(Long lineId, UpdateLineBusDTO updateBusDTO);
    
    ResponseEntity<LineBusDTO> getLineByName(String lineName);
    
//    ResponseEntity<List<LineBusDTO>> listBusStatistics();

}
