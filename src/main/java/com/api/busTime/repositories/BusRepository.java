package com.api.busTime.repositories;

import com.api.busTime.models.BusModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BusRepository extends PagingAndSortingRepository<BusModel, Long> {
    @Query("SELECT b FROM BusModel b WHERE b.line = ?1")
    Page<BusModel> listBusForLine(String line, Pageable pageable);

    @Query("SELECT b FROM BusModel b WHERE b.idUserAdmin = ?1")
    List<BusModel> listBusForUserId(Long userId);
}
