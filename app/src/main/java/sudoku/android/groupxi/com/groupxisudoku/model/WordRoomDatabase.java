package sudoku.android.groupxi.com.groupxisudoku.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

import sudoku.android.groupxi.com.groupxisudoku.controller.WordDao;

@Database(entities = {WordPair.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordDao wordDao();

    private static volatile WordRoomDatabase INSTANCE;

    public synchronized static WordRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = getDatabase(context);
        }
        return INSTANCE;
    }

    public static WordRoomDatabase getDatabase(final Context context) {
        return  Room.databaseBuilder(context.getApplicationContext(),
                WordRoomDatabase.class, "word_database")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).wordDao().insertAll(WordPair.populateData());
                            }
                        });
                    }
                })
                .build();
    }
}




