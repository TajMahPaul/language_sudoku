package sudoku.android.groupxi.com.groupxisudoku.controller;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import sudoku.android.groupxi.com.groupxisudoku.R;
import sudoku.android.groupxi.com.groupxisudoku.model.Board;
import sudoku.android.groupxi.com.groupxisudoku.model.GridViewAdapter;
import sudoku.android.groupxi.com.groupxisudoku.model.WordList;
import sudoku.android.groupxi.com.groupxisudoku.model.WordPair;
import sudoku.android.groupxi.com.groupxisudoku.model.WordRoomDatabase;

public class GameActivity extends AppCompatActivity {
    TextToSpeech t1;
    TextToSpeech t2;
    private final String TAG = "GameActivity";
    public Board startBoard;
    public Board currentBoard;

    private Button[] num_buttons;
    public int height, width;
    List<Integer> boardNumber = new ArrayList<>();
    List<Integer> currentNumber = new ArrayList<>();
    GridViewAdapter adapter;
    public String[] native_strings, chinese_strings;
    public long start_time;
    int difficulty;
    public List<WordPair> word_list1;
    private WordDao mWordDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // make sure you do this first!!
        super.onCreate(savedInstanceState);

        WordRoomDatabase db = WordRoomDatabase.getDatabase(this);
        mWordDao = db.wordDao();

        word_list1 = mWordDao.getAllWords();


        int import_size = isTablet(GameActivity.this) ? 12 : 9;
        final int language = getIntent().getIntExtra("language", 0);
        final int size = getIntent().getIntExtra("size", import_size);
        final boolean isListening = getIntent().getBooleanExtra("listening", false);
        difficulty = getIntent().getIntExtra("difficulty", 0);
        num_buttons = new Button [size];


        if(savedInstanceState!=null) {
            Log.d(TAG, "onCreate: load saved data");
            startBoard = (Board) savedInstanceState.getSerializable("originalBoard");
            currentBoard = (Board) savedInstanceState.getSerializable("currentBoard");
        }else {
            ArrayList<Board> boards = readGameBoards(size);
            startBoard = chooseBoard(boards);
            currentBoard = new Board(size);
            currentBoard.copyValues(startBoard.getGameCells());

        }

        setContentView(R.layout.activity_game);
        Resources res = getResources();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        width = Math.round(metrics.widthPixels);
        height = Math.round(metrics.heightPixels);
        height = width < height ? Math.round(height*6/10) : Math.round(height*7/10);

        int source = getIntent().getIntExtra("source",1);

        native_strings = new String[import_size];
        chinese_strings = new String[import_size];

        if(source == 2){
            List<LanguageSample> word_list = (List<LanguageSample>) getIntent().getSerializableExtra("word_list");
            for (int i = 0; i < native_strings.length; i++){
                native_strings[i] = word_list.get(i).getLang_a();
                chinese_strings[i] = word_list.get(i).getLang_b();
            }
        }else{
            for (int i = 0; i < native_strings.length; i++){
                native_strings[i] = word_list1.get(i).getNativeWord();
                chinese_strings[i] = word_list1.get(i).getForeignWord();
            }

        }

        // create WordList
        final WordList myList = new WordList();


        final GridView gridView = findViewById(R.id.gridView);
        final TextView timer = findViewById(R.id.timer);

        // start timer
        start_time = System.nanoTime();
        final Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long time_passed = (System.nanoTime() - start_time) / 1000000000;
                                String minutes = Long.toString(time_passed / 60);
                                String seconds = Long.toString(time_passed % 60);
                                String message = minutes + ":" + seconds;
                                timer.setText(message);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();

