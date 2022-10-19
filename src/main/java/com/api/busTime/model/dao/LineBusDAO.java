package com.api.busTime.model.dao;

import com.api.busTime.model.entities.LineBus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LineBusDAO extends JpaRepository<LineBus, Long> {
    
    @Query("SELECT l FROM LineBus l where lower(l.lineName) = lower(?1)")
    Optional<LineBus> findLineForName(String name);
}
