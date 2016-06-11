package com.bitbytebitcreations.tasks;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by JeremysMac on 6/2/16.
 */
public class DetailActivity extends AppCompatActivity {

    final String TAG = "DETAIL_ACTIVITY";
    FloatingActionButton mFAB;

    boolean EDIT_MODE;
    MenuItem editMenu;
    MenuItem saveMenu;
    ArrayList<String[]> masterList;
    View mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //SETUP TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //SET UP UI
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mView = findViewById(R.id.revealView);

        //GET PASSED IN DATA
        boolean newTask = getIntent().getBooleanExtra("NEW", false);
        String title = getIntent().getStringExtra("TITLE");
        String[] task = getIntent().getStringArrayExtra("TASK");
//        if (newTask)getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); //ENABLE KEYBOARD

        getSupportActionBar().setTitle(title);

        loadTask(newTask, title, task);
//
//        mFAB = (FloatingActionButton) findViewById(R.id.fab_detail);
//        mFAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "TASK SAVED", Toast.LENGTH_SHORT).show();
//                //TODO SAVE TASK, REMOVE FROM STACK AND RELOAD MAIN ACTIVITY
//                onBackPressed();
//            }
//        });
        //FAB DELAY




    }


    public void loadTask(boolean newTask, String title, String[] task){
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("NEW", newTask);
        bundle.putString("TITLE", title);
        bundle.putStringArray("TASK", task);
        fragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.detail_container, fragment)
                .commit();
    }


    /*
    ========MENU========
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
//        editMenu = menu.findItem(R.id.action_edit);
//        saveMenu = menu.findItem(R.id.action_save);
//        saveMenu.setVisible(false);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        switch (id){
//            case R.id.action_save:
//                onBackPressed();
//                break;
//            case R.id.action_delete:
//                onBackPressed();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void setEditMode(boolean editMode){
//        if (editMode){
//            saveMenu.setVisible(true);
//            editMenu.setVisible(false);
//        } else {
//            saveMenu.setVisible(false);
//            editMenu.setVisible(true);
//        }
    }



    @Override
    public void onBackPressed() {
//        mFAB.hide();
        super.onBackPressed();
    }


    public void revealView(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //LOLLIPOP ANIMATIONS
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
                    closeActivity();
                }
            });
        } else {
            //NON LOLLIPOP ANIMATIONS
            Log.i(TAG, "NOT RUNNING LOLLIPOP ANIMATIONS");
            closeActivity();
        }
    }

    public void closeActivity(){
//        ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_top, R.anim.slide_out_bottom);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, R.anim.fade_out);
    }

//    public void dismissView(){
//        //ONLY CALLED IF BUILD IS > LOLLIPOP
//        int x = (int) myView.getWidth()/2;
//        int y = 0;
//        int initRadius = myView.getHeight();
//        Animator dismiss = ViewAnimationUtils.createCircularReveal(
//                myView,
//                x,
//                y,
//                initRadius,
//                0
//        );
//        dismiss.setDuration(400);
//        dismiss.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                myView.setVisibility(View.INVISIBLE);
//            }
//        });
//        dismiss.start();
////        launchSatellite();
//
//    }
}
