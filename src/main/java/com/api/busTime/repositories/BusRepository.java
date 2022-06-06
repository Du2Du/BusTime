package com.api.busTime.repositories;

import com.api.busTime.models.BusModel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BusRepository extends PagingAndSortingRepository<BusModel, Long> {
}
