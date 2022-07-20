package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.CreateUserDTO;
import com.api.busTime.model.dtos.LoginRequest;
import com.api.busTime.model.dtos.LoginResponse;
import com.api.busTime.model.dtos.UpdateUserDTO;
import com.api.busTime.model.entities.UserModel;
import org.springframework.http.ResponseEntity;

public interface UsersService {
    UserModel create(CreateUserDTO userDTO);

    UserModel findById(Long userId);

    UserModel me();

    String delete(Long id);

    UserModel update( Long id, UpdateUserDTO updateUserDTO);

    ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken);

    ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken);

    void  logout();
}
