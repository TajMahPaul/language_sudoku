package sudoku.android.groupxi.com.groupxisudoku.controller;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import sudoku.android.groupxi.com.groupxisudoku.R;
import sudoku.android.groupxi.com.groupxisudoku.model.Board;
import sudoku.android.groupxi.com.groupxisudoku.model.GridViewAdapter;

public class ListeningActivity extends AppCompatActivity {
    TextToSpeech t1;
    TextToSpeech t2;
    private final String TAG = "GameActivity";
    public Board startBoard;
    public Board currentBoard;


    private Button[] num_buttons = new Button [9];
    public int language = 0; // 0 for native and 1 for chinese on board
    public int row, column;
    public int height, width;
    List<Integer> boardNumber = new ArrayList<>();
    GridViewAdapter adapter;

    public long start_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // make sure you do this first!!
        super.onCreate(savedInstanceState);


        GridView gridView = findViewById(R.id.gridView);

        final int language = getIntent().getIntExtra("language", 0);
        final int size = getIntent().getIntExtra("size", 9);
        ArrayList<Board> boards = readGameBoards(size);
        startBoard = chooseRandomBoard(boards);
        currentBoard = new Board(size);
        currentBoard.copyValues(startBoard.getGameCells());

        //initialize text to speech
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.FRENCH);
                }
            }
        });
    }

    private ArrayList<Board> readGameBoards(int size) {
        //read different game initial template from res/raw based on the level of difficulty
        ArrayList<Board> boards = new ArrayList<>();
        int fileId;

        // choose different file based on grid size
        if (size == 4) {
            fileId = R.raw.size4;
        } else if (size == 6) {
            fileId = R.raw.size6;
        } else if (size == 9) {
            fileId = R.raw.size9;
        } else {
            fileId = R.raw.size12;
        }

        InputStream inputStream = getResources().openRawResource(fileId);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                Board board = new Board(size);
                // read all lines in the board
                for (int i = 0; i < size; i++) {
                    String rowCells[] = line.split(" ");
                    for (int j = 0; j < size; j++) {
                        if (rowCells[j].equals("-")) {
                            board.setValue(i, j, 0);
                        } else {
                            board.setValue(i, j, Integer.parseInt(rowCells[j]));
                        }
                    }
                    line = bufferedReader.readLine();
                }
                boards.add(board);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return boards;
    }

    private Board chooseRandomBoard(ArrayList<Board> boards) {
        int randomNumber = (int) (Math.random() * boards.size());
        return boards.get(randomNumber);
    }
}
