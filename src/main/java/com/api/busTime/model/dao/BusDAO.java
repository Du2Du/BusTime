package com.api.busTime.model.dao;

import com.api.busTime.model.entities.Bus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BusDAO extends PagingAndSortingRepository<Bus, Long> {
    @Query("SELECT b FROM Bus b WHERE lower(b.line) like lower(?1)")
    Page<Bus> listBusForLine(String line, Pageable pageable);

    @Query("SELECT b FROM Bus b INNER JOIN User u ON b.idUserAdmin = u.id WHERE u.id = ?1")
    List<Bus> listBusForUserId(Long userId);
}
