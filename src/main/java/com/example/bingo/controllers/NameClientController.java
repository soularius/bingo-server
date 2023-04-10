/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.controllers;

import com.example.bingo.component.GlobalData;
import com.example.bingo.model.DataPlayersModel;
import com.example.bingo.model.NameClientModel;
import com.example.bingo.model.TypePlayModel;
import com.example.bingo.model.ResponseModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
public class NameClientController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MessageMapping("/set-name")
    @SendTo("/topic/greetings")
    public void handleJsonType(NameClientModel nameClient, @Header("simpSessionId") String sessionId) throws InterruptedException, JsonProcessingException {
        System.out.println("[SERVER] handleJsonType called");
        Thread.sleep(1000);
        ObjectNode message = objectMapper.createObjectNode();
        //! Traemos el listado de los clientes conectados
        int idIndex = GlobalData.clientsPlayers.searchClient(sessionId);
        if (idIndex != -1) {
            GlobalData.clientsPlayers.updateClient(idIndex, null, null, nameClient.getNameClient(), null);
            assert GlobalData.clientsPlayers.getClientByIndex(idIndex).getMode().equals(nameClient.getNameClient());
            
            DataPlayersModel client = GlobalData.clientsPlayers.getClientByIndex(idIndex);

            System.out.println("[SERVER] Received JSON object: " + nameClient.getNameClient());
            System.out.println("[SERVER] Client ID: " + sessionId);
            System.out.println("[SERVER] Uuid client: " + client.getUuid());
            
            String connect_id = client.getUuid() == null ? sessionId : client.getUuid();

            ResponseModel response = new ResponseModel("OK", "name_play_received", "name");
            messagingTemplate.convertAndSendToUser(connect_id, "/responses", response);
            System.out.println("[SERVER] Message sent to client: " + objectMapper.writeValueAsString(response));
        } else {
            ResponseModel response = new ResponseModel("FAILED", "name_play_failed", "name");
            messagingTemplate.convertAndSendToUser(sessionId, "/responses", response);
            System.out.println("[SERVER] Message ERROR sent to client: " + objectMapper.writeValueAsString(response));  
        }
    }
}
