package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.*;
import com.api.busTime.model.entities.Bus;
import com.api.busTime.model.entities.User;
import com.api.busTime.model.entities.UserRoles;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsersBO {
    UserDTO create(CreateUserDTO userDTO);

    UserDTO findById(Long userId);

    ResponseEntity<List<UserDTO>> findAll();
    
    ResponseEntity<UserDTO> setAdminUser(Long userId,  UpdatePermissionDTO updatePermissionDTO);

    ResponseEntity<List<BusDTO>> favoriteBus(Long busId, Long userId);

    ResponseEntity<List<BusDTO>> desfavoriteBus(Long busId, Long userId);

    UserDTO me();

    ResponseEntity<String> delete(Long id);

    ResponseEntity<UserDTO> update(Long id, UpdateUserDTO updateUserDTO);

    ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken);

    ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken);
}
