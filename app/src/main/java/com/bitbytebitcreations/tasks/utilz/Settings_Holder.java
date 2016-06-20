package com.bitbytebitcreations.tasks.utilz;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.bitbytebitcreations.tasks.R;
import com.bitbytebitcreations.tasks.SettingsActivity;

import java.security.PublicKey;

/**
 * Created by JeremysMac on 6/6/16.
 */
public class Settings_Holder extends AppCompatActivity{


    //SETTING KEYS
    Context context;
    private final static String PREFS_NAME = "TASKS_SETTINGS";

    private final static String FAB = "HIDE_FAB";
    private final static String THEME = "THEME";
    private final static String NAME = "NAME";
    private final static String PASTDUE = "PASTDUE";
    private final static String TIME = "TIME";

    public Settings_Holder(Context context){
        this.context = context;
    }

    public String getFabKey(){
        return FAB;
    }
    public String getPastDueKey(){
        return PASTDUE;
    }
    public String getNameKey(){
        return NAME;
    }
    public String getThemeKey(){
        return THEME;
    }
    public String getTimeKey(){
        return TIME;
    }



    /*
    GETTERS
     */
    public boolean getBoolSettings(String KEY){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean fab = prefs.getBoolean(KEY, false);
        return fab;
    }

    public int getINTSettings(String KEY){
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int theme = preferences.getInt(KEY, 0);
        return theme;
    }

    public String getToolbarSettings(){
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String defaultName = context.getResources().getString(R.string.app_name);
        String toolbarName = preferences.getString(NAME, defaultName);
        return toolbarName;
    }






    /*
    SETTERS
     */

    //FOR BOOL SETTINGS ONLY
    public void setBOOLSettings(String settingType, boolean value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(settingType, value);
        editor.apply();
    }
    //FOR INT SETTINGS ONLY
    public void setINTSettings(String settingType, int value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(settingType, value);
        editor.apply();
    }
    //FOR STRING SETTINGS ONLY
    public void setStringSettings(String settingType, String value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(settingType, value);
        editor.apply();
    }

}
