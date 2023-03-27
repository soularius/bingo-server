/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.controllers;

import com.example.bingo.component.WebSocketEventListener;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author ADMIN
 */
@Controller
public class IndexController {
    
    @GetMapping({"/index", "/", "/home", "index"})
    public String index(Model modelo) {
        WebSocketEventListener listClients = new WebSocketEventListener();        
        List<String> bingoclients = new ArrayList<>();
        bingoclients = listClients.getConnectedClients();
        modelo.addAttribute("listClients", bingoclients);
        return "index";
    }
}
