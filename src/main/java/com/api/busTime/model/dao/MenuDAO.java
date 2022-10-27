package com.api.busTime.model.dao;

import com.api.busTime.model.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuDAO extends JpaRepository<Menu, Long> {
}
