package com.api.busTime.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigure implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("Home/home");
        registry.addViewController("/").setViewName("Dashboard/index");
        registry.addViewController("/favorites").setViewName("Favorites/favorites");
        registry.addViewController("/permissions").setViewName("Permissions/permissions");
        registry.addViewController("/permissions-list").setViewName("PermissionsList/permissions-list");
        registry.addViewController("/login").setViewName("Login/login");
        registry.addViewController("/register").setViewName("Register/register");
        registry.addViewController("/bus").setViewName("Bus/bus");
        registry.addViewController("/create-bus").setViewName("CreateBus/create-bus");
        registry.addViewController("/logout").setViewName("Logout/logout");
        registry.addViewController("/profile").setViewName("Profile/profile");
        registry.addViewController("/search-bus").setViewName("SearchBus/search-bus");
        registry.addViewController("/update-bus/{id}").setViewName("UpdateBus/update-bus");
        registry.addViewController("/profile/update").setViewName("UpdateUser/update");
}

}
