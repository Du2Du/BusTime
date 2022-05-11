package com.api.busTime.services;

import com.api.busTime.dtos.CreateUserDTO;
import com.api.busTime.dtos.UpdateUserDTO;
import com.api.busTime.exceptions.EntityExistsException;
import com.api.busTime.exceptions.ResourceNotFoundException;
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

    //Método que irá pegar os dados de um usuário pelo id
    public UserModel findById(Long userId){
        //Realiza uma busca do usuário com o id recebido
        return this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
    }

    //Método que irá fazer o update do usuário
    public UserModel update(Long userId, UpdateUserDTO updateUserDTO){
        //Verificando se existe algum usuário com esse id
       UserModel user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        //Colocando os valores de updateUserDTO em user
        BeanUtils.copyProperties(updateUserDTO, user);

        return this.userRepository.save(user);
    }

    //Método que irá deletar o usuário
    public String delete(Long userId){
        //Procurando o usuário pelo id
        UserModel user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        this.userRepository.delete(user);

        return "Usuário Deletado com sucesso";
    }
}
