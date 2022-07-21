package com.api.busTime.model.bo.impl;

import com.api.busTime.model.dtos.CustomUserDetails;
import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.model.entities.UserModel;
import com.api.busTime.model.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author: TCMALTUNKAN - MEHMET ANIL ALTUNKAN
 * @Date: 30.12.2019:09:07, Pzt
 **/
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserModel user =
                userRepository.findUserByEmail(s).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o email " + s));
        return new CustomUserDetails(user);
    }
}