/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.controllers;

import com.example.bingo.component.GlobalData;
import com.example.bingo.component.WebSocketEventListener;
import com.example.bingo.model.DataPlayersModel;
import com.example.bingo.model.GenerateNumbeModel;
import com.example.bingo.model.PlayerNameModel;
import com.example.bingo.model.ResponseModel;
import com.example.bingo.services.BingoEventsPlayers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class GenerateNumberController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SseController sseController;

    @Autowired
    private BingoEventsPlayers bingoEventPlayers = new BingoEventsPlayers();

    private AtomicBoolean countdownStarted = new AtomicBoolean(false);

    private ScheduledFuture<?> scheduledTask;
    
    private  GameOverController gameOver = new GameOverController();

    @MessageMapping("/get-number")
    @SendTo("/topic/greetings")
    public void GenerateNumbeModel(GenerateNumbeModel genNumeber, @Header("simpSessionId") String sessionId) throws InterruptedException, JsonProcessingException {
        System.out.println("[SERVER] handleJsonType called");
        Thread.sleep(1000);
        ObjectNode message = objectMapper.createObjectNode();
        countdownStarted.set(false);
        String bingoNumber = "";
        String status = "OK";
        ResponseModel response;
        HashMap<String, Object> bingoReturnData = new HashMap<>();
        //! Traemos el listado de los clientes conectados
        int idIndex = GlobalData.clientsPlayers.searchClient(sessionId);
        if (idIndex != -1) {
            if (GlobalData.sendBingoQtyUser == GlobalData.clientsPlayers.getClients().size() || !GlobalData.initPlay) {
                GlobalData.initPlay = true;
                GlobalData.sendBingoQtyUser = 0;
                generateNumbers();
                gameOver.markTable();
                GlobalData.clientWinner = gameOver.validateGameOver();
                status = "OK";
                GlobalData.clientsPlayers.restartNumbersLives();
            } 
            
            bingoReturnData.put("letter", GlobalData.lastLetter);
            bingoReturnData.put("number", GlobalData.lastNumber);
            bingoNumber = objectMapper.writeValueAsString(bingoReturnData);
            
            System.out.println("Bingo:" + GlobalData.lastLetter + ":" + GlobalData.lastNumber);

            DataPlayersModel client = GlobalData.clientsPlayers.getClientByIndex(idIndex);
            
            System.out.println("[SERVER] Received JSON object: " + genNumeber.getLetter() + "" + genNumeber.getNumber());
            System.out.println("[SERVER] Received JSON status: " + genNumeber.isValidate());
            System.out.println("[SERVER] Client ID: " + sessionId);
            System.out.println("[SERVER] Uuid client: " + client.getUuid());

            String connect_id = client.getUuid() == null ? sessionId : client.getUuid();
            client.setNumberLive(true);
            GlobalData.sendBingoQtyUser += 1;

            if(GlobalData.clientWinner == null) {
                response = new ResponseModel(status, bingoNumber, "bingo_number");
                messagingTemplate.convertAndSendToUser(connect_id, "/responses", response);
                System.out.println("[SERVER] Message sent to client: " + objectMapper.writeValueAsString(response));            
            } else {
                System.out.println("[SERVER] WINNER FOUND");
                sendWinner(bingoNumber);
            }
        } else {
            response = new ResponseModel("FAILED", "number_error", "bingo_number");
            messagingTemplate.convertAndSendToUser(sessionId, "/responses", response);
            System.out.println("[SERVER] Message ERROR sent to client: " + objectMapper.writeValueAsString(response));
        }
        //sseController.sendEvent("clientSendName");

        /*if (GlobalData.clientsPlayers.getClients().size() > GlobalData.qtyMinClients && !countdownStarted.getAndSet(true)) {
            System.out.println("[SERVER] Call starterd preparate");
            scheduledTask = bingoEventPlayers.startGameCountdown(countdownStarted, scheduledTask, countdownCompleted -> {
                countdownStarted.set(countdownCompleted);
            });
        }*/
    }
    
    public void generateNumbers() {
        AleatNumber();
        AleatLetter();
    }

    public int AleatNumber() {
        int randomNumber = 0;

        Random random = new Random();
        randomNumber = random.nextInt(GlobalData.maxNumber - GlobalData.minNumber + 1) + GlobalData.minNumber;
        GlobalData.lastNumber = randomNumber;

        return randomNumber;
    }

    public String AleatLetter() {
        String randomLetter = "";

        Random random = new Random();
        randomLetter = GlobalData.alphaBingo[random.nextInt(GlobalData.alphaBingo.length)];
        GlobalData.lastLetter = randomLetter;

        return randomLetter;
    }
    
    public void sendWinner(String bingoNumber) {
        ResponseModel response;
        for (int i = 0; i < GlobalData.clientsPlayers.getClients().size(); i++) {
            DataPlayersModel client = GlobalData.clientsPlayers.getClientByIndex(i);
            String connect_id = client.getUuid() == null ? client.getIdSession() : client.getUuid();
            response = new ResponseModel("OK", bingoNumber, "bingo_number", GlobalData.clientWinner.getName());
            messagingTemplate.convertAndSendToUser(connect_id, "/responses", response);
        }
    }
}
