package sudoku.android.groupxi.com.groupxisudoku.model;

public class WordList {

    private static final int INITIAL_LIST_CAPACITY = 16;

    private int current_list_capacity, word_count;

    private WordPair list[];


    private int findWordPairIndex(String native_word, String foreign_word) {
        for (int i = 0; i < word_count; i++) {
            if (list[i].getNativeWord().equals(native_word) && list[i].getForeignWord().equals(foreign_word))
                return i;
        }
        return -1;
    }

    public WordList() {
        current_list_capacity = INITIAL_LIST_CAPACITY;
        list = new WordPair[current_list_capacity];
        word_count = 0;
    }

    public int getWordCount() { return word_count; }


    public void incrementWordPairIncorrectCount(String native_word, String foreign_word) {
        int index = findWordPairIndex(native_word, foreign_word);
        if (index != -1)
            list[index].incrementIncorrectCount();
    }

    public int getWordPairIncorrectCount(String native_word, String foreign_word) {
        int index = findWordPairIndex(native_word, foreign_word);
        if (index == -1)
            return -1;
        return list[index].getIncorrectCount();
    }

    public void expandWordList() {
        WordPair newList[] = new WordPair[current_list_capacity * 2];
        for (int i = 0; i < current_list_capacity; i++)
            newList[i] = list[i];
        list = newList;
        current_list_capacity *= 2;
    }

    public void resetWordList() {
        list = new WordPair[current_list_capacity];
        word_count = 0;
        current_list_capacity = INITIAL_LIST_CAPACITY;
    }

    public void resetWordPairIncorrectCount(String native_word, String foreign_word) {
        int index = findWordPairIndex(native_word, foreign_word);
        if (index != -1)
            list[index].resetIncorrectCount();
    }

    public WordPair[] createRanking(int size) {
        WordPair tmpList[] = new WordPair[word_count];
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
            for (int j = 0; j < valid_count; j++) {
                if (tmpList[i].getIncorrectCount() < tmpList[j].getIncorrectCount())
                    candidate = tmpList[j];
            }
            rankList[i] = candidate;
            rankList[i].resetIncorrectCount();
        }
        return rankList;
    }

}
