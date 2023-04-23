/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.model;

/**
 *
 * @author ADMIN
 */
public class GenerateNumbeModel {
    private int number;
    private String letter;
    private boolean validate;

    public GenerateNumbeModel() {
    }

    public GenerateNumbeModel(int number, String letter, boolean validate) {
        this.number = number;
        this.letter = letter;
        this.validate = validate;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }    
}
