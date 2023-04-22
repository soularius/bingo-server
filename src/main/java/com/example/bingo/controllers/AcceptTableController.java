/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.controllers;

import com.example.bingo.component.GlobalData;
import com.example.bingo.model.AcceptTableModel;
import com.example.bingo.model.DataPlayersModel;
import com.example.bingo.model.InitPrePlayModel;
import com.example.bingo.model.ResponseModel;
import com.example.bingo.services.BingoEventsPlayers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Arrays;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 *
 * @author ADMIN
 */
@Controller
public class AcceptTableController {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private BingoEventsPlayers bingoEventPlayers = new BingoEventsPlayers();    
    
    private AtomicBoolean countdownStarted = new AtomicBoolean(false);    
    
    private ScheduledFuture<?> scheduledTask;

    @MessageMapping("/accept-table")
    @SendTo("/topic/greetings")
    public void handleJsonPrePlay(AcceptTableModel accept, @Header("simpSessionId") String sessionId) throws InterruptedException, JsonProcessingException {
        System.out.println("[SERVER] handleJsonType called");
        Thread.sleep(1000);
        ObjectNode message = objectMapper.createObjectNode();
        countdownStarted.set(false);
        int idIndex = GlobalData.clientsPlayers.searchClient(sessionId);
        if (idIndex != -1) {
            DataPlayersModel client = GlobalData.clientsPlayers.getClientByIndex(idIndex);

            System.out.println("[SERVER] Received JSON object: " + accept.getAccept());
            System.out.println("[SERVER] Client ID: " + sessionId);
            System.out.println("[SERVER] Uuid client: " + client.getUuid());

            client.setAcceptTable(accept.getAccept());
            String connect_id = client.getUuid() == null ? sessionId : client.getUuid();

            ResponseModel response = new ResponseModel("OK", "recibe", "accept_table", GlobalData.typePlay);
            messagingTemplate.convertAndSendToUser(connect_id, "/responses", response);
            System.out.println("[SERVER] Message sent to client: " + objectMapper.writeValueAsString(response));
        } else {
            ResponseModel response = new ResponseModel("FAILED", "name_play_failed", "name");
            messagingTemplate.convertAndSendToUser(sessionId, "/responses", response);
            System.out.println("[SERVER] Message ERROR sent to client: " + objectMapper.writeValueAsString(response));
        }
        System.out.println(GlobalData.clientsPlayers.getClients().size() > GlobalData.qtyMinClients);
        System.out.println(!countdownStarted.get());
        System.out.println((GlobalData.clientsPlayers.getClients().size() > GlobalData.qtyMinClients) && (!countdownStarted.get()));
        
        if (GlobalData.clientsPlayers.getClients().size() > GlobalData.qtyMinClients && !countdownStarted.getAndSet(true)) {
            System.out.println("[SERVER] Call starterd preparate");
            scheduledTask = bingoEventPlayers.playGameCountdown(countdownStarted, scheduledTask, countdownCompleted -> {
                countdownStarted.set(countdownCompleted);
            });
        }
    }
}
