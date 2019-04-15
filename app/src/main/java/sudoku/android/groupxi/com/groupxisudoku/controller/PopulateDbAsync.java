package sudoku.android.groupxi.com.groupxisudoku.controller;

import android.os.AsyncTask;

import sudoku.android.groupxi.com.groupxisudoku.model.WordPair;
import sudoku.android.groupxi.com.groupxisudoku.model.WordRoomDatabase;

public class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

    private final WordDao mDao;

    public PopulateDbAsync(WordRoomDatabase db) {
        mDao = db.wordDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {
        mDao.deleteAll();
        mDao.insertAll(WordPair.populateData());
        return null;
    }


}
