package com.api.busTime.model.dao;

import com.api.busTime.model.entities.LogMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogMessageDAO extends JpaRepository<LogMessage, Long> {
}
