/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.services;

import com.example.bingo.component.GlobalData;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class StartPlay {
    
    @Scheduled(fixedRate = 10000)
    private void handleStarPlay() {
        int qtyClient = GlobalData.clientsPlayers.getClients().size();
    }
}
