/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.component;

import com.example.bingo.model.ClientsModel;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 *
 * @author ADMIN
 */
@Component
public class GlobalData {
    public static ClientsModel clientsPlayers = new ClientsModel();
    public static int qtyMinClients = 0;
    public static int widthTable = 5;
    public static int heightTable = 5;
    public static int minNumber = 1;
    public static int maxNumber = 75;
    public static String typePlay = null;
}
