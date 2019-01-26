package sudoku.android.groupxi.com.groupxisudoku;

import android.content.Intent;
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
    private List<Pair> list = new ArrayList<Pair>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();

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
                    tempCellGroupFragment.setValue(groupPosition, currentValue);
                }
            }
        }

        //add function to number buttons
        num_buttons[0] = findViewById(R.id.num_button1);
        num_buttons[1] = findViewById(R.id.num_button2);
        num_buttons[2] = findViewById(R.id.num_button3);
        num_buttons[3] = findViewById(R.id.num_button4);
        num_buttons[4] = findViewById(R.id.num_button5);
        num_buttons[5] = findViewById(R.id.num_button6);
        num_buttons[6] = findViewById(R.id.num_button7);
        num_buttons[7] = findViewById(R.id.num_button8);
        num_buttons[8] = findViewById(R.id.num_button9);
        for(int i = 0; i < 9; i++){
            final int finalI = i+1;
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
                        }else{
                            Toast.makeText(GameActivity.this, R.string.board_incorrect, Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(GameActivity.this, R.string.board_incorrect, Toast.LENGTH_SHORT).show();
                    }

                    if(currentBoard.isBoardFull() == true){
                        Toast.makeText(GameActivity.this, "game over", Toast.LENGTH_SHORT).show();
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


    public void onGoBackButtonClicked(View view) {
        finish();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            int row = ((clickedGroup-1)/3)*3 + (clickedCellId/3);
            int column = ((clickedGroup-1)%3)*3 + ((clickedCellId)%3);

            if (data.getBooleanExtra("removePiece", false)) {
                clickedCell.setText("");
                clickedCell.setBackground(getResources().getDrawable(R.drawable.table_border_cell));
                currentBoard.setValue(row, column, 0);
            } else {
                int number = data.getIntExtra("chosenNumber", 1);
                clickedCell.setText(String.valueOf(number));
                currentBoard.setValue(row, column, number);

                boolean isUnsure = data.getBooleanExtra("isUnsure", false);
                if (isUnsure) {
                    clickedCell.setBackground(getResources().getDrawable(R.drawable.table_border_cell_unsure));
                } else {
                    clickedCell.setBackground(getResources().getDrawable(R.drawable.table_border_cell));
                }
            }
        }
    }

    @Override
    public void onFragmentInteraction(int groupId, int cellId, View view) {
        Log.i(TAG, "Clicked group " + groupId + ", cell " + cellId);

        //if there's selected cell previously, reset the background of that cell
        if(clickedCell != null){
            clickedCell.setBackgroundResource(R.drawable.table_border_cell);
        }

        if (!isStartPiece(groupId, cellId)) {
            clickedCell = (TextView) view;
            clickedGroup = groupId;
            clickedCellId = cellId;
            view.setBackgroundResource(R.drawable.table_border_cell_selected);
        } else {
            Toast.makeText(this, getString(R.string.start_piece_error), Toast.LENGTH_SHORT).show();
            clickedCell = null;
            clickedGroup = 0;
            clickedCellId = 0;
        }
    }

    public static class MainActivity {
    }
}