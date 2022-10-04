package com.api.busTime.configs;

import com.api.busTime.utils.Interceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Component
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final Interceptor inperceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(inperceptor).addPathPatterns("/api/**");
    }

}