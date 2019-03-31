package sudoku.android.groupxi.com.groupxisudoku;

import org.junit.Test;

import sudoku.android.groupxi.com.groupxisudoku.model.WordList;
import sudoku.android.groupxi.com.groupxisudoku.model.WordPair;

import static org.junit.Assert.*;

public class WordListTest {
    WordList testList;
    WordPair rank[];
    String nativeStr, foreignStr;

    @Test
    public void generalTest() {
        testList = new WordList();
        assertEquals(testList.getWordCount(), 0);
        nativeStr = "native";
        foreignStr = "foreign";
        // add 150 word pairs
        String tmp_native, tmp_foreign;
        for (int i = 0; i < 150; i++) {
            Integer obj = new Integer(i);
            tmp_native = nativeStr + obj.toString(i);
            tmp_foreign = foreignStr + obj.toString(i);
            testList.appendWordPair(tmp_native, tmp_foreign);
            assertEquals(testList.getWordCount(), i+1);
            for (int j = 0; j < i; j++) {
                testList.incrementWordPairIncorrectCount(tmp_native, tmp_foreign);
                assertEquals(testList.getWordPairIncorrectCount(tmp_native, tmp_foreign), j+1);
            }
        }
        // create a ranking of size 12
        rank = testList.createRanking(12);
        assertNotEquals(rank, null);
        for (int i = 0; i < 12; i++) {
            int step = 149 - i;
            Integer obj = new Integer(step);
            assertEquals(rank[i].getNativeWord(),nativeStr+obj.toString());
        }
        // reset testList
        testList.resetWordList();
        assertEquals(testList.getWordCount(), 0);
        // check that append still works
        testList.appendWordPair(nativeStr,foreignStr);
        assertEquals(testList.getWordCount(),1);
    }

    @Test
    public void cornerCaseTest() {
        testList = new WordList();
        nativeStr = "Hello!";
        foreignStr = "¡Hola!";
        // query a word pair that does not exist
        testList.appendWordPair(nativeStr, foreignStr);
        assertNotEquals(testList.getWordPairIncorrectCount(nativeStr,"Hola!"), 0);
        assertEquals(testList.getWordPairIncorrectCount(nativeStr,"Hola!"), -1);
        assertEquals(testList.getWordPairIncorrectCount("Hello",foreignStr), -1);
        // increment count and test again
        testList.incrementWordPairIncorrectCount(nativeStr, foreignStr);
        assertEquals(testList.getWordPairIncorrectCount(nativeStr,foreignStr), 1);
        assertNotEquals(testList.getWordPairIncorrectCount("",""), 0);
        // create a ranking when # of no valid words is less than size
        rank = testList.createRanking(6);
        assertEquals(rank.length, 1);
        // create a ranking when there are no valid words
        testList.resetWordPairIncorrectCount(nativeStr, foreignStr);
        rank = testList.createRanking(8);
        assertTrue(rank == null);
    }

    @Test
    public void avoidDuplicatesTest() {
        testList = new WordList();
        nativeStr = "no";
        foreignStr = "duplicates";
        // add first one
        testList.appendWordPair(nativeStr,foreignStr);
        assertEquals(testList.getWordCount(), 1);
        // add the same word pair multiple times
        for (int i = 0; i < 10; i++) {
            testList.appendWordPair(nativeStr,foreignStr);
            assertEquals(testList.getWordCount(), 1);
        }
        // check that other words can be added
        nativeStr = "yes";
        testList.appendWordPair(nativeStr,foreignStr);
        assertEquals(testList.getWordCount(), 2);
    }

    @Test
    public void expansionStressTest() {
        testList = new WordList();
        nativeStr = "stress";
        foreignStr = "test";
        // start at 16 and expand all the way to 8192
        String tmp_native, tmp_foreign;
        for (int i = 16; i < 8193; i *= 2) {
            for (int j = 0; j < i+1; j++) {
                Integer obj = new Integer(j);
                tmp_native = nativeStr + obj.toString(j);
                tmp_foreign = foreignStr + obj.toString(j);
                testList.appendWordPair(tmp_native, tmp_foreign);
                assertEquals(testList.getWordCount(), j+1);
            }
            testList.resetWordList();
            assertEquals(testList.getWordCount(), 0);
        }
    }
}
