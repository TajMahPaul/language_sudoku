package sudoku.android.groupxi.com.groupxisudoku;

import org.junit.Test;

import sudoku.android.groupxi.com.groupxisudoku.model.WordPair;

import static org.junit.Assert.*;

public class WordPairTest {
    WordPair testPair;


    @Test
    public void overallTest() {
        String nativeStr = "Native", foreignStr = "Foreign";
        // create a new pair
        testPair = new WordPair(nativeStr, foreignStr);
        assertEquals(testPair.getNativeWord(), nativeStr);
        assertEquals(testPair.getForeignWord(), foreignStr);
        assertEquals(testPair.getIncorrectCount(), (double) 0, 0);
        // increment count
        testPair.incrementIncorrectCount();
        assertEquals(testPair.getIncorrectCount(), (double) 1, 0);
        // increment some more
        for (int i = 0; i < 9; i++) {
            testPair.incrementIncorrectCount();
            assertEquals(testPair.getIncorrectCount(), (double) i+2, 0);
        }
    }
}
