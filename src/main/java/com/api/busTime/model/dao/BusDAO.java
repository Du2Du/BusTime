package com.api.busTime.model.dao;

import com.api.busTime.model.entities.Bus;
import com.api.busTime.model.entities.LogMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusDAO extends PagingAndSortingRepository<Bus, Long> {
    
    @Query("SELECT b FROM Bus b")
    List<Bus> listAllWithoutPage();
    
    @Query("SELECT b FROM Bus b ORDER BY b.createAt DESC")
    Page<Bus> listForDate(Pageable pageable);
    
    @Query("SELECT b FROM Bus b WHERE lower(b.line) like lower(?1) ORDER BY b.createAt DESC")
    Page<Bus> listBusForLine(String line, Pageable pageable);

    @Query("SELECT b FROM Bus b INNER JOIN User u ON b.idUserAdmin = u.id WHERE u.id = ?1 ORDER BY b.createAt DESC")
    List<Bus> listBusForUserId(Long userId);
}   