/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.component;

import com.example.bingo.services.BingoEventsPlayers;
import com.example.bingo.controllers.SseController;
import com.example.bingo.model.DataPlayersModel;
import com.example.bingo.model.ResponseModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 *
 * @author ADMIN
 */
@Component
public class WebSocketEventListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SseController sseController;

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
        sseController.sendEvent("clientConnected");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        System.out.println("Client disconnected with session id: " + sessionId);
        connectedClients.remove(sessionId);

        GlobalData.clientsPlayers.deleteClient(sessionId);
        if(GlobalData.clientsPlayers.getClients().isEmpty()) {
            GlobalData.restarAll();
        }
        sseController.sendEvent("clientDisconnected");
    }

    public List<String> getConnectedClients() {
        return connectedClients;
    }
}
