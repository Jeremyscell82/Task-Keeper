package com.bitbytebitcreations.tasks;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by JeremysMac on 6/2/16.
 */
public class DetailFragment extends Fragment {

    boolean newTask;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_frag, container, false);

        //DECLARE UI
        CardView cardView = (CardView) view.findViewById(R.id.taskCard);
        EditText task = (EditText) view.findViewById(R.id.taskBody);
        TextView taskDOB = (TextView) view.findViewById(R.id.taskDOB);
        TextView taskDUE = (TextView) view.findViewById(R.id.taskDUE);
        ImageView taskPRIO = (ImageView) view.findViewById(R.id.taskPRIORITY);
        //SET UP FOR KEYBOARD
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //GET PASSED IN DATA
        Bundle bundle = getArguments();
        if (bundle != null){
            newTask = bundle.getBoolean("NEW");
            if (newTask){
                //ENABLE KEYBOARD
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                task.setText("");
            }
        }

        //SET LISTENERS
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("TAG","ON LONG PRESS CLICKED");
                return false;
            }
        });




//        task.setCursorVisible(false);

        //SET DATA


        return view;
    }
}
