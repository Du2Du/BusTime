package com.api.busTime.repositories;

import com.api.busTime.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    @Query(name = "email")
    Optional<UserModel> findUserByEmail(String email);
}
