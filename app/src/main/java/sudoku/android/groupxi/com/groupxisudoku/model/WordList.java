package sudoku.android.groupxi.com.groupxisudoku.model;

import sudoku.android.groupxi.com.groupxisudoku.model.WordPair;

public class WordList {

    private int LIST_CAPACITY = 50, word_count = 0;

    private WordPair list[];

    public void WordList() {
        list = new WordPair[LIST_CAPACITY];
    }

    public void appendWordPair(String native_word, String foreign_word) {
        list[word_count++] = new WordPair(native_word, foreign_word);
        if (word_count >= LIST_CAPACITY)
            expandWordList();
    }

    public void expandWordList() {
        WordPair newList[] = new WordPair[LIST_CAPACITY * 2];
        for (int i = 0; i < LIST_CAPACITY; i++)
            newList[i] = list[i];
        list = newList;
        LIST_CAPACITY *= 2;
    }

    public void resetWordList() {
        list = new WordPair[LIST_CAPACITY];
    }

    public WordPair[] createRanking(int size) {
        WordPair tmpList[] = new WordPair[size];
        // first copy words with non-zero count to tmpList
        int valid_count = 0;
        for (int i = 0; i < word_count; i++) {
            if (list[i].getIncorrectCount() != 0)
                tmpList[valid_count++] = list[i];
        }
        // stop if no valid words
        if (valid_count == 0)
            return null;
        if (valid_count < size)
            size = valid_count;
        // now find the words with highest incorrect_count
        WordPair rankList[] = new WordPair[size];
        for (int i = 0; i < size; i++) {
            WordPair candidate = tmpList[i];
            for (int j = 0; j < size; j++) {
                if (tmpList[i].getIncorrectCount() < tmpList[j].getIncorrectCount())
                    candidate = tmpList[j];
            }
            rankList[i] = candidate;
        }
        return rankList;
    }

}
