package com.api.busTime.model.bo.impl;

import com.api.busTime.exceptions.EntityExistsException;
import com.api.busTime.exceptions.ForbbidenException;
import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.exceptions.UnauthorizedException;
import com.api.busTime.model.bo.PermissionsBO;
import com.api.busTime.model.bo.TokenProvider;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dao.UserDAO;
import com.api.busTime.model.dtos.*;
import com.api.busTime.model.entities.*;
import com.api.busTime.utils.CookieUtil;
import com.api.busTime.utils.FormatEntityToDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
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
        return  FormatEntityToDTO.formatEntityToDto(user, UserDTO::new);
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
        PermissionsGroupDTO permissionsGroupDTO = permissionsGroupBO.findByName(UserRoles.DEFAULT).getBody();
        PermissionsGroup permissionsGroup = new PermissionsGroup();
        assert permissionsGroupDTO != null;
        BeanUtils.copyProperties(permissionsGroupDTO, permissionsGroup);
        user.setPermissionsGroup(permissionsGroup);
        user.setFavoriteBus(new ArrayList<>());
        BeanUtils.copyProperties(userDTO, user);

        //Encriptografando senha
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return FormatEntityToDTO.formatEntityToDto(this.userDAO.save(user), UserDTO::new);
    }

    //Método que retorna todos os usuário
    @Override
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        Page<User> allUsers = userDAO.listAllForId(pageable);
        return ResponseEntity.ok(allUsers.map(user -> FormatEntityToDTO.formatEntityToDto(user, UserDTO::new)));
    }

    //Método que atualiza o atributo isAdmin de um usuario
    @Override
    public ResponseEntity<UserDTO> setAdminUser(Long userId, UpdatePermissionDTO updatePermissionDTO) {
        User user = userDAO.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário Não encontrado"));
        PermissionsGroupDTO permissionsGroupDTO = permissionsGroupBO.findByName(updatePermissionDTO.getPermissionGroup()).getBody();
        PermissionsGroup permissionsGroup = new PermissionsGroup();
        if (permissionsGroupDTO == null)
            throw new ResourceNotFoundException("Grupo de Permissão não encontrado!");
        BeanUtils.copyProperties(permissionsGroupDTO, permissionsGroup);
        user.setPermissionsGroup(permissionsGroup);
        userDAO.save(user);
        return ResponseEntity.ok(FormatEntityToDTO.formatEntityToDto(user, UserDTO::new));
    }

    //Método que irá logar o usuário
    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken) {
        String email = loginRequest.getEmail();
        UserDTO user = this.findByEmail(email);
        boolean accessTokenValid = tokenProvider.validateToken(accessToken);
        boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(loginRequest.getPassword());
        LoginRequest loginRequestLog = new LoginRequest();
        BeanUtils.copyProperties(loginRequest, loginRequestLog);
        loginRequestLog.setPassword(encodedPassword);
        HttpHeaders responseHeaders = new HttpHeaders();
        Token newAccessToken;
        Token newRefreshToken;

        //Validações se existe algum token, caso não irá criar para o usuário
        if ((!accessTokenValid && !refreshTokenValid) || (accessTokenValid && refreshTokenValid)) {
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

        LoginResponse loginResponse = new LoginResponse(LoginResponse.SuccessFailure.SUCCESS, "Autenticação realizada com sucesso. " +
                "Tokens criados no cookie.");

        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }

    //Método que retorna os dados do usuário
    @Override
    public UserDTO me() {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            return this.findByEmail(customUserDetails.getUsername());
        } catch (Exception ex) {
            throw new UnauthorizedException("Usuário não permitido");
        }
    }

    //Método que atualiza o token
    @Override
    public ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken) {
        boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);
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
        UserDTO currentUser = me();
        if (!currentUser.getId().equals(userId))
            throw new ForbbidenException("Você não tem permissão para acessar esse recurso");
        User user = this.userDAO.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        return FormatEntityToDTO.formatEntityToDto(user, UserDTO::new);
    }

    //Método que irá fazer o update do usuário
    @Override
    public ResponseEntity<UserDTO> update(Long userId, UpdateUserDTO updateUserDTO) {
        UserDTO currentUser = me();
        if (!currentUser.getId().equals(userId)) throw new ForbbidenException("Usuário não permitido!");
        User user = userDAO.getById(userId);
        BeanUtils.copyProperties(updateUserDTO, currentUser);
        BeanUtils.copyProperties(currentUser, user);
        this.userDAO.save(user);
        return ResponseEntity.ok(currentUser);
    }

    //Método que irá deletar o usuário
    @Override
    public ResponseEntity<String> delete(Long userId) {
        UserDTO userDTO = findById(userId);
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        this.userDAO.delete(user);
        return ResponseEntity.ok("Usuário Deletado com sucesso");
    }

    @Override
    public ResponseEntity<UserDTO> updateFavoriteBus(Long userId, List<Bus> busList) {
        User user = userDAO.getById(userId);
        user.setFavoriteBus(busList);
        return ResponseEntity.ok(FormatEntityToDTO.formatEntityToDto(userDAO.save(user), UserDTO::new));
    }

    @Override
    public ResponseEntity<List<BusDTO>> listFavoriteBuses() {
        UserDTO user = me();
        List<BusDTO> busDTOList = FormatEntityToDTO.formatListEntityToListDTO(user.getFavoriteBus(), BusDTO::new);
        return ResponseEntity.ok(busDTOList);
    }

    @Override
    public boolean verifyPermission(String permissionId) {
        UserDTO user = me();
        List<Permission> permissionList = user.getPermissionsGroup().getPermissionList();
        List<Permission> permissionReturn = permissionList.stream().filter(permission ->
                permission.getPermissionId().equals(permissionId)
        ).collect(Collectors.toList());
        return permissionReturn.size() != 0;
    }
}
