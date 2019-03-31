package sudoku.android.groupxi.com.groupxisudoku;

import org.junit.Test;

import sudoku.android.groupxi.com.groupxisudoku.model.WordPair;

import static org.junit.Assert.*;

public class WordPairTest {
    WordPair testPair;
    String nativeStr, foreignStr;

    @Test
    public void English2EnglishTest() {
        nativeStr = "Native";
        foreignStr = "Foreign";
        // create a new pair
        testPair = new WordPair(nativeStr, foreignStr);
        assertEquals(testPair.getNativeWord(), nativeStr);
        assertEquals(testPair.getForeignWord(), foreignStr);
        assertEquals(testPair.getIncorrectCount(), 0);
        // increment count
        testPair.incrementIncorrectCount();
        assertEquals(testPair.getIncorrectCount(), 1);
        // increment some more
        for (int i = 0; i < 9; i++) {
            testPair.incrementIncorrectCount();
            assertEquals(testPair.getIncorrectCount(), i+2);
        }
    }

    @Test
    public void English2ChineseTest() {
        nativeStr = "Test";
        foreignStr = "测试";
        // create a new pair
        testPair = new WordPair(nativeStr, foreignStr);
        assertEquals(testPair.getNativeWord(), nativeStr);
        assertEquals(testPair.getForeignWord(), foreignStr);
        assertEquals(testPair.getIncorrectCount(), 0);
        assertNotEquals(testPair.getNativeWord(), foreignStr);
        assertNotEquals(testPair.getForeignWord(), nativeStr);
        // increment 1000 times
        for (int i = 0; i < 1000; i++) {
            testPair.incrementIncorrectCount();
            assertEquals(testPair.getIncorrectCount(), i+1);
        }
        // now reset
        testPair.resetIncorrectCount();
        assertEquals(testPair.getIncorrectCount(), 0);
    }

    @Test
    public void stressTest() {
        nativeStr = "stress";
        foreignStr = "test";
        testPair = new WordPair(nativeStr, foreignStr);
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < i; j++)
                testPair.incrementIncorrectCount();
            assertEquals(testPair.getIncorrectCount(), i);
            testPair.resetIncorrectCount();
            assertEquals(testPair.getIncorrectCount(), 0);
        }
    }
}
