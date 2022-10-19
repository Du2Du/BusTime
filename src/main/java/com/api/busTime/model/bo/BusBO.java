package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BusBO {

    ResponseEntity<BusDTO> getById(Long busId);

    ResponseEntity<BusDTO> create(CreateBusDTO createBusDTO);

    ResponseEntity<BusDTO> update(Long busId, UpdateBusDTO updateBusDTO, UsersBO userBO);

    ResponseEntity<List<BusDTO>> findBusForUser(Long userId, UsersBO userBO);

    Page<BusDTO> listAll(Pageable pageable);

    Page<BusDTO> listBusForLine(String line, Pageable pageable);

    ResponseEntity<String> delete(Long busId, UsersBO userBO);

    ResponseEntity<List<StatisticsDTO>> listBusStatistics();

}
