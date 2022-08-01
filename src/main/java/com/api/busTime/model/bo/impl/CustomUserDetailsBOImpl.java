package com.api.busTime.model.bo.impl;

import com.api.busTime.model.dtos.CustomUserDetails;
import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.model.entities.User;
import com.api.busTime.model.dao.UserDAO;
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
public class CustomUserDetailsBOImpl implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user =
                userDAO.findUserByEmail(s).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o email " + s));
        return new CustomUserDetails(user);
    }
}