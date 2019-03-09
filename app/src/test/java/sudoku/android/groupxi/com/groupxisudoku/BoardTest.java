package sudoku.android.groupxi.com.groupxisudoku;

import org.junit.Test;

import sudoku.android.groupxi.com.groupxisudoku.model.Board;

import static org.junit.Assert.*;

public class BoardTest {
    int[][] tester = new int[9][9];
    private Board testee = new Board();

    @Test
    public void setValueTest() {
        testee.setValue(1,1,5);
        assertEquals(testee.getValue(1,1),5);
        testee.setValue(5,3,2);
        assertEquals(testee.getValue(5,3),2);
    }

    @Test
    public void copyValuesTest() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tester[i][j] = j;
            }
        }
        testee.copyValues(tester);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(testee.getValue(i,j), tester[i][j]);
            }
        }
    }

    @Test
    public void correctnessTest() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testee.setValue(i,j,0);
            }
        }
        testee.setValue(0,0,1);
        assert(testee.isBoardCorrect(0,1,2) == true);
        assert(testee.isBoardCorrect(0,7,1) == false);
    }

    @Test
    public void fullTest() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testee.setValue(i,j,1);
            }
        }
        assert(testee.isBoardFull() == true);
        testee.setValue(0,0,0);
        assert(testee.isBoardFull() == false);
    }
}
