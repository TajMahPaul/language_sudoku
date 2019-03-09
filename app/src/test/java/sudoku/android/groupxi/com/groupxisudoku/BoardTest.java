package sudoku.android.groupxi.com.groupxisudoku;

import org.junit.Test;

import sudoku.android.groupxi.com.groupxisudoku.model.Board;

import static org.junit.Assert.*;

public class BoardTest {
    int[][] tester = new int[9][9];
    Board testee = new Board();

    @Test
    public void setValueTest() {
        testee.setValue(1,1,5);
        assertEquals(testee.getValue(1,1),5);
    }
}
