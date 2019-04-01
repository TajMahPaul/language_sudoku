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

    boolean clicked = false; // if a cell has been clicked
    boolean isListening = false;
    Button clickedCell = null; // reference of the clicked cell
    int row;
    int column;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
                if(isListening == true){
                    if(currentNumber.get(position) > 0){
                        int number = Integer.parseInt( (String) button.getText() );
                        t1.speak( current_strings[number-1], TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                    if(clickedCell != null){
                        setCellBackground(clickedCell, row, column);
                        //clickedCell.setBackgroundResource(R.drawable.table_border_cell);
                        clickedCell = null;
                    }


                    if(originalNumber.get(position) == 0) {
                        //if the cell is not original
                        button.setBackgroundResource(R.drawable.table_border_cell_selected);
                        clicked = true;
                        clickedCell = button;
                        row = position / size;
                        column = position % size;
                    }else{
                        Log.d(TAG, "onClick: nothing selected " + originalNumber.get(position));
                    }



            }
        });

        return button;
    }

    public boolean isClicked() {
        return clicked;
    }

    public Button getClickedCell() {
        return clickedCell;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
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

    public void uncheckClickedCell() {
        if(clickedCell != null){

            setCellBackground(clickedCell, row, column);

            clickedCell = null;
            clicked = false;
        }
    }
    public void updateBoardNumber(int position, int value){
        currentNumber.set(position, value);
    }



}
