/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 *
 * @author ADMIN
 */
@Controller
public class ConnectController {
    
    @MessageMapping("/connect")
    public void onConnect(StompHeaderAccessor headerAccessor) {
        String uuid = headerAccessor.getFirstNativeHeader("uuid");
        System.out.println("[SERVER] uuid: " + uuid);
    }
}
