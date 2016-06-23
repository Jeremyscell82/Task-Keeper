package com.bitbytebitcreations.tasks;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.bitbytebitcreations.tasks.utilz.Settings_Holder;
import com.bitbytebitcreations.tasks.utilz.Task_Object;
import com.bitbytebitcreations.tasks.utilz.Theme_Applier;

import java.io.Serializable;
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
    int frag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //SETUP TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTheme(toolbar);
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
        Task_Object task = (Task_Object) getIntent().getSerializableExtra("TASK");
        frag = getIntent().getIntExtra("FRAG", 0);
        Log.i("TEST", "FRAG: " + frag);
//        if (newTask)getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); //ENABLE KEYBOARD

        getSupportActionBar().setTitle(title);

        loadTask(newTask, title, task);


    }


    public void loadTask(boolean newTask, String title, Task_Object task){
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("NEW", newTask);
        bundle.putString("TITLE", title);
        bundle.putSerializable("TASK", (Serializable) task);
        bundle.putInt("FRAG", frag);
        fragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.detail_container, fragment)
                .commit();
    }

    /* ===========SET THEME=============*/
    private void setTheme(Toolbar toolbar){
        Settings_Holder settings_holder = new Settings_Holder(this);
        String themeKey = settings_holder.getThemeKey();
        int theme = settings_holder.getINTSettings(themeKey);
        //NOW APPLY THEME
        Theme_Applier applyTheme = new Theme_Applier();
        applyTheme.themeManager(theme, this, toolbar, false); //THEME ACTIVITY TOOLBAR IS-ON-MAIN
    }


    /*
    ========MENU========
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
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
