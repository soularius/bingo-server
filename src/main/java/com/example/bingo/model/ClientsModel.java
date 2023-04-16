/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class ClientsModel {

    private List<DataPlayersModel> clients = new ArrayList<>();

    public void addClient(String idSession, String mode, String name, String uuid) {
        DataPlayersModel client = new DataPlayersModel();
        if (idSession != null) {
            client.setIdSession(idSession);
        }
        if (mode != null) {
            client.setMode(mode);
        }
        if (name != null) {
            client.setName(name);
        }
        if (uuid != null) {
            client.setUuid(uuid);
        }
        clients.add(client);
    }

    public void updateClient(int index, String idSession, String mode, String name, String uuid) {
        DataPlayersModel client = clients.get(index);
        if (idSession != null) {
            client.setIdSession(idSession);
        }
        if (mode != null) {
            client.setMode(mode);
        }
        if (name != null) {
            client.setName(name);
        }
        if (uuid != null) {
            client.setUuid(uuid);
        }
    }

    public void deleteClient(String idSession) {
        int index = searchClient(idSession);
        if (index != -1) {
            clients.remove(index);
        }
    }

    public int searchClient(String idSession) {
        for (int i = 0; i < clients.size(); i++) {
            DataPlayersModel client = clients.get(i);
            if (client.getIdSession() != null && client.getIdSession().equals(idSession)) {
                return i;
            }
        }
        return -1;
    }

    public List<DataPlayersModel> getClients() {
        return clients;
    }

    public void setClients(List<DataPlayersModel> clients) {
        this.clients = clients;
    }

    public DataPlayersModel getClientByIndex(int index) {
        if (index >= 0 && index < clients.size()) {
            return clients.get(index);
        } else {
            return null; // Manejo del error si el índice está fuera del rango
        }
    }
    
    public boolean getValidClients() {
        int validCount = 0;
        for (DataPlayersModel client : clients) {
            if (client.isValid()) {
                validCount++;
            }
        }
        return clients.size() == validCount;
    }
}
