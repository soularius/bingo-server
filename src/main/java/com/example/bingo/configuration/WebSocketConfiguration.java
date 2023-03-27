/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.configuration;

import com.example.bingo.model.AllowedClients;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 *
 * @author ADMIN
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer { 
    
    private final ResourceLoader resourceLoader;

    public WebSocketConfiguration(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
       List<String> allowedOrigins = allowedOriginsList();
        registry.addEndpoint("/gs-guide-websocket").setAllowedOrigins(allowedOrigins.toArray(new String[allowedOrigins.size()])).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app"); 
        config.enableSimpleBroker("/topic", "/queue"); 
    }
    
    private List<String> allowedOriginsList() {
         ObjectMapper objectMapper = new ObjectMapper();
        List<String> allowedOrigins = Collections.emptyList();
        try {
            Resource resource = new ClassPathResource("static/json/allowed-clients.json");
            AllowedClients allowedClients = objectMapper.readValue(resource.getInputStream(), AllowedClients.class);
            allowedOrigins = allowedClients.getClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allowedOrigins;
    }
}
