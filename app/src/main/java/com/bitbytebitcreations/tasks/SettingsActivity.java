package com.bitbytebitcreations.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

//    public void updateSettings(){
//        getSharedPreferences(pref_name, MODE_PRIVATE);
//    }
//    public

//    public boolean getSavedSettings(String key){
//        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
////        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
////        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        boolean setting;
//        switch (key){
//            case "FAB":
//                setting = prefs.getBoolean(FAB_KEY, false);
//                Log.i("TAG", "GET FAB VALUE: " + setting);
//                break;
//            default:
//                setting = false;
//                break;
//        }
////        HIDE_FAB = prefs.getBoolean(FAB, false);
////            boolean values = new boolean[]{HIDE_FAB, false};
//        setting = prefs.getBoolean(FAB_KEY, false);
//        Log.i("TAG", "GET FAB VALUE: " + setting);
//        Log.i("SETTINGS_HOLDER", "shared preferences were read: " + key + "-> " + setting);
//        return setting;
//    }

//    public void setSavedSettings(String settingType, boolean value){
//        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
////        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
////        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putBoolean(settingType, value);
//        editor.apply();
////        Toast.makeText(getApplicationContext(), "Your settings have been saved", Toast.LENGTH_SHORT).show();
//        Log.i("SETTINGS", "SHARED PREF SAVED");
//    }

}
