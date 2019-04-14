package sudoku.android.groupxi.com.groupxisudoku.controller;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import sudoku.android.groupxi.com.groupxisudoku.model.WordPair;

@Dao
public interface WordDao {

    @Insert
    void insert(WordPair word_pair);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Query("SELECT * from word_table ORDER BY id ASC")
    LiveData<List<WordPair>> getAllWords();

    @Insert
    void insertAll(WordPair... word_pair);

    @Update
    void updateWordPair(WordPair word_pair);
}
