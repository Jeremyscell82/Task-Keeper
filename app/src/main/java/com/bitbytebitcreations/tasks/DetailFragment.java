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
import com.bitbytebitcreations.tasks.utilz.Settings_Holder;
import com.bitbytebitcreations.tasks.utilz.Task_Object;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by JeremysMac on 6/2/16.
 */
public class DetailFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    final static String TAG = "DETAIL_FRAG";
    DB_Controller controller;
    boolean mIsNewTask;
    int[] mTODAY;
    int[] mDUEDAY;
    int whcDate;
    int mPriority; //0 = NEUTRAL & 3 = HIGH
    //UI
    String[] mListName;
    long mTaskID;
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
        mTODAY = getTODAY();
//        mDUEDAY = getDUEDAY();
        mIsNewTask = false;
        controller = new DB_Controller();
        /*
        GET BUNDLE DATA
         */
        Bundle bundle = getArguments();
        if (bundle != null){
            mIsNewTask = bundle.getBoolean("NEW");

            mFrag = bundle.getInt("FRAG");
            if (mIsNewTask){
                //SET UP AS NEW
                mListName = (String[]) bundle.getSerializable("TITLE");
                setUpAsNew();
            } else {
                //SET UP FROM DB - BUNDLE
                Task_Object task = (Task_Object) bundle.getSerializable("TASK"); //CONTAINS ID
                Log.i(TAG, "TASK ID: " + task.rowID);
                mListName = new String[]{String.valueOf(task.listID), task.listName};
                mTaskID = task.rowID;
                setUpFromDB(task);
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
                deleteTask();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    /*==============================================================================================
                                              SET UP AS NEW TASK
     ==============================================================================================*/
    public void setUpAsNew(){
//        int dueTime = getDueDate();
//        Log.i("TEST-TIME", "DUE TIME -->" + dueTime);
//        String currDate = (mTODAY[0]+1)+"/"+mTODAY[1]+"/"+mTODAY[2];
        String currDate = getCurrentDate();
        mTaskDOB.setText(currDate);
//        String dueDate = (mTODAY[0]+1)+"/"+mTODAY[1]+"/"+mTODAY[2]; //PRESET TO ONE MONTH DUE DATES....todo make dynamic
//        getDueDate(currDate);
//        getDueDate();
        mTaskDUE.setText(getDueDate());
        prioritySwitcher(0);
        mTask.setText("");
        //DISPLAY KEYBOARD
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


//    public String getDueDate(String currDate){
//        //GET THE CURRENT DATE
//        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//        Date strDate = null;
//        try {
//            strDate = sdf.parse(currDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//
//        }
//        //GET THE SAVED DATE INT
//        Settings_Holder settings_holder = new Settings_Holder(getActivity());
//        String timeKey = settings_holder.getTimeKey();
//        int dueTime = settings_holder.getINTSettings(timeKey);
//
//        if (strDate != null){
//            //ADD X DAYS TO CURRENT DATE AND RETURN
//            switch (dueTime){
//                case 0:
//                    int day = 7;
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(strDate);
//                    cal.add(Calendar.DATE, day);
//                    date = cal.getTime();
//            }
//        }
//
//
//    }


    /*==============================================================================================
                                            SET UP AS EXISTING TASK
     ==============================================================================================*/
    public void setUpFromDB(Task_Object task){
        mTaskDOB.setText(task.dobDate);
        mTaskDUE.setText(task.dueDate);
        prioritySwitcher(task.priority);
        mTask.setText(task.body);
    }

    /*==============================================================================================
                                            SAVE THE TASK
     ==============================================================================================*/
    public void saveTask(){
        Task_Object task = new Task_Object();
        task.setListID(Long.parseLong(mListName[0]));
        task.setDobDate((String) mTaskDOB.getText());
        task.setPriority(mPriority);
        task.setDueDate((String) mTaskDUE.getText());
        task.setTask(mTask.getText().toString());
        controller.openDB(getActivity());
        if (mIsNewTask){
            controller.addTask(task);
        } else {
            controller.updateTask(mTaskID, task);
        }
        controller.closeDB();
        closeTask();
    }

    /*==============================================================================================
                                            DELETE THE TASK
     ==============================================================================================*/
    public void deleteTask(){
        if (!mIsNewTask){
            controller.openDB(getActivity());
            controller.deleteTask(mTaskID);
            controller.closeDB();
        }
        closeTask();
    }

    /*
    CLOSE TASK ----->> CLOSE ACTIVITY -> CLEAR STACK - ADD ANIMATIONS
     */
    public void closeTask(){
        ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.scale_to_center);
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
    public int[] getTODAY(){
        final Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        int[] currDate = {month, day, year};
        return currDate;
    }
    private String getCurrentDate(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
    private String getDueDate(){
        int[] defaults = getResources().getIntArray(R.array.dueDateDays);
        Settings_Holder settings_holder = new Settings_Holder(getActivity());
        String timeKey = settings_holder.getTimeKey();
        int days = defaults[settings_holder.getINTSettings(timeKey)];
        Log.i(TAG, "DAYS SAVED INT: " + days);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        date = cal.getTime();
        Log.i(TAG, "DUE DATE TIME: " + date);
        SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = sdfDate.format(date);
        return strDate;
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
