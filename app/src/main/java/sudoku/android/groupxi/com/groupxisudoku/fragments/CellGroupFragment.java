package sudoku.android.groupxi.com.groupxisudoku.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sudoku.android.groupxi.com.groupxisudoku.R;

public class CellGroupFragment extends Fragment {
    private int groupId;
    private OnFragmentInteractionListener mListener;
    private View view;

    public CellGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cell_group, container, false);

        //Set textview click listeners
        int textViews[] = new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4,
                         R.id.textView5, R.id.textView6, R.id.textView7, R.id.textView8, R.id.textView9};
        for (int textView1 : textViews) {
            TextView textView = view.findViewById(textView1);
            //textView.setText("1");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onFragmentInteraction(groupId, Integer.parseInt(view.getTag().toString()), view);
                }
            });
        }
        return view;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setValue(int position, int value) {
        int textViews[] = new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4,
                R.id.textView5, R.id.textView6, R.id.textView7, R.id.textView8, R.id.textView9};
        TextView textview = view.findViewById(textViews[position]);
        String text = String.valueOf(value);
        textview.setText(text);

    }

    public boolean checkGroupCorrect() {

        return true;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int groupId, int cellId, View view);
    }
}
