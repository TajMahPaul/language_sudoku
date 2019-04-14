package sudoku.android.groupxi.com.groupxisudoku.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import sudoku.android.groupxi.com.groupxisudoku.controller.WordRepository;

public class WordPairViewModel extends AndroidViewModel {
    private WordRepository mRepository;

    private LiveData<List<WordPair>> mAllWords;

    public WordPairViewModel (Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<WordPair>> getAllWords() { return mAllWords; }

    public void insert(WordPair word_pair) { mRepository.insert(word_pair); }

    public void update(WordPair word_pair) { mRepository.update(word_pair); }
}
