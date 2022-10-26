package com.api.busTime.configs;

import com.api.busTime.utils.Interceptor;
import com.api.busTime.utils.LoggerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Component
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final Interceptor interceptor;

    private final LoggerInterceptor loggerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/api/**");
        registry.addInterceptor(loggerInterceptor).addPathPatterns("/**").excludePathPatterns("/api/**", "/_next/**", "/favicon.ico", "/", "/login", "/register");
    }

}