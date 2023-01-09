package com.api.busTime.utils;

import com.api.busTime.exceptions.ForbbidenException;
import com.api.busTime.exceptions.TooManyRequests;
import com.api.busTime.model.bo.LogMessageBO;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dtos.CreateLogMessageDTO;
import com.api.busTime.model.dtos.UserDTO;
import com.api.busTime.model.entities.UserRoles;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

//Classe do interceptador onde faço a verificação de permissão
@Component
public class Interceptor implements HandlerInterceptor {

    @Autowired
    UsersBO usersBO;

    @Autowired
    LogMessageBO logMessageBO;

    private final Bucket bucket;

    public Interceptor() {
        Bandwidth limit = Bandwidth.classic(40, Refill.greedy(40, Duration.ofMinutes(1)));
        bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    public boolean isAuthRoute(HttpServletRequest request) {
        return request.getRequestURI().equals("/api/v1/auth/login") || request.getRequestURI() == "/api/v1/users";
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        CreateLogMessageDTO createLogMessageDTO = new CreateLogMessageDTO(request.getMethod(), request.getRequestURI());
        final AdminVerify adminVerify = ((HandlerMethod) handler)
                .getMethod().getAnnotation((AdminVerify.class));
        
        if (!bucket.tryConsume(1)) {
            createLogMessageDTO.setUrlStatus(RequisitionStatus.FAILURE.getValue());
            logMessageBO.create(createLogMessageDTO);
            throw new TooManyRequests("Número máximo de requisições alcançada!");
        }
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
