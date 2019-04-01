package sudoku.android.groupxi.com.groupxisudoku;

import org.junit.Test;

import sudoku.android.groupxi.com.groupxisudoku.model.Board;

import static org.junit.Assert.*;

public class BoardTest {
    int[][] tester6 = new int[6][6];
    private Board testee6 = new Board(6);
    int[][] tester9 = new int[9][9];
    private Board testee9 = new Board(9);

    @Test
    public void getValueTest() {
        // size 6
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                tester6[i][j] = j * 6 + i + 1;
            }
        }
        // copy values to tester and check
        testee6.copyValues(tester6);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                assertEquals(testee6.getValue(i,j), j*6+i+1);
            }
        }
        // now check by writing directly
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                testee6.setValue(i,j,36-j*6-i);
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                assertEquals(testee6.getValue(i,j), 36-j*6-i);
            }
        }
        // size 9
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tester9[i][j] = i * 9 + j + 1;
            }
        }
        testee9.copyValues(tester9);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(testee9.getValue(i,j), i*9+j+1);
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testee9.setValue(i,j,81-i*9-j);
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(testee9.getValue(i,j), 81-i*9-j);
            }
        }
    }

    @Test
    public void setValueTest() {
        // size 6
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                testee6.setValue(i, j, j*6+i+1);
            }
        }
        // check using getValue() and getGameCells()
        int[][] SVTestee6 = testee6.getGameCells();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                assertEquals(testee6.getValue(i,j), j*6+i+1);
                assertEquals(SVTestee6[i][j], j*6+i+1);
            }
        }
        // size 9
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testee9.setValue(i, j, i*9+j+1);
            }
        }
        int[][] SVTestee9 = testee9.getGameCells();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(testee9.getValue(i,j), i*9+j+1);
                assertEquals(SVTestee9[i][j], i*9+j+1);
            }
        }
        // out put index check
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testee9.setValue(i, j+9, i*9+j+82);
            }
        }
        SVTestee9 = testee9.getGameCells();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(testee9.getValue(i,j), i*9+j+1);
                assertEquals(SVTestee9[i][j], i*9+j+1);
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testee9.setValue(i+9, j, i*9+j+82);
            }
        }
        SVTestee9 = testee9.getGameCells();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(testee9.getValue(i,j), i*9+j+1);
                assertEquals(SVTestee9[i][j], i*9+j+1);
            }
        }
    }


    @Test
    public void copyValuesTest() {
        // size 6
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                tester6[i][j] = 36 - j * 6 - i;
            }
        }
        testee6.copyValues(tester6);
        // test on getValue() and getGameCells()
        int[][] GCTestee6 = testee6.getGameCells();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                assertEquals(testee6.getValue(i,j),36-j*6-i);
                assertEquals(GCTestee6[i][j], 36-j*6-i);
            }
        }
        // size 9
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tester9[i][j] = i * 9 + j + 1;
            }
        }
        testee9.copyValues(tester9);
        // getGameCells function
        int[][] GCTestee9 = testee9.getGameCells();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(testee9.getValue(i,j),i*9+j+1);
                assertEquals(GCTestee9[i][j], i*9+j+1);
            }
        }
        // irregular size copy
        testee9.copyValues(tester6);
        GCTestee9 = testee9.getGameCells();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                assertEquals(testee9.getValue(i,j),36-j*6-i);
                assertEquals(GCTestee9[i][j], 36-j*6-i);
            }
        }
    }

    @Test
    public void isBoardFullTest() {
        // size 6
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                testee6.setValue(i,j,0);
            }
        }
        assertFalse(testee6.isBoardFull());
        testee6.setValue(0,0,1);
        assertFalse(testee6.isBoardFull());
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                testee6.setValue(i,j,36-j*6-i);
            }
        }
        assertTrue(testee6.isBoardFull());
        testee6.setValue(0,0,0);
        assertFalse(testee6.isBoardFull());
        testee6.setValue(0,0,1);
        assertTrue(testee6.isBoardFull());
        testee6.setValue(5,5,0);
        assertFalse(testee6.isBoardFull());
        testee6.setValue(5,5,81);
        assertTrue(testee6.isBoardFull());
        testee6.setValue(2,2,0);
        assertFalse(testee6.isBoardFull());
        // size 9
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testee9.setValue(i,j,i*9+j+1);
            }
        }
        assertTrue(testee9.isBoardFull());
        testee9.setValue(0,0,0);
        assertFalse(testee9.isBoardFull());
        testee9.setValue(0,0,1);
        assertTrue(testee9.isBoardFull());
        testee9.setValue(8,8,0);
        assertFalse(testee9.isBoardFull());
        testee9.setValue(8,8,81);
        assertTrue(testee9.isBoardFull());
        testee9.setValue(4,4,0);
        assertFalse(testee9.isBoardFull());
    }

    @Test
    public void isBoardCorrectTest() {
        // size 6
        for (int i = 0; i < 5; i++) {
            testee6.setValue(0, i, 100-i);
        }
        assertTrue(testee6.isBoardCorrect(0, 3, 95));
        assertFalse(testee6.isBoardCorrect(0, 5, 100));
        // first column
        for (int i = 0; i < 5; i++) {
            testee6.setValue(i, 0, 100-i);
        }
        assertTrue(testee6.isBoardCorrect(5, 0, -100));
        assertFalse(testee6.isBoardCorrect(5, 0, 97));
        // inner cells
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                testee6.setValue(i, j, 5 - i * 3 - j );
            }
        }
        assertTrue(testee6.isBoardCorrect(1, 2, 0));
        assertFalse(testee6.isBoardCorrect(1, 2, 1));
        for (int i = 2; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                testee6.setValue(i, j, j*3+i);
            }
        }
        assertTrue(testee6.isBoardCorrect(2, 0, 7));
        assertFalse(testee6.isBoardCorrect(2, 0, 2));
        // size 9
        for (int i = 0; i < 8; i++) {
            testee9.setValue(0, i, i+1);
        }
        assertTrue(testee9.isBoardCorrect(0, 8, 9));
        assertFalse(testee9.isBoardCorrect(0, 8, 1));
        for (int i = 0; i < 8; i++) {
            testee9.setValue(i, 0, 9-i);
        }
        assertTrue(testee9.isBoardCorrect(8, 0, 1));
        assertFalse(testee9.isBoardCorrect(8, 0, 9));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                testee9.setValue(i, j, i*3+j+1);
            }
        }
        testee9.setValue(2, 2, 0);
        assertTrue(testee9.isBoardCorrect(2, 2, 9));
        assertFalse(testee9.isBoardCorrect(2, 2, 1));
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                testee9.setValue(i, j, (i-3)*3+j+1);
            }
        }
        testee9.setValue(5, 2, 0);
        assertTrue(testee9.isBoardCorrect(5, 2, 9));
        assertFalse(testee9.isBoardCorrect(5, 2, 1));
    }

    @Test
    public void toStringTest() {
        // size 6
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                testee6.setValue(i, j, 0);
            }
        }
        assertEquals(testee6.toString(),
                "\n- - - - - -" +
                        "\n- - - - - -" +
                        "\n- - - - - -" +
                        "\n- - - - - -" +
                        "\n- - - - - -" +
                        "\n- - - - - -");
        // size 6
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                testee6.setValue(i, j, (i*6+j+1));
            }
        }
        // random numbers
        assertEquals(testee6.toString(),
                "\n1 2 3 4 5 6" +
                        "\n7 8 9 10 11 12" +
                        "\n13 14 15 16 17 18" +
                        "\n19 20 21 22 23 24" +
                        "\n25 26 27 28 29 30" +
                        "\n31 32 33 34 35 36");
        // size 9
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testee9.setValue(i, j, (i * j) % 10);
            }
        }
        assertEquals(testee9.toString(),
                "\n- - - - - - - - -" +
                        "\n- 1 2 3 4 5 6 7 8" +
                        "\n- 2 4 6 8 - 2 4 6" +
                        "\n- 3 6 9 2 5 8 1 4" +
                        "\n- 4 8 2 6 - 4 8 2" +
                        "\n- 5 - 5 - 5 - 5 -" +
                        "\n- 6 2 8 4 - 6 2 8" +
                        "\n- 7 4 1 8 5 2 9 6" +
                        "\n- 8 6 4 2 - 8 6 4");
        // size 9
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testee9.setValue(i, j, 0);
            }
        }
        assertEquals(testee9.toString(),
                "\n- - - - - - - - -" +
                        "\n- - - - - - - - -" +
                        "\n- - - - - - - - -" +
                        "\n- - - - - - - - -" +
                        "\n- - - - - - - - -" +
                        "\n- - - - - - - - -" +
                        "\n- - - - - - - - -" +
                        "\n- - - - - - - - -" +
                        "\n- - - - - - - - -");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testee9.setValue(i, j, (i * j) % 10);
            }
        }
        assertEquals(testee9.toString(),
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
