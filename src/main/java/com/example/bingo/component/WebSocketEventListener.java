/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

/**
 *
 * @author ADMIN
 */
@Component
public class WebSocketEventListener {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public static List<String> connectedClients = new ArrayList<>();

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        System.out.println("[SERVER] Client connected with session id: " + sessionId);
        connectedClients.add(sessionId);
        
        String uuid = headerAccessor.getFirstNativeHeader("uuid");
        System.out.println("[SERVER] Client UUID: " + uuid);

        GlobalData.clientsPlayers.addClient(sessionId, null, null, uuid);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        System.out.println("Client disconnected with session id: " + sessionId);
        connectedClients.remove(sessionId);

        GlobalData.clientsPlayers.deleteClient(sessionId);
    }

    public List<String> getConnectedClients() {
        return connectedClients;
    }
}
