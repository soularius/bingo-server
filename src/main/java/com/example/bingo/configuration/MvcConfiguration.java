/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

/**
 *
 * @author ADMIN
 */
@Configuration
public class MvcConfiguration{
    
    public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/error_403").setViewName("error_403");
    }
}
