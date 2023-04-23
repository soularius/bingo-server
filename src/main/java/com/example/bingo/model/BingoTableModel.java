/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.model;

import com.example.bingo.component.GlobalData;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author ADMIN
 */
public class BingoTableModel {

    private int table[][];
    private int tablePlay[][];

    public BingoTableModel() {
        this.table = new int[GlobalData.widthTable][GlobalData.heightTable];
        this.tablePlay = new int[GlobalData.widthTable][GlobalData.heightTable];
        initializeTable();
        copyTableToTablePlay();
    }

    public int[][] getTable() {
        return table;
    }

    public void setTable(int[][] table) {
        this.table = table;
    }
    
    public void refreshTable() {
        initializeTable();
        copyTableToTablePlay();
    }

    public void initializeTable() {
        int width = GlobalData.widthTable;
        int height = GlobalData.heightTable;

        Set<Integer> usedNumbers = new HashSet<>();
        Random random = new Random();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == width / 2 && j == height / 2) {
                    table[i][j] = 0;
                } else {
                    int randomNumber;
                    //do {
                        randomNumber = random.nextInt(GlobalData.maxNumber - GlobalData.minNumber + 1) + GlobalData.minNumber;
                    //} while (usedNumbers.contains(randomNumber));

                    usedNumbers.add(randomNumber);
                    table[i][j] = randomNumber;
                }
            }
        }
    }

    private void copyTableToTablePlay() {
        for (int i = 0; i < GlobalData.heightTable; i++) {
            System.arraycopy(table[i], 0, tablePlay[i], 0, GlobalData.widthTable);
        }
    }

    public void markCellAsPlayed(int x, int y) {
        int width = GlobalData.widthTable;
        int height = GlobalData.heightTable;
        int centerX = width / 2;
        int centerY = height / 2;

        if (x != centerX || y != centerY) {
            tablePlay[x][y] = -1;
        }
    }

    public int[][] getTablePlay() {
        return tablePlay;
    }

    public void setTablePlay(int[][] tablePlay) {
        this.tablePlay = tablePlay;
    }

    public void setTablePlayScale(int x, int y, int value) {
        this.tablePlay[x][y] = value;
    }
}
