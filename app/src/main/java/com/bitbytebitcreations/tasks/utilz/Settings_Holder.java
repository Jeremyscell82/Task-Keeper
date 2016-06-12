package com.bitbytebitcreations.tasks.utilz;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.bitbytebitcreations.tasks.R;
import com.bitbytebitcreations.tasks.SettingsActivity;

/**
 * Created by JeremysMac on 6/6/16.
 */
public class Settings_Holder extends AppCompatActivity{


    //SETTING KEYS
    Context context;
    private final static String PREFS_NAME = "TASKS_SETTINGS";
    private final static String FAB = "HIDE_FAB";

    public Settings_Holder(Context context){
        this.context = context;
    }

    public String getFabKey(){
        return FAB;
    }


    public boolean getSavedSettings(String key){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean setting = prefs.getBoolean(FAB, false);
        return setting;
    }


    public void setSavedSettings(String settingType, boolean value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(settingType, value);
        editor.apply();
    }
}
