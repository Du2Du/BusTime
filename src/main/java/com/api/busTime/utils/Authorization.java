package com.api.busTime.utils;

import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.entities.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Authorization implements HandlerInterceptor {

    @Autowired
    private UsersBO usersBO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final AdminVerify adminVerify = ((HandlerMethod) handler)
                .getMethod().getAnnotation((AdminVerify.class));

        final SuperAdminVerify superAdminVerify = ((HandlerMethod) handler)
                .getMethod().getAnnotation((SuperAdminVerify.class));

        if ((superAdminVerify != null && usersBO.me().getPermissionsGroup().getName() == UserRoles.SUPER_ADMINISTRATOR) || (adminVerify != null && usersBO.me().getPermissionsGroup().getName() != UserRoles.DEFAULT))
            return true;

        if ((superAdminVerify != null && usersBO.me().getPermissionsGroup().getName() != UserRoles.SUPER_ADMINISTRATOR) || (adminVerify != null && usersBO.me().getPermissionsGroup().getName() == UserRoles.DEFAULT)) {
            response.setStatus(403);
            return false;
        }

        return true;
    }
}
