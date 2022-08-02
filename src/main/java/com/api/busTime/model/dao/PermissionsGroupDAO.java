package com.api.busTime.model.dao;

import com.api.busTime.model.entities.PermissionsGroup;
import com.api.busTime.model.entities.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsGroupDAO extends JpaRepository<PermissionsGroup, Integer> {
    @Query("SELECT p FROM PermissionsGroup p WHERE p.name = ?1")
    PermissionsGroup findByName(UserRoles userRoles);
}
