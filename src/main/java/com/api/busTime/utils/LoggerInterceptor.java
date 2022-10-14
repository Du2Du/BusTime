package com.api.busTime.utils;

import com.api.busTime.model.bo.LogMessageBO;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dtos.CreateLogMessageDTO;
import com.api.busTime.model.dtos.UserDTO;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Esse é um interceptador das view, fiz um separado por que pela minha visão era necessário para não ocorrer erros 
@Component
public class LoggerInterceptor implements HandlerInterceptor {

    @Autowired
    private LogMessageBO logMessageBO;

    @Autowired
    private UsersBO usersBO;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);

        UserDTO user = usersBO.me();

        CreateLogMessageDTO createLogMessageDTO = new CreateLogMessageDTO(request.getMethod(), request.getRequestURI());
        createLogMessageDTO.setUserForm(user.toString());
        
        createLogMessageDTO.setUrlStatus(RequisitionStatus.SUCCESS.getValue());
        logMessageBO.create(createLogMessageDTO);
    }
}
