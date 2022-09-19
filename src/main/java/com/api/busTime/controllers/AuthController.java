package com.api.busTime.controllers;

import com.api.busTime.model.dtos.LoginRequest;
import com.api.busTime.model.dtos.LoginResponse;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.utils.AdminVerify;
import com.api.busTime.utils.SecurityCipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UsersBO userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @RequestBody @Validated LoginRequest loginRequest
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String decryptedAccessToken = SecurityCipher.decrypt(accessToken);
        String decryptedRefreshToken = SecurityCipher.decrypt(refreshToken);
        return userService.login(loginRequest, decryptedAccessToken, decryptedRefreshToken);
    }

    //Deixei esse método inutil pq teria q arrumar o front de novo, arrumar os arquivos no backend de novo, então por enquanto continua aq. Aonde realmente faz o logout é no 
    //SecurityConfig
    @GetMapping("/logout")
    public void logout() {
        return;
    }

    @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> refreshToken(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {
        String decryptedAccessToken = SecurityCipher.decrypt(accessToken);
        String decryptedRefreshToken = SecurityCipher.decrypt(refreshToken);
        return userService.refresh(decryptedAccessToken, decryptedRefreshToken);
    }
}
