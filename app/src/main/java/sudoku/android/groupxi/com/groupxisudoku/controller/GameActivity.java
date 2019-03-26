package sudoku.android.groupxi.com.groupxisudoku.controller;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.speech.tts.TextToSpeech;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import sudoku.android.groupxi.com.groupxisudoku.model.GridViewAdapter;
import sudoku.android.groupxi.com.groupxisudoku.R;
import sudoku.android.groupxi.com.groupxisudoku.model.Board;


public class GameActivity extends AppCompatActivity {
    TextToSpeech t1;
    TextToSpeech t2;
    private final String TAG = "GameActivity";
    public Board startBoard;
    public Board currentBoard;


    private Button[] num_buttons = new Button [9];
    public int language = 0; // 0 for native and 1 for chinese on board
    List<Integer> boardNumber = new ArrayList<>();
    GridViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // make sure you do this first!!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Resources res = getResources();
        final String[] native_strings = res.getStringArray(R.array.native_array);
        String[] chinese_strings = res.getStringArray(R.array.chinese_array);
        GridView gridView = findViewById(R.id.gridView);
        final ToggleButton toggle = (ToggleButton) findViewById(R.id.voice);

        final int language = getIntent().getIntExtra("language", 0);
        int size = getIntent().getIntExtra("size", 9);
        ArrayList<Board> boards = readGameBoards(size);
        startBoard = chooseRandomBoard(boards);
        currentBoard = new Board(size);
        currentBoard.copyValues(startBoard.getGameCells());

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
                boardNumber.add(currentBoard.getValue(i, j));
            }
        }

        // set up gridView adapter
        gridView.setNumColumns(size);
        adapter = new GridViewAdapter(boardNumber, native_strings, chinese_strings, language, size,this);
        gridView.setAdapter(adapter);

        //add function to number buttons
        int numButtonsId[] = new int[]{R.id.num_button1, R.id.num_button2, R.id.num_button3, R.id.num_button4,
                R.id.num_button5, R.id.num_button6, R.id.num_button7, R.id.num_button8,R.id.num_button9};
        for(int i = 0; i < size; i++){
            final int finalI = i+1;
            final int curI = i;
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
                    if(toggle.isChecked()){
                        if (language == 0){
                            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        }else{
                            t2.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        }

                    }



                    //check if a cell has been selected
                    if(adapter.isClicked()){
                        int row = adapter.getRow();
                        int column = adapter.getColumn();
                        //check if the number can fill in this cell
                        if(currentBoard.isBoardCorrect(row,column, finalI) == true){
                            currentBoard.setValue(row,column, finalI);

                            Button clickedButton = adapter.getClickedCell();
                            clickedButton.setText(text);
                            adapter.setClicked(false);
                            adapter.uncheckClickedCell();

                        }else{
                            Toast.makeText(GameActivity.this, R.string.board_incorrect, Toast.LENGTH_SHORT).show();

                        }

                    }

                    if(currentBoard.isBoardFull() == true){
                        Toast.makeText(GameActivity.this, "game over", Toast.LENGTH_SHORT).show();
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
                if(adapter.isClicked()){
                    Button clickedButton = adapter.getClickedCell();
                    clickedButton.setText("");
                    adapter.uncheckClickedCell();
                    int row = adapter.getRow();
                    int column = adapter.getColumn();
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

        //reading from internal storage (/data/data/<package-name>/files)
//        String fileName = "boards-";
//        if (difficulty == 0) {
//            fileName += "easy";
//        } else if (difficulty == 1) {
//            fileName += "normal";
//        } else {
//            fileName += "hard";
//        }

//        FileInputStream fileInputStream;
//        try {
//            fileInputStream = this.openFileInput(fileName);
//            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
//            BufferedReader internalBufferedReader = new BufferedReader(inputStreamReader);
//            String line = internalBufferedReader.readLine();
//            line = internalBufferedReader.readLine();
//            while (line != null) {
//                Board board = new Board();
//                // read all lines in the board
//                for (int i = 0; i < 9; i++) {
//                    String rowCells[] = line.split(" ");
//                    for (int j = 0; j < 9; j++) {
//                        if (rowCells[j].equals("-")) {
//                            board.setValue(i, j, 0);
//                        } else {
//                            board.setValue(i, j, Integer.parseInt(rowCells[j]));
//                        }
//                    }
//                    line = internalBufferedReader.readLine();
//                    if (line == null) {
//                        break;
//                    }
//                }
//                boards.add(board);
//                line = internalBufferedReader.readLine();
//            }
//            internalBufferedReader.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return boards;
    }

    private Board chooseRandomBoard(ArrayList<Board> boards) {
        int randomNumber = (int) (Math.random() * boards.size());
        return boards.get(randomNumber);
    }


    public void onGoBackButtonClicked() {
        finish();
    }


    /*
    public void swapButton(View view){
        String[] chinese_strings = getResources().getStringArray(R.array.chinese_array);
        String[] native_strings = getResources().getStringArray(R.array.native_array);
        int cellGroupFragments[] = new int[]{R.id.cellGroupFragment, R.id.cellGroupFragment2, R.id.cellGroupFragment3, R.id.cellGroupFragment4,
                R.id.cellGroupFragment5, R.id.cellGroupFragment6, R.id.cellGroupFragment7, R.id.cellGroupFragment8, R.id.cellGroupFragment9};
        CellGroupFragment thisCellGroupFragment;
        if(swap == 0){
            for(int i = 0; i < 9; i++){
                num_buttons[i].setText(native_strings[i]);
            }
            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    if(startBoard.getValue(i,j) != 0){
                        thisCellGroupFragment = (CellGroupFragment) getSupportFragmentManager().findFragmentById(cellGroupFragments[(i/3)*3+j/3]);
                        Log.i(TAG, "Clicked group ");
                        thisCellGroupFragment.setValue((i%3)*3+(j%3), chinese_strings[startBoard.getValue(i,j)-1]);

                    }else if(currentBoard.getValue(i,j) != 0){
                        thisCellGroupFragment = (CellGroupFragment) getSupportFragmentManager().findFragmentById(cellGroupFragments[(i/3)*3+j/3]);
                        thisCellGroupFragment.setValue((i%3)*3+(j%3), native_strings[currentBoard.getValue(i,j)-1]);
                    }
                }
            }
            swap = 1;
        }else{
            for(int i = 0; i < 9; i++){
                num_buttons[i].setText(chinese_strings[i]);
            }
            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    if(startBoard.getValue(i,j) != 0){
                        thisCellGroupFragment = (CellGroupFragment) getSupportFragmentManager().findFragmentById(cellGroupFragments[(i/3)*3+(j/3)]);
                        thisCellGroupFragment.setValue((i%3)*3+(j%3), native_strings[startBoard.getValue(i,j)-1]);

                    }else if(currentBoard.getValue(i,j) != 0){
                        thisCellGroupFragment = (CellGroupFragment) getSupportFragmentManager().findFragmentById(cellGroupFragments[(i/3)*3+(j/3)]);
                        thisCellGroupFragment.setValue((i%3)*3+(j%3), chinese_strings[currentBoard.getValue(i,j)-1]);
                    }
                }
            }
            swap = 0;
        }
    }*/
}