package com.api.busTime.model.dao;

import com.api.busTime.model.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    @Query(name = "email")
    Optional<UserModel> findUserByEmail(String email);
    
    @Query("SELECT u FROM UserModel u WHERE u.cpf = ?1")
    Optional<UserModel> findUserByCpf(String cpf);
}
