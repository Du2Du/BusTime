package com.api.busTime.model.bo.impl;

import com.api.busTime.exceptions.EntityExistsException;
import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.model.bo.PermissionsBO;
import com.api.busTime.model.bo.TokenProvider;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dao.UserDAO;
import com.api.busTime.model.dtos.*;
import com.api.busTime.model.entities.*;
import com.api.busTime.utils.CookieUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserBOImpl implements UsersBO {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PermissionsBO permissionsGroupBO;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CookieUtil cookieUtil;

    public UserBOImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    //Método que procura usuário pelo email
    private UserDTO findByEmail(String email) {
        User user = userDAO.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        UserDTO userReturn = new UserDTO();
        BeanUtils.copyProperties(user, userReturn);

        return userReturn;
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
    @Override
    public UserDTO create(CreateUserDTO userDTO) {
        //Verificação se existe algum usuário com o email cadastrado
        Optional<User> userOptional = this.userDAO.findUserByEmail(userDTO.getEmail());
        Optional<User> userOptionalCpf = this.userDAO.findUserByCpf(userDTO.getCpf());

        //Retornando erro caso exista um usuário com o email cadastrado
        if (userOptional.isPresent())
            throw new EntityExistsException("Usuário com email ja cadsatrado");

        if (userOptionalCpf.isPresent())
            throw new EntityExistsException("Usuário com cpf ja cadsatrado");


        User user = new User();
        UserDTO userReturn = new UserDTO();
        PermissionsGroupDTO permissionsGroupDTO = permissionsGroupBO.findByName(UserRoles.DEFAULT).getBody();
        PermissionsGroup permissionsGroup = new PermissionsGroup();

        assert permissionsGroupDTO != null;
        BeanUtils.copyProperties(permissionsGroupDTO, permissionsGroup);

        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(userDTO, userReturn);
        userReturn.setPermissionsGroup(permissionsGroup);

        BeanUtils.copyProperties(userReturn, user);

        //Encriptografando senha
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        //Deixando usuário como user normal

        //Criando usuário
        BeanUtils.copyProperties(this.userDAO.save(user), userReturn);

        return userReturn;
    }

    //Método que retorna todos os usuário
    @Override
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        Page<User> allUsers;
        Page<UserDTO> allUsersReturn;

        allUsers = userDAO.listAllForId(pageable);

        allUsersReturn = allUsers.map((user) -> {
            UserDTO newUser = new UserDTO();
            BeanUtils.copyProperties(user, newUser);

            return newUser;
        });

        return ResponseEntity.ok(allUsersReturn);
    }

    //Método que atualiza o atributo isAdmin de um usuario
    @Override
    public ResponseEntity<UserDTO> setAdminUser(Long userId, UpdatePermissionDTO updatePermissionDTO) {
        User user = userDAO.getById(userId);
        PermissionsGroupDTO permissionsGroupDTO = permissionsGroupBO.findByName(updatePermissionDTO.getPermissionGroup()).getBody();
        PermissionsGroup permissionsGroup = new PermissionsGroup();

        if (permissionsGroupDTO == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        BeanUtils.copyProperties(permissionsGroupDTO, permissionsGroup);
        user.setPermissionsGroup(permissionsGroup);

        UserDTO userSave = new UserDTO();

        BeanUtils.copyProperties(user, userSave);
        userDAO.save(user);

        return ResponseEntity.ok(userSave);


    }

    //Método que irá logar o usuário
    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken) {
        UserDTO user;
        String email = loginRequest.getEmail();
        user = this.findByEmail(email);
        Boolean accessTokenValid = tokenProvider.validateToken(accessToken);
        Boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(loginRequest.getPassword());

        LoginRequest loginRequestLog = new LoginRequest();

        BeanUtils.copyProperties(loginRequest, loginRequestLog);
        loginRequestLog.setPassword(encodedPassword);

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
    @Override
    public UserDTO me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return this.findByEmail(customUserDetails.getUsername());
    }

    //Método que atualiza o token
    @Override
    public ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken) {
        Boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);
        if (!refreshTokenValid) {
            throw new IllegalArgumentException("O token refresh é inválido!");
        }

        String currentUserEmail = tokenProvider.getUsernameFromToken(refreshToken);

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
    @Override
    public UserDTO findById(Long userId) {
        //Realiza uma busca do usuário com o id recebido
        User user = this.userDAO.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        UserDTO userReturn = new UserDTO();
        BeanUtils.copyProperties(user, userReturn);

        return userReturn;
    }

    //Método que irá fazer o update do usuário
    @Override
    public ResponseEntity<UserDTO> update(Long userId, UpdateUserDTO updateUserDTO) {
        UserDTO currentUser = me();

        if (!currentUser.getId().equals(userId)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        User user = userDAO.getById(userId);
        UserDTO userDTO = new UserDTO();


        //Colocando os valores de updateUserDTO em user
        BeanUtils.copyProperties(updateUserDTO, userDTO);
        userDTO.setPermissionsGroup(user.getPermissionsGroup());
        userDTO.setFavoriteBus(user.getFavoriteBus());
        BeanUtils.copyProperties(userDTO, user);
        this.userDAO.save(user);

        return ResponseEntity.ok(userDTO);
    }

    //Método que irá deletar o usuário
    @Override
    public ResponseEntity<String> delete(Long userId) {

        //Procurando o usuário pelo id
        UserDTO userDTO = findById(userId);
        User user = new User();

        BeanUtils.copyProperties(userDTO, user);

        this.userDAO.delete(user);
        return ResponseEntity.ok("Usuário Deletado com sucesso");
    }

    @Override
    public ResponseEntity<UserDTO> updateFavoriteBus(Long userId, List<Bus> busList) {

        User user = userDAO.getById(userId);
        UserDTO userDTO = new UserDTO();

        user.setFavoriteBus(busList);
        userDTO.setFavoriteBus(busList);

        BeanUtils.copyProperties(userDAO.save(user), userDTO);

        return ResponseEntity.ok(userDTO);
    }

    @Override
    public ResponseEntity<List<BusDTO>> listFavoriteBuses() {
        UserDTO user = me();
        List<BusDTO> busDTOList = user.getFavoriteBus().stream().map(bus -> {
            BusDTO busDTO = new BusDTO();

            BeanUtils.copyProperties(bus, busDTO);

            return busDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(busDTOList);
    }

    @Override
    public boolean verifyPermission(String permissionId) {
        UserDTO user = me();
        List<Permission> permissionList = user.getPermissionsGroup().getPermissionList();

        List<Permission> permissionReturn = permissionList.stream().filter(permission -> {
            if (permission.getPermissionId().equals(permissionId)) return true;
            return false;
        }).collect(Collectors.toList());
        
        if(permissionReturn.size() == 0)return false;
        return true;
    }
}
