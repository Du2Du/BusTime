package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.*;
import com.api.busTime.model.entities.Bus;
import com.api.busTime.model.entities.User;
import com.api.busTime.model.entities.UserRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsersBO {
    UserDTO create(CreateUserDTO userDTO);

    UserDTO findById(Long userId, boolean... passVerification);

    ResponseEntity<Page<UserDTO>> findAll(Pageable pageable);
    
    ResponseEntity<UserDTO> setAdminUser(Long userId,  UpdatePermissionDTO updatePermissionDTO);

    UserDTO me();

    ResponseEntity<String> delete(Long id);

    ResponseEntity<UserDTO> update(Long id, UpdateUserDTO updateUserDTO);
    
    ResponseEntity<UserDTO> updateFavoriteBus(Long userId, List<Bus> busList);

    ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken, String secret2FACode);

    ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken);
    
    ResponseEntity<List<BusDTO>> listFavoriteBuses();
    
    boolean verifyPermission(String permissionName);
}
