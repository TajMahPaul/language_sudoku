package sudoku.android.groupxi.com.groupxisudoku;

import org.junit.Test;

import sudoku.android.groupxi.com.groupxisudoku.model.Board;

import static org.junit.Assert.*;

public class BoardTest {
    int[][] tester = new int[9][9];
    private Board testee = new Board(9);

    @Test
    public void getValueTest() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tester[i][j] = i * 9 + j + 1;
            }
        }
        testee.copyValues(tester);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(testee.getValue(i, j), i*9+j+1);
            }
        }
    }

    @Test
    public void setValueTest() {
        // getter and setter functions
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testee.setValue(i, j, i*9+j+1);
            }
        }
        int[][] SVTestee = testee.getGameCells();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(SVTestee[i][j], i*9+j+1);
            }
        }
    }


    @Test
    public void copyValuesTest() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tester[i][j] = i * 9 + j + 1;
            }
        }
        testee.copyValues(tester);
        // getGameCells function
        int[][] GCTestee = testee.getGameCells();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(GCTestee[i][j], i*9+j+1);
            }
        }
    }

    @Test
    public void isBoardFullTest() {
        // isBoardFull function
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testee.setValue(i,j,i*9+j+1);
            }
        }
        assertTrue(testee.isBoardFull());
        testee.setValue(0,0,0);
        assertFalse(testee.isBoardFull());
        testee.setValue(0,0,1);
        testee.setValue(8,8,0);
        assertFalse(testee.isBoardFull());
        testee.setValue(8,8,81);
        testee.setValue(4,4,0);
        assertFalse(testee.isBoardFull());
    }

    @Test
    public void isBoardCorrectTest() {
        // isBoardCorrect function
        for (int i = 0; i < 8; i++) {
            testee.setValue(0, i, i+1);
        }
        assertTrue(testee.isBoardCorrect(0, 8, 9));
        assertFalse(testee.isBoardCorrect(0, 8, 1));
        for (int i = 0; i < 8; i++) {
            testee.setValue(i, 0, 9-i);
        }
        assertTrue(testee.isBoardCorrect(8, 0, 1));
        assertFalse(testee.isBoardCorrect(8, 0, 9));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                testee.setValue(i, j, i*3+j+1);
            }
        }
        testee.setValue(2, 2, 0);
        assertTrue(testee.isBoardCorrect(2, 2, 9));
        assertFalse(testee.isBoardCorrect(2, 2, 1));
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                testee.setValue(i, j, (i-3)*3+j+1);
            }
        }
        testee.setValue(5, 2, 0);
        assertTrue(testee.isBoardCorrect(5, 2, 9));
        assertFalse(testee.isBoardCorrect(5, 2, 1));
    }

    @Test
    public void toStringTest() {
        // all zeroes
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testee.setValue(i, j, 0);
            }
        }
        assertEquals(testee.toString(),
                "\n- - - - - - - - -" +
                        "\n- - - - - - - - -" +
                        "\n- - - - - - - - -" +
                        "\n- - - - - - - - -" +
                        "\n- - - - - - - - -" +
                        "\n- - - - - - - - -" +
                        "\n- - - - - - - - -" +
                        "\n- - - - - - - - -" +
                        "\n- - - - - - - - -");
        // random numbers
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testee.setValue(i, j, (i * j) % 10);
            }
        }
        assertEquals(testee.toString(),
                "\n- - - - - - - - -" +
                        "\n- 1 2 3 4 5 6 7 8" +
                        "\n- 2 4 6 8 - 2 4 6" +
                        "\n- 3 6 9 2 5 8 1 4" +
                        "\n- 4 8 2 6 - 4 8 2" +
                        "\n- 5 - 5 - 5 - 5 -" +
                        "\n- 6 2 8 4 - 6 2 8" +
                        "\n- 7 4 1 8 5 2 9 6" +
                        "\n- 8 6 4 2 - 8 6 4");
    }
}
