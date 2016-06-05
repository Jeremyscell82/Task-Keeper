package com.bitbytebitcreations.tasks;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by JeremysMac on 6/2/16.
 */
public class DetailFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    boolean newTask;
    int[] mTODAY;
    //UI
    EditText mTask;
    TextView mTaskDOB;
    TextView mTaskDUE;
    ImageView mTaskPRIO;
    FloatingActionButton mFAB;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_frag, container, false);

        //DECLARE UI
        CardView cardView = (CardView) view.findViewById(R.id.taskCard);
        mTask = (EditText) view.findViewById(R.id.taskBody);
        mTaskDOB = (TextView) view.findViewById(R.id.taskDOB);
        mTaskDUE = (TextView) view.findViewById(R.id.taskDUE);
        mTaskPRIO = (ImageView) view.findViewById(R.id.taskPRIORITY);
        mFAB = (FloatingActionButton) getActivity().findViewById(R.id.fab_detail);

        //SET UP UI
        mTODAY = getCurrentDate();

        //SET UP FOR KEYBOARD
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



        //SET LISTENERS
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("TAG","ON LONG PRESS CLICKED");
                return false;
            }
        });

        //SET DATE OF BIRTH
        mTaskDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO CHANGE MTODAY TO MDOB FROM DB
                DatePickerDialog dateDialog = new DatePickerDialog(getActivity(), datePickerListener, mTODAY[2], mTODAY[0], mTODAY[1]);
                dateDialog.show();
            }
        });
        //SET PRIORITY
        mTaskPRIO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priorityDialog();
            }
        });

        //SET DUE DATE
        mTaskDUE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), datePickerListener, mTODAY[2], mTODAY[0], mTODAY[1]);
                datePickerDialog.show();
            }
        });
        //SET UP KEYBOARD AND FAB TO WORK AS A TEAM
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()){
            mFAB.hide();
        } else {
            mFAB.show();
        }

        //GET PASSED IN DATA
        Bundle bundle = getArguments();
        if (bundle != null){
            newTask = bundle.getBoolean("NEW");
            if (newTask){
                //SET UP AS NEW
                setUpAsNew();
            } else {
                //SET UP FROM DB - BUNDLE
            }
        }

//        task.setCursorVisible(false);

        //SET DATA


        return view;
    }

    public void fabDelay(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFAB.show();
            }
        }, 800);
    }



    /*==============================================================================================
                                              SET UP AS NEW TASK
     ==============================================================================================*/
    public void setUpAsNew(){
        mFAB.hide(); //HIDE AS KEYBOARD WILL BE VISIBLE
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); //ENABLE KEYBOARD
        String currDate = (mTODAY[0]+1)+"/"+mTODAY[1]+"/"+mTODAY[2];
        String dueDate = (mTODAY[0]+2)+"/"+mTODAY[1]+"/"+mTODAY[2]; //PRESET TO ONE MONTH DUE DATES....todo make dynamic
        mTaskDOB.setText(currDate);
        mTaskDUE.setText(dueDate);


        //TODO TEMO
        mTask.setText("");

    }


    /*==============================================================================================
                                            SET UP AS EXISTING TASK
     ==============================================================================================*/
    public void setUpFromDB(){
        fabDelay(); //DISPLAY FAB AFTER UI EFFECTS
    }



    /*============
    DATE FUNCTIONS
     ============*/
    public int[] getCurrentDate(){
        final Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        int[] currDate = {month, day, year};
        return currDate;
    }

    //CREATE DIALOG
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mTaskDOB.setText((selectedMonth+1)+"/"+selectedDay+"/"+selectedYear);
        }
    };

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }
    /*================
     PRIORITY SWITCHER
     ================*/
    //DIALOG CALL
    public void priorityDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.priority_choose)
                .setItems(R.array.priority_colors, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        prioritySwitcher(which);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    //SWITCHER
    public void prioritySwitcher(int priority){
        switch (priority){
            case 0:
                mTaskPRIO.setImageResource(R.color.neutral);
                break;
            case 1:
                mTaskPRIO.setImageResource(R.color.yellow);
                break;
            case 2:
                mTaskPRIO.setImageResource(R.color.orange);
                break;
            case 3:
                mTaskPRIO.setImageResource(R.color.red);
                break;
        }
    }

}
