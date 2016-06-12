package com.bitbytebitcreations.tasks;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bitbytebitcreations.tasks.utilz.Settings_Holder;

/**
 * Created by JeremysMac on 6/4/16.
 */
public class SettingsActivity extends AppCompatActivity {

    private final String PREFS_NAME = "SETTINGS";
    String FAB_KEY;
    boolean HIDE_FAB = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //SET UP THE SHARED PREFERENCE CLASS
        final Settings_Holder settings_holder = new Settings_Holder(this);

        //GET SAVED SETTINGS
        FAB_KEY = settings_holder.getFabKey();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //SETUP TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //SET UP UI
        TextView settings1 = (TextView) findViewById(R.id.textView);
        TextView settings2 = (TextView) findViewById(R.id.textView2);
        TextView hideFab = (TextView) findViewById(R.id.hideFab);
        final CheckBox settings3Box = (CheckBox) findViewById(R.id.checkBox);
        settings1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG","SETTINGS ONE CLICKED...");
            }
        });


        settings2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG","SETTINGS TWO CLICKED...");
            }
        });

        assert hideFab != null;
        hideFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("TAG","SETTINGS THREE CLICKED...");
                if (settings3Box.isChecked()){
                    //DISABLE SETTING
                    settings3Box.setChecked(false);
                    settings_holder.setSavedSettings(FAB_KEY, false);
                } else {
                    //ENABLE SETTING
                    settings3Box.setChecked(true);
                    settings_holder.setSavedSettings(FAB_KEY, true);
                }
            }
        });

        if (settings_holder.getSavedSettings(FAB_KEY)){
            settings3Box.setChecked(true);
        }

        settings3Box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG","SETTINGS THREE CLICKED...");
            }
        });

    }

//    public void dismissView(){
//        //ONLY CALLED IF BUILD IS > LOLLIPOP
//        final View view = findViewById(R.id.settingsRevealView);
//        view.setVisibility(View.VISIBLE);
//        int x = 0;
//        int y = 0;
//        float initRadius = (float) Math.hypot(view.getMeasuredWidth(), view.getHeight());
//        Animator dismiss = ViewAnimationUtils.createCircularReveal(
//                view,
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
//                view.setVisibility(View.GONE);
//                finish();
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//            }
//        });
//        dismiss.start();
////        launchSatellite();
//
//    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.scale_to_corner);
        } else {
            super.onBackPressed();
        }

    }
}
