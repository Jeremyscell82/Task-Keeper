package com.bitbytebitcreations.tasks;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bitbytebitcreations.tasks.utilz.DB.DB_Controller;
import com.bitbytebitcreations.tasks.utilz.ViewPager_Controller;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final private String TAG = "MAIN_ACTIVITY";
    BottomSheetBehavior bottomSheetBehavior;
    DB_Controller controller;
    ViewPager mViewPager;
    String[] mTitleList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = new DB_Controller();




        //LOAD UP THE VIEWPAGER
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTitleList = getTitles();
        ViewPager_Controller viewPager_controller = new ViewPager_Controller(getSupportFragmentManager(), mTitleList, this);
        mViewPager.setAdapter(viewPager_controller);
        Log.i(TAG, "MASTERLIST HAS RUN");

        View bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);


        //CHECK IF INNER CALL
        boolean innerCall = getIntent().getBooleanExtra("INNER", false);
        if (innerCall){
            int frag = getIntent().getIntExtra("FRAG", 0);
            Log.i(TAG, "THIS IS A INNER CALL..........."+ frag);
            mViewPager.setCurrentItem(frag);
        }

        //IF NO LISTS..SHOW USER THE DIALOG
        if (mTitleList == null)showDialog();


    }

    public int getCurrentFragNum(){
        return mViewPager.getCurrentItem();
    }


    /*
    ========MENU========
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_add_list:
                //LOAD ADD LIST DIALOG
                showDialog();
                break;
            case R.id.action_settings:
                //LOAD SETTINGS ACTIVITY
//                ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_bottom, R.anim.fade_out);
//                Intent intent = new Intent(this, SettingsActivity.class);
//                startActivity(intent, options.toBundle());
//                revealView();
                startSettings();
                break;
        }
        return super.onOptionsItemSelected(item);
    }/*END OF MENU*/

    public void expandBottomSheet(View v){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    /*=============
    ADD LIST DIALOG
     =============*/
    public void showDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.addlist_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle(R.string.addlist_dialog_title);
        dialogBuilder.setMessage(R.string.addlist_dialog_hint);
        dialogBuilder.setPositiveButton(R.string.addlist_dialog_create, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                String title = edt.getText().toString();
                if (!title.isEmpty() && !title.startsWith(" ")){
                    saveTitle(title);
                    dialog.dismiss();
                } else {
                    showToast("What are you really trying to do?");
                    dialog.dismiss();
                }


            }
        });
        dialogBuilder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();

    }
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /*
    DB CALLS
     */
    public void saveTitle(String title){
        controller.openDB(this);
        controller.addTitle(title);
        controller.closeDB();

//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        int fragNum;
        if (mTitleList != null){
            fragNum = mTitleList.length;
        } else {
            fragNum = 0;
        }
        Log.i("TEST", "FRAG NUM IS: --> "+fragNum);
        Toast.makeText(this, title+" has been created.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("NEW", true);
        intent.putExtra("FRAG", fragNum);
        startActivity(intent);
    }

    public String[] getTitles(){
        controller.openDB(this);
        ArrayList<String[]> masterList = controller.getAllTitles();
        controller.closeDB();
        if (masterList != null){
            List<String> titleList = new ArrayList<String>();
            for (int i = 0; i < masterList.size(); i++){
                titleList.add(masterList.get(i)[1]);
                Log.i(TAG, "RUNNING...");
            }
            if (titleList.size() != 0){
                String[] titles = new String[titleList.size()];
                titles = titleList.toArray(titles);
                return titles;
            }
        }

        Log.i(TAG, "NO TITLES SAVED...");
        return null;
    }
//    public ArrayList<String[]> getTasks(){
//        ArrayList<String[]> list = new ArrayList<>();
//        controller.openDB(this);
//        list = controller.getAllTasks("test");
//        controller.closeDB();
//        return list;
//    }


    /*
    REVEAL VIEW CODE
     */
    public void revealView(){
        final View view = findViewById(R.id.mainRevealView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //LOLLIPOP ANIMATIONS
            int cx = view.getMeasuredWidth();
            int cy = 0;
            float finalRadius = (float) Math.hypot(cx, view.getHeight());
            final Animator reveal = ViewAnimationUtils.createCircularReveal(
                    view,
                    cx,
                    cy,
                    0,
                    finalRadius
            );
            reveal.setDuration(200);
            view.setVisibility(View.VISIBLE);
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
//                    closeActivity();
                    startSettings();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.setVisibility(View.GONE);
                        }
                    }, 800);

                }
            });
        } else {
            //NON LOLLIPOP ANIMATIONS
            Log.i(TAG, "NOT RUNNING LOLLIPOP ANIMATIONS");
//            closeActivity();
            startSettings();
        }
    }



    public void startSettings(){
        ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.scale_in_corner, R.anim.fade_out);
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent, options.toBundle());
    }
}
