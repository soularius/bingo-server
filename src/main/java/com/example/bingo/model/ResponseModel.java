/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.model;

/**
 *
 * @author ADMIN
 */
public class ResponseModel {
    private String status;
    private String message;
    private String type;
    private String other;
    
    public ResponseModel() {
    }

    public ResponseModel(String status, String message, String type) {
        this.status = status;
        this.message = message;
        this.type = type;
    }

    public ResponseModel(String status, String message, String type, String other) {
        this.status = status;
        this.message = message;
        this.type = type;
        this.other = other;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
