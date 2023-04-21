/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.services;

import com.example.bingo.component.GlobalData;
import com.example.bingo.component.WebSocketEventListener;
import com.example.bingo.controllers.SseController;
import com.example.bingo.model.DataPlayersModel;
import com.example.bingo.model.ResponseModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class BingoEventsPlayers {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SseController sseController;


    public ScheduledFuture<?> startGameCountdown(AtomicBoolean countdownStarted, ScheduledFuture<?> scheduledTask, Consumer<Boolean> onComplete) {
        
        if (scheduledTask != null && !scheduledTask.isDone()) {
            scheduledTask.cancel(false);
            System.out.println("[SERVER] Restart scheduledTask");
        }
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);        
        scheduledTask = scheduler.schedule(() -> {
            System.out.println(GlobalData.clientsPlayers.getValidClients());
            if (GlobalData.clientsPlayers.getClients().size() > GlobalData.qtyMinClients && GlobalData.clientsPlayers.getValidClients()) {
                ResponseModel response = new ResponseModel("OK", "play_prepared", "pre_play");
                System.out.println("Sheduled exe, QTY clients: " + GlobalData.clientsPlayers.getClients().size());
                for (int i = 0; i < GlobalData.clientsPlayers.getClients().size(); i++) {
                    DataPlayersModel client = GlobalData.clientsPlayers.getClientByIndex(i);
                    String connect_id = client.getUuid() == null ? client.getIdSession() : client.getUuid();
                    messagingTemplate.convertAndSendToUser(connect_id, "/responses", response);

                    try {
                        System.out.println("[SERVER] Message sent to client: " + objectMapper.writeValueAsString(response));
                    } catch (JsonProcessingException ex) {
                        System.out.println("[SERVER] Error");
                        Logger.getLogger(WebSocketEventListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // Se generan los tableros
                    client.initTableBingo();
                    calculateTypePlay();
                }
            }            
            onComplete.accept(GlobalData.clientsPlayers.getClients().size() > GlobalData.qtyMinClients && GlobalData.clientsPlayers.getValidClients());
            
        }, 1, TimeUnit.MINUTES);
        return scheduledTask;
    }

    protected void calculateTypePlay() {
        int qtyFull = 0;
        int qtyNormal = 0;
        for (int i = 0; i < GlobalData.clientsPlayers.getClients().size(); i++) {
            DataPlayersModel client = GlobalData.clientsPlayers.getClientByIndex(i);
            if("FULL".equals(client.getMode())) {
                qtyFull ++;
            } else {
                qtyNormal ++;
            }
        }        
        GlobalData.typePlay = qtyFull>= qtyNormal ? "FULL" : "NORMAL";
    }
}
