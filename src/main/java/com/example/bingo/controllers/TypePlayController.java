/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.controllers;

import com.example.bingo.component.WebSocketEventListener;
import com.example.bingo.model.TypePlayModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 *
 * @author ADMIN
 */
@Controller
public class TypePlayController {
    
    public static List<String> modePlayers = new ArrayList<>();

    @MessageMapping("/type-play")
    @SendTo("/topic/greetings")
    public void handleJsonType(TypePlayModel typePlay, @Header("simpSessionId") String sessionId) throws InterruptedException {
        Thread.sleep(1000);
        //! Traemos el listado de los clientes conectados
        WebSocketEventListener listClients = new WebSocketEventListener();        
        List<String> bingoclients = new ArrayList<>();
        bingoclients = listClients.getConnectedClients();
        
        System.out.println("Received JSON object: " + typePlay.getMode());
        System.out.println("Client ID: " + sessionId);
    }

}
