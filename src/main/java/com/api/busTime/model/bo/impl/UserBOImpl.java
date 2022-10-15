package com.api.busTime.model.bo.impl;

import com.api.busTime.exceptions.EntityExistsException;
import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.model.bo.TokenProvider;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dao.BusDAO;
import com.api.busTime.model.dao.PermissionsGroupDAO;
import com.api.busTime.model.dao.UserDAO;
import com.api.busTime.model.dtos.*;
import com.api.busTime.model.entities.Bus;
import com.api.busTime.model.entities.PermissionsGroup;
import com.api.busTime.model.entities.User;
import com.api.busTime.model.entities.UserRoles;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserBOImpl implements UsersBO {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BusDAO busDAO;

    @Autowired
    private PermissionsGroupDAO permissionsGroupDAO;

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

        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(userDTO, user);

        //Encriptografando senha
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        //Deixando usuário como user normal
        user.setPermissionsGroup(this.permissionsGroupDAO.findByName(UserRoles.DEFAULT));

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
        User user = userDAO.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        UserDTO userReturn = new UserDTO();

        PermissionsGroup permissionsGroup = this.permissionsGroupDAO.findByName(updatePermissionDTO.getPermissionGroup());

        if (permissionsGroup == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        user.setPermissionsGroup(permissionsGroup);

        BeanUtils.copyProperties(userDAO.save(user), userReturn);

        return ResponseEntity.ok(userReturn);


    }

    //Método que favorita um onibus
    @Override
    public ResponseEntity<List<BusDTO>> favoriteBus(Long busId, Long userId) {
        UserDTO currenUser = me();

        if (!Objects.equals(currenUser.getId(), userId)) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        User user = userDAO.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        Bus bus = busDAO.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));
        List<Bus> busList = user.getFavoriteBus();
        List<BusDTO> busDTOS;

        if (busList.contains(bus))
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();

        busList.add(bus);

        user.setFavoriteBus(busList);
        userDAO.save(user);

        busDTOS = user.getFavoriteBus().stream().map((favoriteBus) -> {
            BusDTO busDTO = new BusDTO();
            BeanUtils.copyProperties(favoriteBus, busDTO);

            return busDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(busDTOS);
    }

    //Método que desfavorita um onibus
    @Override
    public ResponseEntity<List<BusDTO>> desfavoriteBus(Long busId, Long userId) {
        UserDTO currenUser = me();

        if (!Objects.equals(currenUser.getId(), userId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();


        User user = userDAO.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        Bus bus = busDAO.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));
        List<Bus> busList = user.getFavoriteBus();
        List<BusDTO> busDTOS;

        if (!busList.contains(bus))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();


        busList.remove(bus);
        user.setFavoriteBus(busList);
        userDAO.save(user);

        busDTOS = user.getFavoriteBus().stream().map((favoriteBus) -> {
            BusDTO busDTO = new BusDTO();
            BeanUtils.copyProperties(favoriteBus, busDTO);

            return busDTO;
        }).collect(Collectors.toList());
        
        return ResponseEntity.ok(busDTOS);
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
        //Verificando se existe algum usuário com esse id
        UserDTO currentUser = me();
        User user = this.userDAO.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        UserDTO userReturn = new UserDTO();

        if (!currentUser.getId().equals(userId)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        //Colocando os valores de updateUserDTO em user
        BeanUtils.copyProperties(updateUserDTO, user);

        BeanUtils.copyProperties(this.userDAO.save(user), userReturn);

        return ResponseEntity.ok(userReturn);
    }

    //Método que irá deletar o usuário
    @Override
    public ResponseEntity<String> delete(Long userId) {

        //Procurando o usuário pelo id
        User user = this.userDAO.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        this.userDAO.delete(user);
        return ResponseEntity.ok("Usuário Deletado com sucesso");
    }
}
