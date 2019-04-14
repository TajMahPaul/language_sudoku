package sudoku.android.groupxi.com.groupxisudoku.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "word_table")
public class WordPair {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "incorrect_count")
    private int incorrect_count;
    @ColumnInfo(name = "correct_count")
    private int correct_count;
    @ColumnInfo(name = "native_word")
    private String native_word;

    public int getCorrect_count() {
        return correct_count;
    }

    public void setCorrect_count(int correct_count) {
        this.correct_count = correct_count;
    }

    public String getNative_word() {
        return native_word;
    }

    public void setNative_word(String native_word) {
        this.native_word = native_word;
    }

    public String getForeign_word() {
        return foreign_word;
    }

    public void setForeign_word(String foreign_word) {
        this.foreign_word = foreign_word;
    }

    public String getNative_language() {
        return native_language;
    }

    public void setNative_language(String native_language) {
        this.native_language = native_language;
    }

    public String getForeign_language() {
        return foreign_language;
    }

    public void setForeign_language(String foreign_language) {
        this.foreign_language = foreign_language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIncorrect_count() {
        return incorrect_count;
    }

    public void setIncorrect_count(int incorrect_count) {
        this.incorrect_count = incorrect_count;
    }

    @ColumnInfo(name = "foreign_word")
    private String foreign_word;
    @ColumnInfo(name = "native_language")
    private String native_language;
    @ColumnInfo(name = "foreign_language")
    private String foreign_language;

    public WordPair(String native_word, String foreign_word, String native_language, String foreign_language){
        this.incorrect_count = 0;
        this.native_word = native_word;
        this.foreign_word = foreign_word;
    }

    public String getNativeWord() { return native_word; }

    public String getForeignWord() { return foreign_word; }

    public void incrementIncorrectCount() { incorrect_count++; }

    public int getIncorrectCount() { return incorrect_count; }

    public void resetIncorrectCount() { incorrect_count = 0; }

    public static WordPair[] populateData() {
        return new WordPair[] {
                new WordPair("Dog", "狗", "English","Chinese"),
                new WordPair("Hi", "嗨","English","Chinese"),
                new WordPair("Wrist", "腕", "English","Chinese"),
                new WordPair("Pig", "猪", "English","Chinese"),
                new WordPair("Cat", "猫", "English","Chinese"),
                new WordPair("Toy", "玩具", "English","Chinese"),
                new WordPair("Work", "工作", "English","Chinese"),
                new WordPair("Rest", "休息", "English","Chinese"),
                new WordPair("Boy", "男孩", "English","Chinese"),
                new WordPair("Hi", "嗨", "English","Chinese"),
                new WordPair("Bye", "再见", "English","Chinese"),
                new WordPair("Cow", "牛", "English","Chinese")

        };
    }
}
