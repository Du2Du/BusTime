package com.api.busTime.services;

import com.api.busTime.dtos.CreateUserDTO;
import com.api.busTime.dtos.LoginRequest;
import com.api.busTime.dtos.LoginResponse;
import com.api.busTime.dtos.UpdateUserDTO;
import com.api.busTime.models.UserModel;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UsersService {
    UserModel create(CreateUserDTO userDTO);

    UserModel findById(Long userId);

    UserModel me();

    String delete(Long id);

    UserModel update( Long id, UpdateUserDTO updateUserDTO);

    ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken);

    ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken);

    ResponseEntity<LoginResponse>  logout(String accessToken, String refreshToken, HttpServletRequest req, HttpServletResponse resp);
}
