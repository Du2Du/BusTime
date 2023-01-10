package com.api.busTime.model.dao;

import com.api.busTime.model.dtos.UserDTO;
import com.api.busTime.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT u FROM User u ORDER BY u.id ASC")
    Page<User> listAllForId(Pageable pageable);

    @Query("SELECT secret2FACode FROM User u WHERE u.id = ?1")
    String getSecret2FACode(Long id);
}
