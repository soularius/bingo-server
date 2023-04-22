/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.model;

/**
 *
 * @author ADMIN
 */
public class DataPlayersModel {
    private String idSession;
    private String uuid;
    private String mode;
    private String name;
    private BingoTableModel tableBingo;
    private boolean acceptTable;
    
    public DataPlayersModel() {
    }
    
    public DataPlayersModel(String idSession, String mode, String name, String uuid) {
        this.idSession = idSession;
        this.mode = mode.toUpperCase();
        this.name = name;
        this.uuid = uuid;
        this.acceptTable = false;
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public void initTableBingo() {        
        this.tableBingo = new BingoTableModel();
    }

    public BingoTableModel getTableBingo() {
        return tableBingo;
    }
    
    public boolean isValid() {
        return idSession != null && uuid != null && mode != null && name != null;
    }

    public boolean getAcceptTable() {
        return acceptTable;
    }

    public void setAcceptTable(Boolean acceptTable) {
        this.acceptTable = acceptTable;
    }
    
}
