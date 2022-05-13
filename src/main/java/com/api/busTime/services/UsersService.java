package com.api.busTime.services;

import com.api.busTime.dtos.CreateUserDTO;
import com.api.busTime.dtos.LoginRequest;
import com.api.busTime.dtos.LoginResponse;
import com.api.busTime.models.UserModel;
import org.springframework.http.ResponseEntity;

public interface UsersService {
    UserModel create(CreateUserDTO userDTO);

    UserModel findById(Long userId);

    UserModel me();

    ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken);

    ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken);
}
