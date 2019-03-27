package sudoku.android.groupxi.com.groupxisudoku.model;

import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class Board {
    private int[][] gameCells;
    private int size;

    public Board(int size) {
        this.size = size;
        this.gameCells = new int [size][size];
        Log.e(TAG, "Board: "+ String.valueOf(size));

    }

    public void setValue(int row, int column, int value) {
        if(row < size && column < size){
            gameCells[row][column] = value;
        }
    }

    public int[][] getGameCells() {
        return gameCells;
    }

    public void copyValues(int[][] newGameCells) {
        for (int i = 0; i < newGameCells.length; i++) {
            for (int j = 0; j < newGameCells[i].length; j++) {
                gameCells[i][j] = newGameCells[i][j];
            }
        }
    }

    public boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (gameCells[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    //before set value use this function to check if this value can set in the board
    public boolean isBoardCorrect(int row, int column, int value) {
        //check horizontally and vertically
        for (int i = 0; i < size; i++) {
            if(gameCells[row][i] == value){
                return false;
            }
            if(gameCells[i][column] == value){
                return false;
            }
        }

        int g_row, g_column;
        if(size == 4){
            g_row = 2;
            g_column = 2;
        }else if(size == 6){
            g_row = 2;
            g_column = 3;
        }else if(size == 9){
            g_row = 3;
            g_column = 3;
        }else{
            g_row = 3;
            g_column = 4;
        }
        //check group
        int sub_row = row/g_row;
        int sub_column = column/g_column;
        for(int i = sub_row; i < sub_row+g_row; i++){
            for(int j = sub_column; j < sub_column+g_column; j++){
                if(gameCells[i][j] == value){
                    return false;
                }
            }
        }

        return true;
    }

    public int getValue(int row, int column) {
        return gameCells[row][column];
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (j == 0) {
                    temp.append("\n");
                }

                int currentNumber = gameCells[i][j];
                if (currentNumber == 0) {
                    temp.append("-");
                } else {
                    temp.append(currentNumber);
                }

                if (j != (gameCells[i].length-1)) {
                    temp.append(" ");
                }
            }
        }
        return temp.toString();
    }
}
