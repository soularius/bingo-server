/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.controllers;

import com.example.bingo.model.Greeting;
import com.example.bingo.model.TypePlayModel;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

/**
 *
 * @author ADMIN
 */

@Controller
public class TypePlayController {
    
    @MessageMapping("/type-play")
    @SendTo("/topic/greetings")
    public Greeting greeting(TypePlayModel typePlay) throws InterruptedException {
        Thread.sleep(1000);
        return new Greeting(HtmlUtils.htmlEscape(typePlay.getName()));
    }
    
}
