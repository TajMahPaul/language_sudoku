package sudoku.android.groupxi.com.groupxisudoku.model;

import android.content.Context;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import sudoku.android.groupxi.com.groupxisudoku.R;

import static android.content.ContentValues.TAG;


public class GridViewAdapter extends BaseAdapter {
    TextToSpeech t1;
    List<Integer> currentNumber;
    List<Integer> originalNumber;
    Context mContext;
    String[] native_strings;
    String[] chinese_strings;
    String[] current_strings;
    String[] not_current_strings;
    int size;

    int language; // 0 for native and 1 for Chinese

    boolean emptyClicked = false; // if a empty cell has been emptyClicked
    boolean isListening = false;
    Button clickedCell = null;
    int clickedPosition = 0;// reference of the emptyClicked cell

    int clickedRow;
    int clickedColumn;
    int square_height;
    int square_width;
    int height;
    int width;

    public GridViewAdapter(List<Integer> isSource1, List<Integer> isSource2, String[] native_strings, String[] chinese_strings, int language, int size, int height, int width, boolean isListening, Context context) {
        this.originalNumber = new ArrayList<Integer>(isSource1);
        this.currentNumber = new ArrayList<Integer>(isSource2);
        this.native_strings = native_strings;
        this.chinese_strings = chinese_strings;
        this.language = language;
        this.size = size;
        this.mContext = context;
        this.height = height;
        this.width = width;
        this.isListening = isListening;

        if(language == 0){
            current_strings = native_strings;
            not_current_strings = chinese_strings;
            t1=new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status != TextToSpeech.ERROR) {
                        t1.setLanguage(Locale.ENGLISH);
                    }
                }
            });
        }else{
            current_strings = chinese_strings;
            not_current_strings = native_strings;
            t1=new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status != TextToSpeech.ERROR) {
                        t1.setLanguage(Locale.CHINESE);
                    }
                }
            });
        }
        if (size == 4){
            square_height=2;
            square_width=2;

        }
        else if(size == 6){
            square_width=3;
            square_height=2;

        }
        else if(size == 9){
            square_width=3;
            square_height=3;

        }
        else if(size == 12){
            square_width=4;
            square_height=3;

        }
    }


    @Override
    public int getCount() {
        return currentNumber.size();
    }

    @Override
    public Object getItem(int position) {
        return currentNumber.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Button button;
        if(convertView == null){
            button = new Button (mContext);
            // set size of each cell


        }else{

            button = (Button)convertView;
        }
        //setMargins(button,0,0,8,0);
        //setCellBackground(button, position);
        //button.setTextColor(Color.WHITE);
        setCellBackground(button, position);

        button.setTextColor(Color.WHITE);
        button.setMinimumHeight((height)/size);

        //Setting different text sizes for different grid sizes
        if (size == 4) {
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60);
        }
        else if(size == 6){
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);

        }
        else if(size == 9){
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);

        }


        // set string on board;
        if(currentNumber.get(position) != 0 && currentNumber.get(position) > 0) {
            if(isListening == true){

                button.setText(String.valueOf(currentNumber.get(position)));
            }else {
                button.setText(current_strings[currentNumber.get(position)-1]);
            }
        }
        else if(currentNumber.get(position) != 0 && currentNumber.get(position) < 0){
            button.setText(not_current_strings[-1*(currentNumber.get(position)+1)]);
        }
        else{

            button.setText("");
        }

        // set up button function
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //uncheck selected cell
                if(clickedCell != null){

                    setRCSBackground(clickedRow, clickedColumn, parent);

                    clickedCell = null;
                }


                if(isListening == true){
                    if(currentNumber.get(position) > 0){
                        int number = Integer.parseInt( (String) button.getText() );
                        t1.speak( current_strings[number-1], TextToSpeech.QUEUE_FLUSH, null);
                    }
                }


                setSelectedRCSBackground(position, parent);
                clickedCell = button;
                clickedPosition = position;
                clickedRow = position / size;
                clickedColumn = position % size;

                if(originalNumber.get(position) == 0) {

                    emptyClicked = true;

                }else{
                    emptyClicked = false;
                }



            }
        });

        return button;
    }

    public boolean isEmptyClicked() {
        return emptyClicked;
    }

    public Button getClickedCell() {
        return clickedCell;
    }

    public int getClickedRow() {
        return clickedRow;
    }

    public int getClickedColumn() {
        return clickedColumn;
    }

    public void setEmptyClicked(boolean emptyClicked) {
        this.emptyClicked = emptyClicked;
    }

    private void setCellBackground(Button button, int row, int column){
        if( ( (row+1)%square_height == 0 && (row+1)%size != 0) && (column+1)%square_width == 0 && (column+1)%size != 0) {
            button.setBackgroundResource(R.drawable.table_border_cell_bottom_right);
        }
        else if ((column+1)%square_width == 0 && (column+1)%size != 0){
            button.setBackgroundResource(R.drawable.table_border_cell_right);
        }
        else if((row+1)%square_height == 0 && (row+1)%size != 0) {
            button.setBackgroundResource(R.drawable.table_border_cell_bottom);
        }
        else {
            button.setBackgroundResource(R.drawable.table_border_cell);
        }
    }

    private void setSelectedCellBackground(Button button, int position){
        if( ( (position/size + 1)%square_height == 0 && (position/size+1)%size != 0) && (position+1)%square_width == 0 && (position+1)%size!= 0) {
            button.setBackgroundResource(R.drawable.table_border_cell_selected_bottom_right);
        }
        else if ((position+1)%square_width == 0 && (position+1)%size != 0 ){
            button.setBackgroundResource(R.drawable.table_border_cell_selected_right);
        }
        else if( (position/size + 1)%square_height == 0 && (position/size+1)%size != 0){
            button.setBackgroundResource(R.drawable.table_border_cell_selected_bottom);
        }
        else{
            button.setBackgroundResource(R.drawable.table_border_cell_selected);
        }
    }

    private void setCellBackground(Button button, int position){
        if( ( (position/size + 1)%square_height == 0 && (position/size+1)%size != 0) && (position+1)%square_width == 0 && (position+1)%size!= 0) {
            button.setBackgroundResource(R.drawable.table_border_cell_bottom_right);
        }
        else if ((position+1)%square_width == 0 && (position+1)%size != 0 ){
            button.setBackgroundResource(R.drawable.table_border_cell_right);
        }
        else if( (position/size + 1)%square_height == 0 && (position/size+1)%size != 0){
            button.setBackgroundResource(R.drawable.table_border_cell_bottom);
        }
        else{
            button.setBackgroundResource(R.drawable.table_border_cell);
        }
    }

    private void setSelectedRCSBackground(int position, ViewGroup parent){
        int cellRow = position/size;
        int cellColumn = position % size;
        for(int i = 0; i < size; i++){
            setSelectedCellBackground((Button)parent.getChildAt(cellRow*size+i), cellRow*size+i);

            setSelectedCellBackground((Button)parent.getChildAt(cellColumn+size*i), cellColumn+size*i);
        }

        int startPosition = position - (cellRow%square_height)*size - (cellColumn%square_width);
        for(int j = 0; j < square_height; j++){
            for(int k = 0; k < square_width; k++){
                setSelectedCellBackground((Button)parent.getChildAt(startPosition + j * size + k), startPosition + j * size + k);
            }
        }

    }

    private void setRCSBackground(int selectedRow, int selectedColumn, ViewGroup parent){

        for(int i = 0; i < size; i++){
            setCellBackground((Button)parent.getChildAt(selectedRow*size+i), selectedRow*size+i);

            setCellBackground((Button)parent.getChildAt(selectedColumn+size*i), selectedColumn+size*i);
        }

        int startPosition = (selectedRow-(selectedRow%square_height))*size + selectedColumn - (selectedColumn%square_width);
        for(int j = 0; j < square_height; j++){
            for(int k = 0; k < square_width; k++){
                setCellBackground((Button)parent.getChildAt(startPosition + j * size + k), startPosition + j * size + k);
            }
        }

    }

    public void uncheckClickedCell(GridView gridView) {
        if(clickedCell != null){

            for(int i = 0; i < size; i++){
                setCellBackground((Button)gridView.getChildAt(clickedRow*size+i), clickedRow*size+i);

                setCellBackground((Button)gridView.getChildAt(clickedColumn+size*i), clickedColumn+size*i);
            }

            int startPosition = (clickedRow-(clickedRow%square_height))*size + clickedColumn - (clickedColumn%square_width);
            for(int j = 0; j < square_height; j++){
                for(int k = 0; k < square_width; k++){
                    setCellBackground((Button)gridView.getChildAt(startPosition + j * size + k), startPosition + j * size + k);
                }
            }

            clickedCell = null;
            emptyClicked = false;
        }
    }

    public void updateBoardNumber(int position, int value){
        currentNumber.set(position, value);
    }



}
