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
        Log.i("HOLDER", "GETTING KEY FOR FAB");
        return FAB;
    }


    public boolean getSavedSettings(String key){
//        SettingsActivity activity = (SettingsActivity) context;
//        SettingsActivity activity = new SettingsActivity();
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        boolean setting;
//        switch (key){
//            case "FAB":
//                setting = prefs.getBoolean(FAB, false);
//                break;
//            default:
//                setting = false;
//                break;
//        }
//        HIDE_FAB = prefs.getBoolean(FAB, false);
//            boolean values = new boolean[]{HIDE_FAB, false};

        boolean setting = prefs.getBoolean(FAB, false);
        Log.i("SETTINGS_HOLDER", "shared preferences were read: " + key + "-> " + setting);
        return setting;
    }


    public void setSavedSettings(String settingType, boolean value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(settingType, value);
        editor.apply();
//        Toast.makeText(getApplicationContext(), "Your settings have been saved", Toast.LENGTH_SHORT).show();
        Log.i("SETTINGS", "SHARED PREF SAVED");
    }
}
