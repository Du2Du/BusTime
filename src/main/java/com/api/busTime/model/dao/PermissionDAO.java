package com.api.busTime.model.dao;

import com.api.busTime.model.entities.Permission;
import com.api.busTime.model.entities.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionDAO extends JpaRepository<Permission, Integer> {
    @Query("SELECT p FROM Permission p WHERE p.id = ?1")
    Permission findByIdExsits(int id);
}