        //initialize text to speech
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.CHINESE);
                }
            }
        });

        t2=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t2.setLanguage(Locale.ENGLISH);
                }
            }
        });

        //add board number into a list
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardNumber.add(startBoard.getValue(i, j));
                currentNumber.add(currentBoard.getValue(i, j));
            }
        }

        // set up gridView adapter
        gridView.setNumColumns(size);
        adapter = new GridViewAdapter(boardNumber, currentNumber, native_strings, chinese_strings, language, size,height,width, isListening, this);
        gridView.setAdapter(adapter);

        //add function to number buttons
        int numButtonsId[] = new int[]{R.id.num_button1, R.id.num_button2, R.id.num_button3, R.id.num_button4,
                R.id.num_button5, R.id.num_button6, R.id.num_button7, R.id.num_button8,R.id.num_button9,
                R.id.num_button10, R.id.num_button11, R.id.num_button12};

        // check orientation and hide dead buttons
        Button tmp;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // In portrait
            if (size == 4) {
                for (int i = size; i < import_size; i++) {
                    tmp = findViewById(numButtonsId[i]);
                    tmp.setVisibility(View.GONE);
                }
            } else if (size == 6) {
                if (isTablet(GameActivity.this)) {
                    for (int i = size; i < import_size; i++) {
                        tmp = findViewById(numButtonsId[i]);
                        tmp.setVisibility(View.GONE);
                    }
                } else {
                    numButtonsId[3] = R.id.num_button6;
                    numButtonsId[4] = R.id.num_button7;
                    numButtonsId[5] = R.id.num_button8;
                    tmp = findViewById(R.id.num_button4);
                    tmp.setVisibility(View.GONE);
                    tmp = findViewById(R.id.num_button5);
                    tmp.setVisibility(View.INVISIBLE);
                    tmp = findViewById(R.id.num_button9);
                    tmp.setVisibility(View.GONE);
                }
            } else if (size == 9 && isTablet(GameActivity.this)) {
                for (int i = 5; i < 9; i++) {
                    numButtonsId[i] = numButtonsId[i + 1];
                }
                tmp = findViewById(R.id.num_button6);
                tmp.setVisibility(View.GONE);
                tmp = findViewById(R.id.num_button11);
                tmp.setVisibility(View.INVISIBLE);
                tmp = findViewById(R.id.num_button12);
                tmp.setVisibility(View.GONE);
            }
        } else {
            // In landscape
            if (size == 4) {
                numButtonsId[2] = R.id.num_button4;
                numButtonsId[3] = R.id.num_button5;
                int[] GONE = new int[] {R.id.num_button3, R.id.num_button6,
                        R.id.num_button7, R.id.num_button8, R.id.num_button9,
                        R.id.num_button10, R.id.num_button11, R.id.num_button12};
                int n = isTablet(GameActivity.this) ? 8 : 5;
                for (int i = 0; i < n; i++){
                    tmp = findViewById(GONE[i]);
                    tmp.setVisibility(View.GONE);
                }
            } else if (size == 6) {
                int[] GONE = new int[] {R.id.num_button7, R.id.num_button8, R.id.num_button9,
                        R.id.num_button10, R.id.num_button11, R.id.num_button12};
                int n = isTablet(GameActivity.this) ? 6 : 3;
                for (int i = 0; i < n; i++){
                    tmp = findViewById(GONE[i]);
                    tmp.setVisibility(View.GONE);
                }
            } else if (size == 9 && isTablet(GameActivity.this)) {
                tmp = findViewById(R.id.num_button10);
                tmp.setVisibility(View.GONE);
                tmp = findViewById(R.id.num_button11);
                tmp.setVisibility(View.GONE);
                tmp = findViewById(R.id.num_button12);
                tmp.setVisibility(View.GONE);
            }
        }

        // initialize buttons
        for(int i = 0; i < size; i++){
            final int finalI = i+1;
            num_buttons[i] = findViewById(numButtonsId[i]);
            if(language == 0) {
                num_buttons[i].setText(chinese_strings[i]);
            }else{
                num_buttons[i].setText(native_strings[i]);
            }
            num_buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Button temp_button = (Button) v;
                    String text = (String) temp_button.getText();

                    //check if a cell has been selected
                    if(adapter.isEmptyClicked()){
                        int row = adapter.getClickedRow();
                        int column = adapter.getClickedColumn();

                        //check if the number can fill in this cell
                        if(currentBoard.isBoardCorrect(row,column, finalI) == true){
                            currentBoard.setValue(row,column, -finalI);

                            Button clickedButton = adapter.getClickedCell();
                            clickedButton.setText(text);
                            row = adapter.getClickedRow();
                            column = adapter.getClickedColumn();

                            adapter.uncheckClickedCell(gridView);

                            int pos = row*size+(column);
                            adapter.updateBoardNumber(pos, -finalI);
                        }else{
                            Toast.makeText(GameActivity.this, R.string.board_incorrect, Toast.LENGTH_SHORT).show();
                            // increment incorrect count on that word pair
                            myList.incrementWordPairIncorrectCount(native_strings[finalI-1], chinese_strings[finalI-1]);
                            String log = "Incremented count on pair " + native_strings[finalI-1] + " " + chinese_strings[finalI-1];
                            Log.d("incorrect count", log);
                        }
                    }else {
                        Toast.makeText(GameActivity.this, R.string.start_piece_error, Toast.LENGTH_SHORT).show();
                    }

                    if(currentBoard.isBoardFull()){
                        // compute and display amount of time taken to beat the game
                        long time_passed = (System.nanoTime() - start_time) / 1000000000;
                        String minutes = Long.toString(time_passed / 60);
                        String seconds = Long.toString(time_passed % 60);
                        String message = "You beat the game in: ";
                        if (time_passed / 60 > 0)
                            message += minutes + " minutes, ";
                        message += seconds + " seconds.";
                        // compute top three words the user answered incorrectly and save it to a file
                        WordPair[] rank = myList.createRanking(size);
                        String filename = "ranking.txt";
                        String fileContents = "";
                        if (rank != null) {
                            fileContents += "\n\nWords to improve:\n";
                            for (int i = 0; i < rank.length; i++) {
                                fileContents += "("+rank[i].getNativeWord() + ", " + rank[i].getForeignWord() + ")    ";
                                Log.d("ranking", rank[i].getNativeWord() + " " + rank[i].getForeignWord());
                            }
                        }
                        Toast.makeText(GameActivity.this, message+fileContents, Toast.LENGTH_LONG).show();
                        Log.d("message", message+fileContents);
                        FileOutputStream outputStream;
                        try {
                            outputStream = openFileOutput(filename, MODE_PRIVATE);
                            outputStream.write(fileContents.getBytes());
                            outputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        onGoBackButtonClicked();
                    }

                }
            });
        }

        // add function for delete button
        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.isEmptyClicked()){
                    Log.d(TAG, "onClick: delete");
                    Button clickedButton = adapter.getClickedCell();
                    clickedButton.setText("");
                    adapter.uncheckClickedCell(gridView);
                    int row = adapter.getClickedRow();
                    int column = adapter.getClickedColumn();
                    currentBoard.setValue(row,column, 0);

                }
            }
        });

    }

    private ArrayList<Board> readGameBoards(int size) {
        //read different game initial template from res/raw based on the level of difficulty
        ArrayList<Board> boards = new ArrayList<>();
        int fileId;

        // choose different file based on grid size
        if(size == 4) {
            fileId = R.raw.size4;
        }else if(size == 6) {
            fileId = R.raw.size6;
        }else if(size == 9){
            fileId = R.raw.size9;
        }else{
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
                        Log.d(TAG, "readGameBoards: " + (String)rowCells[j]+ " " + (String.valueOf(i)) + " " +(String.valueOf(j)) ) ;
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

    private Board chooseBoard(ArrayList<Board> boards) {
        return boards.get(difficulty);
    }

    public void onGoBackButtonClicked() {
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("originalBoard", startBoard);
        outState.putSerializable("currentBoard", currentBoard);
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
