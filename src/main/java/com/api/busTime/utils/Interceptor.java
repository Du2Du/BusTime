package com.api.busTime.utils;

import com.api.busTime.exceptions.ForbbidenException;
import com.api.busTime.model.bo.LogMessageBO;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dtos.CreateLogMessageDTO;
import com.api.busTime.model.dtos.UserDTO;
import com.api.busTime.model.entities.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Classe do interceptador onde faço a verificação de permissão
@Component
public class Interceptor implements HandlerInterceptor {

    @Autowired
    private UsersBO usersBO;

    @Autowired
    private LogMessageBO logMessageBO;

    public boolean isAuthRoute(HttpServletRequest request) {
        return request.getRequestURI().equals("/api/v1/auth/login") || request.getRequestURI().equals("/api/v1/users");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        CreateLogMessageDTO createLogMessageDTO = new CreateLogMessageDTO(request.getMethod(), request.getRequestURI());
        final AdminVerify adminVerify = ((HandlerMethod) handler)
                .getMethod().getAnnotation((AdminVerify.class));

        if (adminVerify == null) {
            createLogMessageDTO.setUrlStatus(RequisitionStatus.SUCCESS.getValue());
            if (!isAuthRoute(request)) {
                UserDTO user = usersBO.me();
                createLogMessageDTO.setUserForm(user.toString());
            }
            logMessageBO.create(createLogMessageDTO);
            return true;
        }
        UserDTO user = usersBO.me();
        createLogMessageDTO.setUserForm(user.toString());
        if ((adminVerify.validationType() == ValidationType.ADMIN && user.getPermissionsGroup().getName() != UserRoles.DEFAULT) ||
                (adminVerify.validationType() == ValidationType.SUPER_ADMIN && user.getPermissionsGroup().getName() == UserRoles.SUPER_ADMINISTRATOR)) {
            createLogMessageDTO.setUrlStatus(RequisitionStatus.SUCCESS.getValue());
            logMessageBO.create(createLogMessageDTO);
            return true;
        }
        createLogMessageDTO.setUrlStatus(RequisitionStatus.FAILURE.getValue());
        logMessageBO.create(createLogMessageDTO);
        throw new ForbbidenException("Você não tem permissão para acessar esse recurso!");
    }
}
