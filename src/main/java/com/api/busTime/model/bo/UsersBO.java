package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.*;
import com.api.busTime.model.entities.Bus;
import com.api.busTime.model.entities.User;
import com.api.busTime.model.entities.UserRoles;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsersBO {
    User create(CreateUserDTO userDTO);

    User findById(Long userId);

    ResponseEntity<List<User>> findAll();
    
    ResponseEntity<User> setAdminUser(Long userId,  UpdatePermissionDTO updatePermissionDTO);

    List<Bus> favoriteBus(Long busId, Long userId);

    List<Bus> desfavoriteBus(Long busId, Long userId);
    
    User me();

    ResponseEntity<String> delete(Long id);

    ResponseEntity<User> update(Long id, UpdateUserDTO updateUserDTO);

    ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken);

    ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken);
}
