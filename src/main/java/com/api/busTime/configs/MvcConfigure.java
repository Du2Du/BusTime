package com.api.busTime.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigure implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("Home/index");
        registry.addViewController("/").setViewName("Dashboard/index");
        registry.addViewController("/login").setViewName("Login/index");
        registry.addViewController("/register").setViewName("Register/index");
        registry.addViewController("/bus").setViewName("Bus/index");
        registry.addViewController("/create-bus").setViewName("CreateBus/index");
        registry.addViewController("/logout").setViewName("Logout/index");
        registry.addViewController("/profile").setViewName("Profile/index");
        registry.addViewController("/search-bus").setViewName("SearchBus/index");
        registry.addViewController("/update-bus").setViewName("UpdateBus/index");
        registry.addViewController("/profile/update").setViewName("UpdateUser/index");
    }

}
