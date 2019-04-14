package sudoku.android.groupxi.com.groupxisudoku.controller;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import sudoku.android.groupxi.com.groupxisudoku.model.WordPair;
import sudoku.android.groupxi.com.groupxisudoku.model.WordRoomDatabase;

public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<WordPair>> mAllWords;

    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        Log.e("bang", "WordRepository: inserted");
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();

    }

    public LiveData<List<WordPair>> getAllWords() {
        return mAllWords;
    }

    public void update (WordPair word_pair) {
        new insertAsyncTask(mWordDao).execute(word_pair);
    }

    public void insert (WordPair word_pair) {
        new insertAsyncTask(mWordDao).execute(word_pair);
    }

    private static class insertAsyncTask extends AsyncTask<WordPair, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WordPair... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<WordPair, Void, Void> {

        private WordDao mAsyncTaskDao;

        updateAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WordPair... params) {
            mAsyncTaskDao.updateWordPair(params[0]);
            return null;
        }
    }
}
