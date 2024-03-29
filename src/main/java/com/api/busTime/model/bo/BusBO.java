package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BusBO {

    ResponseEntity<BusDTO> getById(Long busId);

    ResponseEntity<BusDTO> create(CreateBusDTO createBusDTO);

    ResponseEntity<BusDTO> update(Long busId, UpdateBusDTO updateBusDTO);

    ResponseEntity<List<BusDTO>> findBusForUser();

    Page<BusDTO> listAll(Pageable pageable);

    Page<BusDTO> listBusForLine(String line, Pageable pageable);

    ResponseEntity<String> delete(Long busId);
    ResponseEntity<List<BusDTO>> favoriteBus(Long busId);

    ResponseEntity<List<BusDTO>> desfavoriteBus(Long busId, Long userId, boolean... skipVerificationUserId);
    
    ResponseEntity<List<StatisticsDTO>> listBusStatistics();

}
