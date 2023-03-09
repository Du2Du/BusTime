package com.api.busTime.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigure implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/favorites").setViewName("favorites");
        registry.addViewController("/permissions").setViewName("permissions");
        registry.addViewController("/permissions-list").setViewName("permissions-list");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/bus").setViewName("bus");
        registry.addViewController("/create-bus").setViewName("create-bus");
        registry.addViewController("/logout").setViewName("logout");
        registry.addViewController("/profile").setViewName("profile");
        registry.addViewController("/search-bus").setViewName("search-bus");
        registry.addViewController("/update-bus/{id}").setViewName("update-bus");
        registry.addViewController("/profile/update").setViewName("update");
        registry.addViewController("/statistics").setViewName("statistics");
        registry.addViewController("/logs").setViewName("logs");
    }
}
