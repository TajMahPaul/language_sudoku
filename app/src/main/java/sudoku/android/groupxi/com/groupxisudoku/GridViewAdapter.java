package sudoku.android.groupxi.com.groupxisudoku;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import java.util.List;


public class GridViewAdapter extends BaseAdapter {
    List<Integer> boardNumber;
    List<Integer> originalNumber;
    Context mContext;
    String[] native_strings;
    String[] chinese_strings;
    String[] current_strings;

    int language; // 0 for native and 1 for Chinese

    boolean clicked = false;
    Button clickedCell = null;
    int row;
    int column;


    public GridViewAdapter(List<Integer> isSource, String[] native_strings, String[] chinese_strings, int language, Context context) {
        this.boardNumber = isSource;
        this.originalNumber = isSource;
        this.native_strings = native_strings;
        this.chinese_strings = chinese_strings;
        this.language = language;
        this.mContext = context;
        if(language == 0){
            current_strings = native_strings;
        }else{
            current_strings = chinese_strings;
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
            button.setBackgroundResource(R.drawable.table_border_cell);
            button.setTextColor(Color.WHITE);
            // set string on board
            if(boardNumber.get(position) != 0) {
                button.setText(current_strings[boardNumber.get(position)-1]);
            }else{
                button.setText("");
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //uncheck selected cell
                    if(clickedCell != null){
                        clickedCell.setBackgroundResource(R.drawable.table_border_cell);
                        clickedCell = null;
                    }


                    if(originalNumber.get(position) == 0) {
                        //if the cell is not original
                        button.setBackgroundResource(R.drawable.table_border_cell_selected);
                        clicked = true;
                        clickedCell = button;
                        row = position / 9;
                        column = position % 9;
                    }else{

                    }
                }
            });
        }else{

            button = (Button)convertView;
        }
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

    public void uncheckClickedCell() {
        if(clickedCell != null){
            clickedCell.setBackgroundResource(R.drawable.table_border_cell);
            clickedCell = null;
        }
    }
}
