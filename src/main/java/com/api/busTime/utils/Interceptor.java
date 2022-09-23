package com.api.busTime.utils;

import com.api.busTime.model.bo.UsersBO;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final AdminVerify adminVerify = ((HandlerMethod) handler)
                .getMethod().getAnnotation((AdminVerify.class));

        if ((adminVerify == null) || (adminVerify.validationType() == ValidationType.ADMIN && usersBO.me().getPermissionsGroup().getName() != UserRoles.DEFAULT) ||
                (adminVerify.validationType() == ValidationType.SUPER_ADMIN && usersBO.me().getPermissionsGroup().getName() == UserRoles.SUPER_ADMINISTRATOR))
            return true;

        response.setStatus(403);
        return false;

    }
}
