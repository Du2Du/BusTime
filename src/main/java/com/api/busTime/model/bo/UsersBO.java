package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.CreateUserDTO;
import com.api.busTime.model.dtos.LoginRequest;
import com.api.busTime.model.dtos.LoginResponse;
import com.api.busTime.model.dtos.UpdateUserDTO;
import com.api.busTime.model.entities.Bus;
import com.api.busTime.model.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsersBO {
    User create(CreateUserDTO userDTO);

    User findById(Long userId);

    ResponseEntity<List<User>> findAll();
    
    ResponseEntity<User> setAdminUser(Long userId, boolean isAdmin);

    List<Bus> favoriteBus(Long busId, Long userId);

    List<Bus> desfavoriteBus(Long busId, Long userId);
    
    User me();

    String delete(Long id);

    User update(Long id, UpdateUserDTO updateUserDTO);

    ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken);

    ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken);
}
