package com.api.busTime.services;

import com.api.busTime.dtos.CreateUserDTO;
import com.api.busTime.exceptions.EntityExistsException;
import com.api.busTime.models.UserModel;
import com.api.busTime.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Função que irá criar o usuário
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
}
