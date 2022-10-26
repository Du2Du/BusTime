package com.api.busTime.model.dao;

import com.api.busTime.model.entities.LogMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogMessageDAO extends JpaRepository<LogMessage, Long> {

    @Query("SELECT l FROM LogMessage l ORDER BY l.time DESC")
    Page<LogMessage> listForDate(Pageable pageable);
}
