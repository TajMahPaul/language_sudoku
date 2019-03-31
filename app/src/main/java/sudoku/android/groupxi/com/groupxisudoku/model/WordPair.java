package sudoku.android.groupxi.com.groupxisudoku.model;

public class WordPair {

    private int incorrect_count;
    private String native_word, foreign_word;

    public WordPair(String native_word, String foreign_word){
        this.incorrect_count = 0;
        this.native_word = native_word;
        this.foreign_word = foreign_word;
    }

    public String getNativeWord() { return native_word; }

    public String getForeignWord() { return foreign_word; }

    public void incrementIncorrectCount () { incorrect_count++; }

    public int getIncorrectCount() { return incorrect_count; }

}
