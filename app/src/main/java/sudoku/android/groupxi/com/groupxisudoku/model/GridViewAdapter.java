package sudoku.android.groupxi.com.groupxisudoku.model;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.List;

import sudoku.android.groupxi.com.groupxisudoku.R;


public class GridViewAdapter extends BaseAdapter {
    List<Integer> boardNumber;
    List<Integer> originalNumber;
    Context mContext;
    String[] native_strings;
    String[] chinese_strings;
    String[] current_strings;
    String[] not_current_strings;
    int size;

    int language; // 0 for native and 1 for Chinese

    boolean clicked = false; // if a cell has been clicked
    Button clickedCell = null; // reference of the clicked cell
    int row;
    int column;

    public GridViewAdapter(List<Integer> isSource, String[] native_strings, String[] chinese_strings, int language, int size, Context context) {
        this.boardNumber = isSource;
        this.originalNumber = isSource;
        this.native_strings = native_strings;
        this.chinese_strings = chinese_strings;
        this.language = language;
        this.size = size;
        this.mContext = context;
        if(language == 0){
            current_strings = native_strings;
            not_current_strings = chinese_strings;
        }else{
            current_strings = chinese_strings;
            not_current_strings = native_strings;
        }

    }

    @Override
    public int getCount() {
        return boardNumber.size();
    }

    @Override
    public Object getItem(int position) {
        return boardNumber.get(position);
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
            if(size == 4){
                button.setLayoutParams(new GridView.LayoutParams(220, 220));
            }else if(size == 6){
                button.setLayoutParams(new GridView.LayoutParams(160, 160));
            }else if(size == 9){
                button.setLayoutParams(new GridView.LayoutParams(115, 115));
            }else{

            }

        }else{

            button = (Button)convertView;
        }
        //setMargins(button,0,0,8,0);
        //setCellBackground(button, position);
        //button.setTextColor(Color.WHITE);
        setCellBackground(button, position);

        button.setTextColor(Color.WHITE);



        // set string on board;
        if(boardNumber.get(position) != 0 && boardNumber.get(position) > 0) {
            button.setText(current_strings[boardNumber.get(position)-1]);
        }
        else if(boardNumber.get(position) != 0 && boardNumber.get(position) < 0){
            button.setText(not_current_strings[-1*(boardNumber.get(position)-1)]);
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
        if( ( (row+1)%3 == 0 && (row+1)%9 != 0) && (column+1)%3 == 0 && (column+1)%9 != 0) {
            button.setBackgroundResource(R.drawable.table_border_cell_bottom_right);
        }
        else if ((column+1)%3 == 0 && (column+1)%9 != 0){
            button.setBackgroundResource(R.drawable.table_border_cell_right);
        }
        else if((row+1)%3 == 0 && (row+1)%9 != 0) {
            button.setBackgroundResource(R.drawable.table_border_cell_bottom);
        }
        else {
            button.setBackgroundResource(R.drawable.table_border_cell);
        }
    }
    private void setCellBackground(Button button, int position){
        if( ( (position/9 + 1)%3 == 0 && (position/9+1)%9 != 0) && (position+1)%3 == 0 && (position+1)%9 != 0) {
            button.setBackgroundResource(R.drawable.table_border_cell_bottom_right);
        }
        else if ((position+1)%3 == 0 && (position+1)%9 != 0 ){
            button.setBackgroundResource(R.drawable.table_border_cell_right);
        }
        else if( (position/9 + 1)%3 == 0 && (position/9+1)%9 != 0){
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
        }
    }
    public void updateBoardNumber(int position, int value){
        boardNumber.set(position, value);
    }
}
