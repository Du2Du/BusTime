package com.api.busTime.services.impl;

import com.api.busTime.dtos.*;
import com.api.busTime.exceptions.EntityExistsException;
import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.models.UserModel;
import com.api.busTime.repositories.UserRepository;
import com.api.busTime.services.TokenProvider;
import com.api.busTime.services.UsersService;
import com.api.busTime.utils.CookieUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UserServiceImpl implements UsersService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CookieUtil cookieUtil;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Método que procura usuário pelo email
    private UserModel findByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    //Método que adiciona o token de acesso
    private void addAccessTokenCookie(HttpHeaders httpHeaders, Token token) {
        //Adicionando o token no cookie do header
        httpHeaders.add(
                HttpHeaders.SET_COOKIE,
                cookieUtil.createAccessTokenCookie(token.getTokenValue(), token.getDuration()).toString());
    }

    //Método que adiciona o token de refresh
    private void addRefreshTokenCookie(HttpHeaders httpHeaders, Token token) {
        //Adicionando o token no cookie do header
        httpHeaders.add(HttpHeaders.SET_COOKIE,
                cookieUtil.createRefreshTokenCookie(token.getTokenValue(), token.getDuration()).toString());
    }

    //Método que irá criar o usuário
    public UserModel create(CreateUserDTO userDTO) {
        //Verificação se existe algum usuário com o email cadastrado
        Optional<UserModel> userOptional = this.userRepository.findUserByEmail(userDTO.getEmail());

        //Retornando erro caso exista um usuário com o email cadastrado
        if (userOptional.isPresent()) throw new EntityExistsException("Usuário com email ja cadsatrado");

        UserModel user = new UserModel();

        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(userDTO, user);

        //Encriptografando senha
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        //Deixando usuário como user normal
        user.setIsAdmin(false);

        //Criando usuário
        return this.userRepository.save(user);
    }

    //Método que irá logar o usuário
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken) {
        String email = loginRequest.getEmail();
        UserModel user = this.findByEmail(email);
        Boolean accessTokenValid = tokenProvider.validateToken(accessToken);
        Boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);

        HttpHeaders responseHeaders = new HttpHeaders();
        Token newAccessToken;
        Token newRefreshToken;

        //Validações se existe algum token, caso não irá criar para o usuário
        if (!accessTokenValid && !refreshTokenValid) {
            newAccessToken = tokenProvider.generateAccessToken(user.getEmail());
            newRefreshToken = tokenProvider.generateRefreshToken(user.getEmail());
            addAccessTokenCookie(responseHeaders, newAccessToken);
            addRefreshTokenCookie(responseHeaders, newRefreshToken);
        }

        //Validações se existe algum token, caso não irá criar para o usuário
        if (!accessTokenValid && refreshTokenValid) {
            newAccessToken = tokenProvider.generateAccessToken(user.getEmail());
            addAccessTokenCookie(responseHeaders, newAccessToken);
        }

        //Adiciona/cria os tokens
        if (accessTokenValid && refreshTokenValid) {
            newAccessToken = tokenProvider.generateAccessToken(user.getEmail());
            newRefreshToken = tokenProvider.generateRefreshToken(user.getEmail());
            addAccessTokenCookie(responseHeaders, newAccessToken);
            addRefreshTokenCookie(responseHeaders, newRefreshToken);
        }

        LoginResponse loginResponse = new LoginResponse(LoginResponse.SuccessFailure.SUCCESS, "Autenticação realizada com sucesso. " +
                "Tokens criados no cookie.");
        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }

    //Método que retorna os dados do usuário
    public UserModel me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return this.findByEmail(customUserDetails.getUsername());
    }

    //Método que atualiza o token
    public ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken) {
        Boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);
        if (!refreshTokenValid) {
            throw new IllegalArgumentException("O token refresh é inválido!");
        }

        String currentUserEmail = tokenProvider.getUsernameFromToken(refreshToken);
        System.out.println(currentUserEmail);

        Token newAccessToken = tokenProvider.generateAccessToken(currentUserEmail);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE,
                cookieUtil.createAccessTokenCookie(newAccessToken.getTokenValue(), newAccessToken.getDuration())
                        .toString());

        LoginResponse loginResponse = new LoginResponse(LoginResponse.SuccessFailure.SUCCESS,
                "Autenticação concluida. Os Tokens foram criados no cookie.");
        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }

    //Método que irá pegar os dados de um usuário pelo id
    public UserModel findById(Long userId) {
        //Realiza uma busca do usuário com o id recebido
        return this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
    }

    //Método que irá fazer o update do usuário
    public UserModel update(Long userId, UpdateUserDTO updateUserDTO) {
        //Verificando se existe algum usuário com esse id
        UserModel user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        //Colocando os valores de updateUserDTO em user
        BeanUtils.copyProperties(updateUserDTO, user);

        //Encriptografando senha
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return this.userRepository.save(user);
    }

    //Método que irá deletar o usuário
    public String delete(Long userId) {
        //Procurando o usuário pelo id
        UserModel user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        this.userRepository.delete(user);

        return "Usuário Deletado com sucesso";
    }
}
