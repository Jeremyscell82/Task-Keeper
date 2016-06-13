package com.bitbytebitcreations.tasks;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbytebitcreations.tasks.utilz.DB.DB_Controller;

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
    int mTaskID;
    EditText mTask;
    TextView mTaskDOB;
    TextView mTaskDUE;
    ImageView mTaskPRIO;
    FloatingActionButton mFAB;
//    CardView mCardView;
    int mFrag;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_frag, container, false);
        //SET UP FOR KEYBOARD
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //SET MENU CONTROLS
        setHasOptionsMenu(true);

        //DECLARE UI
//        mCardView = (CardView) view.findViewById(R.id.taskCard);
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
            mFrag = bundle.getInt("FRAG");
            Log.i(TAG, "MASTER LIST: "+ mListName);
            if (mIsNewTask){
                //SET UP AS NEW
                setUpAsNew();
            } else {
                //SET UP FROM DB - BUNDLE
                String[] masterTask = bundle.getStringArray("TASK"); //CONTAINS ID
                Log.i(TAG, "TASK ID: " + masterTask[0]);
                mTaskID = Integer.valueOf(masterTask[0]);
                setUpFromDB(masterTask);
            }
        }


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



        //SET DATA


        return view;
    }


    /*
    ========MENU========
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_save:
                saveTask();
                break;
            case R.id.action_delete:
                //todo delete
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    /*==============================================================================================
                                              SET UP AS NEW TASK
     ==============================================================================================*/
    public void setUpAsNew(){
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
        mTaskDOB.setText(task[2]);
        mTaskDUE.setText(task[3]);
        prioritySwitcher(Integer.parseInt(task[4]));
        mTask.setText(task[5]);
    }

    /*==============================================================================================
                                            SAVE THE TASK
     ==============================================================================================*/
    public void saveTask(){
        String lis = mListName;
        String dob = (String) mTaskDOB.getText();
        String due = (String) mTaskDUE.getText();
        String pri = String.valueOf(mPriority);
        String tas = mTask.getText().toString();
        String[] task = new String[]{lis, dob, due, pri, tas};
        controller.openDB(getActivity());
        if (mIsNewTask){
            controller.addTask(task);
        } else {
            controller.updateTask(mTaskID, task);
        }
        controller.closeDB();
//        DetailActivity activity = (DetailActivity) getActivity();
//        activity.onBackPressed();
//        activity.closeActivity();


//        revealView();

        ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.scale_to_center);
//        View view = getActivity().findViewById(R.id.revealView);
//        int width = view.getWidth();
//        int height = view.getHeight();
//        ActivityOptions options = ActivityOptions.makeScaleUpAnimation(getView(), 0, 0, 0, 0);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("INNER", true);
        intent.putExtra("FRAG", mFrag);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent, options.toBundle());

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


    public void revealView(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //LOLLIPOP ANIMATIONS
            View mView = getActivity().findViewById(R.id.revealView);
            int x = (int) mView.getWidth()/2;
            int y = (int) mView.getHeight()/2;
            int finalRadius = mView.getHeight();
            final Animator reveal = ViewAnimationUtils.createCircularReveal(
                    mView,
                    x,
                    y,
                    0,
                    finalRadius
            );
            reveal.setDuration(800);
            mView.setVisibility(View.VISIBLE);
            reveal.start();
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    launchSatellite();
//                }
//            }, 200);
            reveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
//                    dismissView();
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("INNER", true);
                    startActivity(intent, options.toBundle());
                }
            });
        } else {
            //NON LOLLIPOP ANIMATIONS
//            Log.i(TAG, "NOT RUNNING LOLLIPOP ANIMATIONS");
//            ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra("INNER", true);
            startActivity(intent);
        }
    }

}
