package com.api.busTime.repositories;

import com.api.busTime.models.BusModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<BusModel, Long> {
}
