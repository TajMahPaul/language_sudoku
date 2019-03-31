package sudoku.android.groupxi.com.groupxisudoku;

import org.junit.Test;

import sudoku.android.groupxi.com.groupxisudoku.model.WordList;
import sudoku.android.groupxi.com.groupxisudoku.model.WordPair;

import static org.junit.Assert.*;

public class WordListTest {
    WordList testList;
    String nativeStr, foreignStr;

    @Test
    public void overallTest() {
        testList = new WordList();
        assertEquals(testList.getWordCount(), 0);
        nativeStr = "native";
        foreignStr = "foreign";
        // add 150 word pairs
        String tmp_native, tmp_foreign;
        for (int i = 0; i < 150; i++) {
            tmp_native = nativeStr + (char) i;
            tmp_foreign = foreignStr + (char) i;
            testList.appendWordPair(tmp_native, tmp_foreign);
            assertEquals(testList.getWordCount(), i+1);
            for (int j = 0; j < i; j++) {
                testList.incrementWordPairIncorrectCount(tmp_native, tmp_foreign);
                assertEquals(testList.getWordPairIncorrectCount(tmp_native, tmp_foreign), j+1);
            }
        }
        // reset testList
        testList.resetWordList();
        assertEquals(testList.getWordCount(), 0);
        WordPair rank[] = testList.createRanking(12);
        assertEquals(rank, null);
        //System.out.print(rank[0].getForeignWord());
        //for (int i = 0; i < 12; i++) {
          //  int step = 149 - i;
            //assertTrue(rank[i].getForeignWord().equals(nativeStr + (char) step));
        //}
    }
}
