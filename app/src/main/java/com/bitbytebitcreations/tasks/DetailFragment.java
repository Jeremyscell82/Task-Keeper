package com.bitbytebitcreations.tasks;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import com.bitbytebitcreations.tasks.utilz.DB.DB_Controller;

import java.sql.RowId;
import java.util.Calendar;

/**
 * Created by JeremysMac on 6/2/16.
 */
public class DetailFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    final static String TAG = "DETAIL_FRAG";
    DB_Controller controller;
    boolean mIsNewTask;
    int[] mTODAY;
    int whcDate;
    int mPriority; //0 = NEUTRAL & 3 = HIGH
    //UI
    String mListName;
//    String[] masterTask;
    int mTaskID;
    EditText mTask;
    TextView mTaskDOB;
    TextView mTaskDUE;
    ImageView mTaskPRIO;
    FloatingActionButton mFAB;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_frag, container, false);
        //SET UP FOR KEYBOARD
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //SET MENU CONTROLS
        setHasOptionsMenu(true);

        //DECLARE UI
        CardView cardView = (CardView) view.findViewById(R.id.taskCard);
        mTask = (EditText) view.findViewById(R.id.taskBody);
        mTaskDOB = (TextView) view.findViewById(R.id.taskDOB);
        mTaskDUE = (TextView) view.findViewById(R.id.taskDUE);
        mTaskPRIO = (ImageView) view.findViewById(R.id.taskPRIORITY);

        //SET UP UI
        mTODAY = getCurrentDate();
        mIsNewTask = false;
        controller = new DB_Controller();
        /*
        GET BUNDLE DATA
         */
        Bundle bundle = getArguments();
        if (bundle != null){
            mIsNewTask = bundle.getBoolean("NEW");
            mListName = bundle.getString("TITLE");
            Log.i(TAG, "MASTER LIST: "+ mListName);
            if (mIsNewTask){
                //SET UP AS NEW
                setUpAsNew();
            } else {
                //SET UP FROM DB - BUNDLE
                String[] masterTask = bundle.getStringArray("TASK"); //CONTAINS ID
//                mTaskLIST = task[0]; //SET ID
                Log.i(TAG, "TASK ID: " + masterTask[0]);
                mTaskID = Integer.valueOf(masterTask[0]);
                setUpFromDB(masterTask);
            }
        }







//        //SET LISTENERS
//        cardView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Log.i("TAG","ON LONG PRESS CLICKED");
//                return false;
//            }
//        });

        //SET DATE OF BIRTH
        mTaskDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO CHANGE MTODAY TO MDOB FROM DB
                whcDate = 0;
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
                whcDate = 1;
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), datePickerListener, mTODAY[2], mTODAY[0], mTODAY[1]);
                datePickerDialog.show();
            }
        });




//        task.setCursorVisible(false);

        //SET DATA


        return view;
    }

//    public void fabDelay(){
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mFAB.show();
//            }
//        }, 800);
//    }


    /*
    ========MENU========
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_save:
                Log.i("", "wtf");
                saveTask();
//                onBackPressed();
                break;
            case R.id.action_delete:
//                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    /*==============================================================================================
                                              SET UP AS NEW TASK
     ==============================================================================================*/
    public void setUpAsNew(){
        Log.i(TAG, "THIS IS NEW");
//        mFAB.hide(); //HIDE AS KEYBOARD WILL BE VISIBLE
        String currDate = (mTODAY[0]+1)+"/"+mTODAY[1]+"/"+mTODAY[2];
        String dueDate = (mTODAY[0]+2)+"/"+mTODAY[1]+"/"+mTODAY[2]; //PRESET TO ONE MONTH DUE DATES....todo make dynamic
        mTaskDOB.setText(currDate);
        mTaskDUE.setText(dueDate);
        prioritySwitcher(0);
        mTask.setText("");
        //DISPLAY KEYBOARD
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    /*==============================================================================================
                                            SET UP AS EXISTING TASK
     ==============================================================================================*/
    public void setUpFromDB(String[] task){
//        fabDelay(); //DISPLAY FAB AFTER UI EFFECTS
        mTaskDOB.setText(task[2]);
        mTaskDUE.setText(task[3]);
        prioritySwitcher(Integer.parseInt(task[4]));
        mTask.setText(task[5]);
//        for (int i = 0; i < task.length; i++){
//            Log.i(TAG, "CURRENT TASK ITEMS: " + task[i]);
//        }
    }

    /*==============================================================================================
                                            SAVE THE TASK
     ==============================================================================================*/
    public void saveTask(){
        Log.i("TEST", "MASTER LIST: "+ mListName);
        String lis = mListName;
        String dob = (String) mTaskDOB.getText();
        String due = (String) mTaskDUE.getText();
        String pri = String.valueOf(mPriority);
        Log.i("TEST", "PRIORITY: " + pri);
        String tas = mTask.getText().toString();
        String[] task = new String[]{lis, dob, due, pri, tas};
        Log.i("TEST", "save task running......");
//        for (int x = 0; x <= task.length; x++){
//            Log.i("DETAIL FRAGMENT", task[x]);
//        }
        controller.openDB(getActivity());
        if (mIsNewTask){
            Log.i("TEST", "THIS IS BEING SAVED");
            controller.addTask(task);
        } else {
            Log.i("TEST", "THIS IS BEING SAVED: " + mTaskID);
            controller.updateTask(mTaskID, task);
        }
        controller.closeDB();
        DetailActivity activity = (DetailActivity) getActivity();
//        activity.revealView();
        activity.closeActivity();


//        ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in_top, R.anim.slide_out_bottom);
//        Intent intent = new Intent(getActivity(), MainActivity.class);
//        startActivity(intent, options.toBundle());
//        Intent intent = new Intent(getActivity(), MainActivity.class);
//        startActivity(intent);
//        getActivity().finish();
//        getActivity().overridePendingTransition(0, R.anim.slide_out_bottom);
//        startActivity(intent);
    }

    public void closeTask(){

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
            if (whcDate==0)mTaskDOB.setText((selectedMonth+1)+"/"+selectedDay+"/"+selectedYear);
            if (whcDate==1)mTaskDUE.setText((selectedMonth+1)+"/"+selectedDay+"/"+selectedYear);
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
    //COLOR SWITCHER
    public void prioritySwitcher(int priority){
        switch (priority){
            case 0:
                mTaskPRIO.setImageResource(R.color.neutral);
                mPriority = 0;
                break;
            case 1:
                mTaskPRIO.setImageResource(R.color.yellow);
                mPriority = 1;
                break;
            case 2:
                mTaskPRIO.setImageResource(R.color.orange);
                mPriority = 2;
                break;
            case 3:
                mTaskPRIO.setImageResource(R.color.red);
                mPriority = 3;
                break;
        }
    }

}
