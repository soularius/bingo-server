/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.controllers;

import com.example.bingo.component.GlobalData;
import com.example.bingo.model.DataPlayersModel;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class GameOverController {
    
    public boolean markTable() {
        String markLetter = GlobalData.lastLetter;
        int markNumber = GlobalData.lastNumber;

        for (DataPlayersModel client : GlobalData.clientsPlayers.getClients()) {
            markNumberColumn(client, markLetter, markNumber);
        }
        return true;
    }
    
    public void markNumberColumn(DataPlayersModel client, String letra, int numero) {
        int columna = Arrays.asList(GlobalData.alphaBingo).indexOf(letra);

        if (columna == -1) {
            return;
        }

        for (int i = 0; i < client.getTableBingo().getTablePlay().length; i++) {
            if (client.getTableBingo().getTablePlay()[i][columna] == numero) {
                client.getTableBingo().setTablePlayScale(i, columna, -1);
                break;
            }
        }
        String tableString = Arrays.deepToString(client.getTableBingo().getTablePlay());
        System.out.println(tableString);
    }
    
    public DataPlayersModel validateGameOver() {
        markPlayers();    
        
        boolean gamer = false;
        for (DataPlayersModel client : GlobalData.clientsPlayers.getClients()) {
            if(client.isGameOver()) {
                return client;
            }
        }
        return null;
    }
    
    public void markPlayers() {
        
        for (DataPlayersModel client : GlobalData.clientsPlayers.getClients()) {
            boolean gamer = false;
            if(GlobalData.typePlay == "FULL") {
                gamer = moodeFull(client.getTableBingo().getTablePlay());
            } else {
                gamer = modeNormal(client.getTableBingo().getTablePlay());            
            }
            client.setGameOver(gamer);
        }
    }
    
    public boolean moodeFull(int[][] matriz) {
        for (int[] fila : matriz) {
            for (int numero : fila) {
                if (numero != -1 && numero != 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean modeNormal(int[][] matriz) {
        int filas = matriz.length;
        int columnas = matriz[0].length;

        // Verificar horizontal
        for (int i = 0; i < filas; i++) {
            boolean lineaHorizontal = true;
            for (int j = 0; j < columnas; j++) {
                if (matriz[i][j] != -1 && matriz[i][j] != 0) {
                    lineaHorizontal = false;
                    break;
                }
            }
            if (lineaHorizontal) {
                return true;
            }
        }

        // Verificar vertical
        for (int j = 0; j < columnas; j++) {
            boolean lineaVertical = true;
            for (int i = 0; i < filas; i++) {
                if (matriz[i][j] != -1 && matriz[i][j] != 0) {
                    lineaVertical = false;
                    break;
                }
            }
            if (lineaVertical) {
                return true;
            }
        }

        // Verificar diagonal principal
        boolean diagonalPrincipal = true;
        for (int i = 0; i < filas && i < columnas; i++) {
            if (matriz[i][i] != -1 && matriz[i][i] != 0) {
                diagonalPrincipal = false;
                break;
            }
        }
        if (diagonalPrincipal) {
            return true;
        }

        // Verificar diagonal secundaria
        boolean diagonalSecundaria = true;
        for (int i = 0; i < filas && i < columnas; i++) {
            if (matriz[i][columnas - 1 - i] != -1 && matriz[i][columnas - 1 - i] != 0) {
                diagonalSecundaria = false;
                break;
            }
        }

        return diagonalSecundaria;
    }
}
