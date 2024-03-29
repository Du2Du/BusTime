package com.api.busTime.model.dao;

import com.api.busTime.model.entities.Bus;
import com.api.busTime.model.entities.LogMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusDAO extends PagingAndSortingRepository<Bus, Long> {

    @Query("SELECT b FROM Bus b ORDER BY b.createAt DESC")
    Page<Bus> listForDate(Pageable pageable);

    @Query(value = "SELECT COUNT(b.id) FROM bus b INNER JOIN users_favorite_bus fb ON fb.favorite_bus_id = b.id WHERE b.line_bus_id = ?1", nativeQuery = true)
    int listLineBusFavorited(Long lineId);

    @Query("SELECT b FROM Bus b INNER JOIN LineBus l ON b.lineBus.id = l.id WHERE lower(l.lineName) LIKE CONCAT(lower(?1),'%')")
    Page<Bus> listBusForLine(String line, Pageable pageable);

    @Query("SELECT b FROM Bus b WHERE b.busNumber = ?1")
    Optional<Bus> listBusForNumber(Integer busNumber);

    @Query("SELECT b FROM Bus b INNER JOIN User u ON b.idUserAdmin = u.id WHERE u.id = ?1 ORDER BY b.createAt DESC")
    List<Bus> listBusForUserId(Long userId);
}   