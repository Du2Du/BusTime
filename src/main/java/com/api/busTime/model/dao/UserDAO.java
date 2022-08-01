package com.api.busTime.model.dao;

import com.api.busTime.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
    @Query(name = "email")
    Optional<User> findUserByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.cpf = ?1")
    Optional<User> findUserByCpf(String cpf);
}
