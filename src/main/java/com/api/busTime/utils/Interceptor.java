package com.api.busTime.utils;

import com.api.busTime.model.bo.LogMessageBO;
import com.api.busTime.model.bo.TokenProvider;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dtos.CreateLogMessageDTO;
import com.api.busTime.model.dtos.UserDTO;
import com.api.busTime.model.entities.UserRoles;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Map;
import java.util.stream.Collectors;

//Classe do interceptador onde faço a verificação de permissão
@Component
public class Interceptor implements HandlerInterceptor {

    @Autowired
    private UsersBO usersBO;

    @Autowired
    private LogMessageBO logMessageBO;

    public boolean isAuthRoute(HttpServletRequest request) {
        return request.getRequestURI().equals("/api/v1/auth/login") || request.getRequestURI().equals("/api/v1/users/register");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
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

        response.setStatus(403);
        createLogMessageDTO.setUrlStatus(RequisitionStatus.FAILURE.getValue());
        logMessageBO.create(createLogMessageDTO);
        return false;
    }
}
