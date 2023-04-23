/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.component;

import com.example.bingo.model.ClientsModel;
import com.example.bingo.model.DataPlayersModel;
import java.util.ArrayList;
import java.util.List;
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
    public static int maxNumber = 5;
    //public static int maxNumber = 75;
    public static String typePlay = null;
    public static String[] alphaBingo = new String[]{"B", "I", "N", "G", "O"};
    public static List<Integer> numberGenerate = new ArrayList<>();
    public static List<String> letterGenerate = new ArrayList<>();
    public static int qtyNumberGenerate = 0;
    public static boolean allUserValidateNumber = true;
    public static boolean initPlay = false;
    public static int lastNumber = 0;
    public static  String lastLetter = "";
    public static int sendBingoQtyUser = 0;
    public static DataPlayersModel clientWinner = null;
    
    public static void restarAll() {
        typePlay = null;
        qtyNumberGenerate = 0;
        allUserValidateNumber = true;
        initPlay = false;
        lastNumber = 0;
        lastLetter = "";
        sendBingoQtyUser = 0;
        numberGenerate.clear();
        letterGenerate.clear();
    }    
}
