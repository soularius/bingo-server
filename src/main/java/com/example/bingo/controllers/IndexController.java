/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.controllers;

import com.example.bingo.component.GlobalData;
import com.example.bingo.component.WebSocketEventListener;
import java.util.ArrayList;
import java.util.List;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 *
 * @author ADMIN
 */
@Controller
public class IndexController {
    
    @GetMapping({"/index", "/", "/home", "index"})
    public String index(Model modelo) {
        WebSocketEventListener listClients = new WebSocketEventListener();
        
        modelo.addAttribute("clients_players", GlobalData.clientsPlayers.getClients());
        return "index";
    }
}
