package sudoku.android.groupxi.com.groupxisudoku.model;

import java.util.ArrayList;


public class Board {
    private int[][] gameCells = new int[9][9];

    public Board() {

    }

    public void setValue(int row, int column, int value) {
        gameCells[row][column] = value;
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
        for (int i = 0; i < gameCells.length; i++) {
            for (int j = 0; j < gameCells[i].length; j++) {
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
        for (int i = 0; i < 9; i++) {
            if(gameCells[row][i] == value){
                return false;
            }
            if(gameCells[i][column] == value){
                return false;
            }
        }

        //check group
        int g_row = row/3*3;
        int g_column = column/3*3;
        for(int i = g_row; i < g_row+3; i++){
            for(int j = g_column; j < g_column+3; j++){
                if(gameCells[i][j] == value){
                    return false;
                }
            }
        }
        // Check each group is in CellGroupFragment class for easier code
        // returns true if horizontal and vertical lines are correct
        return true;
    }

    public int getValue(int row, int column) {
        return gameCells[row][column];
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < gameCells.length; i++) {
            for (int j = 0; j < gameCells[i].length; j++) {
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
