package sudoku.android.groupxi.com.groupxisudoku;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import sudoku.android.groupxi.com.groupxisudoku.fragments.CellGroupFragment;
import sudoku.android.groupxi.com.groupxisudoku.model.Board;
import sudoku.android.groupxi.com.groupxisudoku.model.Pair;



public class GameActivity extends AppCompatActivity implements CellGroupFragment.OnFragmentInteractionListener {
    private final String TAG = "GameActivity";
    private TextView clickedCell = null;
    private int clickedGroup = 0;
    private int clickedCellId = 0;
    private Board startBoard;
    private Board currentBoard;
    private Button[] num_buttons = new Button [9];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Resources res = getResources();
        String[] native_strings = res.getStringArray(R.array.native_array);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        int difficulty = getIntent().getIntExtra("difficulty", 0);
        ArrayList<Board> boards = readGameBoards(difficulty);
        startBoard = chooseRandomBoard(boards);
        currentBoard = new Board();
        currentBoard.copyValues(startBoard.getGameCells());

        int cellGroupFragments[] = new int[]{R.id.cellGroupFragment, R.id.cellGroupFragment2, R.id.cellGroupFragment3, R.id.cellGroupFragment4,
                R.id.cellGroupFragment5, R.id.cellGroupFragment6, R.id.cellGroupFragment7, R.id.cellGroupFragment8, R.id.cellGroupFragment9};
        for (int i = 1; i < 10; i++) {
            CellGroupFragment thisCellGroupFragment = (CellGroupFragment) getSupportFragmentManager().findFragmentById(cellGroupFragments[i-1]);
            thisCellGroupFragment.setGroupId(i);
        }

        //Appear all values from the current board
        CellGroupFragment tempCellGroupFragment;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int column = j / 3;
                int row = i / 3;

                int fragmentNumber = (row * 3) + column;
                tempCellGroupFragment = (CellGroupFragment) getSupportFragmentManager().findFragmentById(cellGroupFragments[fragmentNumber]);
                int groupColumn = j % 3;
                int groupRow = i % 3;

                int groupPosition = (groupRow * 3) + groupColumn;
                int currentValue = currentBoard.getValue(i, j);

                if (currentValue != 0) {
                    tempCellGroupFragment.setValue(groupPosition, native_strings[currentValue - 1]);
                }
            }
        }

        //add function to number buttons
        int numButtonsId[] = new int[]{R.id.num_button1, R.id.num_button2, R.id.num_button3, R.id.num_button4,
                R.id.num_button5, R.id.num_button6, R.id.num_button7, R.id.num_button8,R.id.num_button9};
        for(int i = 0; i < 9; i++){
            final int finalI = i+1;
            num_buttons[i] = findViewById(numButtonsId[i]);
            num_buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //check if a cell has been selected
                    if(clickedGroup != 0){
                        int row = ((clickedGroup-1)/3)*3 + clickedCellId / 3;
                        int column = (clickedGroup-1)%3*3 + clickedCellId % 3;
                        //check if the number can fill in this cell
                        if(currentBoard.isBoardCorrect(row,column, finalI) == true){
                            currentBoard.setValue(row,column, finalI);
                            Button temp_button = (Button) v;
                            String text = (String) temp_button.getText();
                            clickedCell.setText(text);
                            clickedCell.setBackgroundResource(R.drawable.table_border_cell);
                            clickedCell = null;
                            clickedGroup = 0;
                            clickedCellId = 0;
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

    }

    private ArrayList<Board> readGameBoards(int difficulty) {
        //read different game initial template from res/raw based on the level of difficulty
        ArrayList<Board> boards = new ArrayList<>();
        int fileId;
        if (difficulty == 1) {
            fileId = R.raw.normal;
        } else if (difficulty == 0) {
            fileId = R.raw.easy;
        } else {
            fileId = R.raw.hard;
        }

        InputStream inputStream = getResources().openRawResource(fileId);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                Board board = new Board();
                // read all lines in the board
                for (int i = 0; i < 9; i++) {
                    String rowCells[] = line.split(" ");
                    for (int j = 0; j < 9; j++) {
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
        String fileName = "boards-";
        if (difficulty == 0) {
            fileName += "easy";
        } else if (difficulty == 1) {
            fileName += "normal";
        } else {
            fileName += "hard";
        }

        FileInputStream fileInputStream;
        try {
            fileInputStream = this.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader internalBufferedReader = new BufferedReader(inputStreamReader);
            String line = internalBufferedReader.readLine();
            line = internalBufferedReader.readLine();
            while (line != null) {
                Board board = new Board();
                // read all lines in the board
                for (int i = 0; i < 9; i++) {
                    String rowCells[] = line.split(" ");
                    for (int j = 0; j < 9; j++) {
                        if (rowCells[j].equals("-")) {
                            board.setValue(i, j, 0);
                        } else {
                            board.setValue(i, j, Integer.parseInt(rowCells[j]));
                        }
                    }
                    line = internalBufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                }
                boards.add(board);
                line = internalBufferedReader.readLine();
            }
            internalBufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return boards;
    }

    private Board chooseRandomBoard(ArrayList<Board> boards) {
        int randomNumber = (int) (Math.random() * boards.size());
        return boards.get(randomNumber);
    }

    private boolean isStartPiece(int group, int cell) {
        int row = ((group-1)/3)*3 + (cell/3);
        int column = ((group-1)%3)*3 + ((cell)%3);
        return startBoard.getValue(row, column) != 0;
    }


    public void onGoBackButtonClicked() {
        finish();
    }


    @Override
    public void onFragmentInteraction(int groupId, int cellId, View view) {
        Log.i(TAG, "Clicked group " + groupId + ", cell " + cellId);

        int cellGroupFragments[] = new int[]{R.id.cellGroupFragment, R.id.cellGroupFragment2, R.id.cellGroupFragment3, R.id.cellGroupFragment4,
                R.id.cellGroupFragment5, R.id.cellGroupFragment6, R.id.cellGroupFragment7, R.id.cellGroupFragment8, R.id.cellGroupFragment9};

        //if there's selected cell previously, reset the background of that cell
        for(int i = 0; i < 9; i++){
            CellGroupFragment thisCellGroupFragment = (CellGroupFragment) getSupportFragmentManager().findFragmentById(cellGroupFragments[i]);
            thisCellGroupFragment.unselectTextview();
        }
        if(clickedCell != null){
            clickedCell.setBackgroundResource(R.drawable.table_border_cell);
        }

        //set clicked cell background
        clickedCell = (TextView) view;
        if (!isStartPiece(groupId, cellId)) {
            clickedGroup = groupId;
            clickedCellId = cellId;
            view.setBackgroundResource(R.drawable.table_border_cell_selected);
        } else {
            for(int i = 0; i < 9; i++){
                CellGroupFragment thisCellGroupFragment = (CellGroupFragment) getSupportFragmentManager().findFragmentById(cellGroupFragments[i]);
                thisCellGroupFragment.selectTextview(clickedCell.getText());
            }
            clickedCell = null;
            clickedGroup = 0;
            clickedCellId = 0;
        }
    }

    public void deleteButton(View view){
        if(clickedGroup != 0){
            clickedCell.setText("");
            int row = ((clickedGroup-1)/3)*3 + clickedCellId / 3;
            int column = (clickedGroup-1)%3*3 + clickedCellId % 3;
            currentBoard.setValue(row,column, 0);
            clickedCell.setBackgroundResource(R.drawable.table_border_cell);
        }
    }
}