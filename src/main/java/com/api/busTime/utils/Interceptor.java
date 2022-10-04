package com.api.busTime.utils;

import com.api.busTime.model.bo.LogMessageBO;
import com.api.busTime.model.bo.TokenProvider;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dtos.CreateLogMessageDTO;
import com.api.busTime.model.entities.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
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
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        CreateLogMessageDTO createLogMessageDTO = new CreateLogMessageDTO(request.getMethod(), request.getRequestURI());
        
        if (request.getHeader("Cookie") != null)
            createLogMessageDTO.setUserForm("Email: "+usersBO.me().getEmail()+"\nPermission: "+usersBO.me().getPermissionsGroup().getName());
        
        final AdminVerify adminVerify = ((HandlerMethod) handler)
                .getMethod().getAnnotation((AdminVerify.class));

        if ((adminVerify == null) || (adminVerify.validationType() == ValidationType.ADMIN && usersBO.me().getPermissionsGroup().getName() != UserRoles.DEFAULT) ||
                (adminVerify.validationType() == ValidationType.SUPER_ADMIN && usersBO.me().getPermissionsGroup().getName() == UserRoles.SUPER_ADMINISTRATOR)) {
            createLogMessageDTO.setUrlStatus(RequisitionStatus.SUCCESS.getValue());
            logMessageBO.create(createLogMessageDTO);
            return true;
        }

        response.setStatus(403);
        createLogMessageDTO.setUrlStatus(RequisitionStatus.FAILURE.getValue());
        logMessageBO.create(createLogMessageDTO);
        return false;

    }
}
