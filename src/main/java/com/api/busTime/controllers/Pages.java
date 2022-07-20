package com.api.busTime.controllers;

import com.fasterxml.jackson.annotation.JacksonInject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Pages {
    
    private ModelAndView returnHtmlFile(String repository){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(repository+"/index");
        return modelAndView;
    }
    
    @RequestMapping("/")
    public ModelAndView index () {
        return returnHtmlFile("Dashboard");
    } 
    
    @RequestMapping("/register")
    public ModelAndView register () {
        return returnHtmlFile("Register");
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return returnHtmlFile("Login");
    }
    
    @RequestMapping("/home")
    public ModelAndView home() {
        return returnHtmlFile("Home");
    }

    @RequestMapping("/bus")
    public ModelAndView bus() {
        return returnHtmlFile("Bus");
    }

    @RequestMapping("/create-bus")
    public ModelAndView createBus() {
        return returnHtmlFile("CreateBus");
    }

    @RequestMapping("/logout")
    public ModelAndView logout() {
        return returnHtmlFile("Logout");
    }

    @RequestMapping("/profile")
    public ModelAndView profile() {
        return returnHtmlFile("Profile");
    }

    @RequestMapping("/search-bus")
    public ModelAndView searchBus() {
        return returnHtmlFile("SearchBus");
    }

    @RequestMapping("/update-bus")
    public ModelAndView updateBus() {
        return returnHtmlFile("UpdateBus");
    }

    @RequestMapping("/profile/update")
    public ModelAndView updateUser() {
        return returnHtmlFile("UpdateUser");
    }
}
