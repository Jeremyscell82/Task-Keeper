package com.bitbytebitcreations.tasks;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;
import com.bitbytebitcreations.tasks.utilz.Settings_Holder;
import com.bitbytebitcreations.tasks.utilz.Theme_Applier;

/**
 * Created by JeremysMac on 6/4/16.
 */
public class SettingsActivity extends AppCompatActivity {

    private final String PREFS_NAME = "SETTINGS";
    String FAB_KEY;
    String PASTDUE_KEY;
//    boolean HIDE_FAB = false;
    int currTheme;
    Toolbar toolbar;
    Settings_Holder settings_holder;
    TextView dueTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //SET UP THE SHARED PREFERENCE CLASS
        settings_holder = new Settings_Holder(this);

        //GET SAVED SETTINGS
        FAB_KEY = settings_holder.getFabKey();
        PASTDUE_KEY = settings_holder.getPastDueKey();
        Log.i("TEST_FAB", "FAB KEY IS: " + FAB_KEY);

        toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        setTheme(toolbar);
        getSupportActionBar().setTitle(R.string.settings_title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //SET UP UI
        TextView toolbarName = (TextView) findViewById(R.id.toolbarName);
        TextView appTheme = (TextView) findViewById(R.id.appTheme);
        TextView hideFab = (TextView) findViewById(R.id.hideFab);
        TextView taskOrder = (TextView) findViewById(R.id.taskOrder);
        TextView defaultTime = (TextView) findViewById(R.id.defaultTime);
        dueTime = (TextView) findViewById(R.id.dueTime);
        TextView pastDue = (TextView) findViewById(R.id.pastDue);
        TextView listOrder = (TextView) findViewById(R.id.listOrder);
        TextView listName = (TextView) findViewById(R.id.listName);
        final TextView dltList = (TextView) findViewById(R.id.dltList);
        TextView dltALL = (TextView) findViewById(R.id.dltALL);
        TextView resetSettings = (TextView) findViewById(R.id.resetSettings);
        final Switch hideFabSwitch = (Switch) findViewById(R.id.fabCheckBox);
        final Switch pastDueSwitch = (Switch) findViewById(R.id.pastDueChkBox);

        //SET UP LISTENERS
        assert toolbarName != null;
        toolbarName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNameDialog();
            }
        });

        assert appTheme != null;
        appTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showThemePicker();
            }
        });
        assert hideFab != null;
        hideFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hideFabSwitch.isChecked()){
                    //DISABLE SETTING
                    hideFabSwitch.setChecked(false);
                    settings_holder.setBOOLSettings(FAB_KEY, false);
                } else {
                    //ENABLE SETTING
                    hideFabSwitch.setChecked(true);
                    settings_holder.setBOOLSettings(FAB_KEY, true);
                }
            }
        });
        assert hideFabSwitch != null;
        hideFabSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings_holder.setBOOLSettings(FAB_KEY, isChecked);
            }
        });
        assert taskOrder != null;
        taskOrder.setTextColor(Color.GRAY);
        taskOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("TASK ORDER");
            }
        });
        assert defaultTime != null;
        defaultTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("DEFAULT TIME");
                showTimeDialog();
            }
        });
        assert pastDue != null;
        pastDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pastDueSwitch.isChecked()){
                    //DISABLE SETTING
                    pastDueSwitch.setChecked(false);
                    settings_holder.setBOOLSettings(PASTDUE_KEY, false);
                } else {
                    //ENABLE SETTING
                    pastDueSwitch.setChecked(true);
                    settings_holder.setBOOLSettings(PASTDUE_KEY, true);
                }
            }
        });
        assert pastDueSwitch != null;
        pastDueSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings_holder.setBOOLSettings(PASTDUE_KEY, isChecked);
            }
        });

        assert listOrder != null;
        listOrder.setTextColor(Color.GRAY);
        listOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("LIST ORDER");
            }
        });
        assert listName != null;
        listName.setTextColor(Color.GRAY);
        listName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("LIST NAME");
            }
        });
        assert dltList != null;
        dltList.setTextColor(Color.GRAY);
        dltList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("DELETE LIST");
            }
        });
        assert dltALL != null;
        dltALL.setTextColor(Color.GRAY);
        dltALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("DELETE ALL");
            }
        });
        assert resetSettings != null;
        resetSettings.setTextColor(Color.GRAY);
        resetSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("RESET SETTINGS");
            }
        });

        /* ============SET SWITCH FROM SAVED SETTINGS ==================*/
        String fabKey = settings_holder.getFabKey();
        if (settings_holder.getBoolSettings(fabKey)){
            hideFabSwitch.setChecked(true);
        }
        String pastDueKey = settings_holder.getPastDueKey();
        if (settings_holder.getBoolSettings(pastDueKey)){
            pastDueSwitch.setChecked(true);
        }
        //GET SAVED PREF FOR DEFAULT DUE DATE TIME PERIOD
        String timeKey = settings_holder.getTimeKey();
        int savedTime = settings_holder.getINTSettings(timeKey);
        if (savedTime >= 0){
            String[] defaultTimes = getResources().getStringArray(R.array.defaultTimes);
            dueTime.setText(defaultTimes[savedTime]);
        }


    }


    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /* ===========SET THEME=============*/
    private void setTheme(Toolbar toolbar){
        String themeKey = settings_holder.getThemeKey();
        currTheme = settings_holder.getINTSettings(themeKey);
        //NOW APPLY THEME
        Theme_Applier applyTheme = new Theme_Applier();
        applyTheme.themeManager(currTheme, this, toolbar, false); //THEME ACTIVITY TOOLBAR IS-ON-MAIN
    }

    /*
    ========MENU========
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_close, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_close:
                //LOAD ADD LIST DIALOG
//                onBackPressed();
                finish();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.scale_to_corner);
                break;
        }
        return super.onOptionsItemSelected(item);
    }/*END OF MENU*/


    public void showThemeDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.theme_dialog, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle(R.string.addlist_dialog_title);
        dialogBuilder.setMessage(R.string.addlist_dialog_hint);
        dialogBuilder.setPositiveButton(R.string.addlist_dialog_create, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
            }
        });
        dialogBuilder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    /*================================
    ============APP THEME============
    ================================*/
    public void showThemePicker(){
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
        final int[] colors = {
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.greenPrimary),
                getResources().getColor(R.color.redPrimary),
                getResources().getColor(R.color.purpPrimary),
                getResources().getColor(R.color.pinkPrimary),
                getResources().getColor(R.color.colorBlackish)
        };
        int selectedColor = colors[currTheme];
        Log.i("TEST_COLOR","SELECTED: " + selectedColor);
        int numOfColumns = 3;
        colorPickerDialog.initialize(R.string.theme_dialog_message, colors, selectedColor, numOfColumns, colors.length);
        colorPickerDialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                Log.i("TEST_THEME", "THIS IS THE COLOR: " + color);
                //CONVERT TO INT
                switch (color){
                    case -12627531:
                        Log.i("TEST", "BLUE");
                        applyTheme(0);
                        break;
                    case -14064874:
                        Log.i("TEST", "GREEN");
                        applyTheme(1);
                        break;
                    case -3928819:
                        Log.i("TEST", "RED");
                        applyTheme(2);
                        break;
                    case -10021284:
                        Log.i("TEST", "PURP");
                        applyTheme(3);
                        break;
                    case -4965808:
                        Log.i("TEST", "PINK");
                        applyTheme(4);
                        break;
                    case R.color.colorBlackish:
                        break;
                }
            }
        });
        colorPickerDialog.show(getFragmentManager(), "THEME");
    }

    //APPLY NEW THEME
    public void applyTheme(int theme){
        if (currTheme != theme){
//            Settings_Holder settings_holder = new Settings_Holder(this);
            settings_holder.setINTSettings("THEME", theme);
            masterRefresh();
        }
    }

    /*================================
    ============APP NAME============
    ================================*/
    public void showNameDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.addlist_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText listName = (EditText) dialogView.findViewById(R.id.titleName);

        dialogBuilder.setTitle(R.string.changeName_dialog_title);
        dialogBuilder.setMessage(R.string.changeName_dialog_hint);
        dialogBuilder.setNeutralButton(R.string.changeName_dialog_default, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String defaultName = getString(R.string.app_name);
                applyName(defaultName);
            }
        });
        dialogBuilder.setPositiveButton(R.string.changeName_dialog_create, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                String title = listName.getText().toString();
                if (!title.isEmpty() && !title.startsWith(" ")){
                    applyName(title);
                    dialog.dismiss();
                } else {
                    String toast = getString(R.string.dialog_error);
                    showToast(toast);
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
    public void applyName(String name){
        String nameKey = settings_holder.getNameKey();
        settings_holder.setStringSettings(nameKey, name);
        masterRefresh();
    }

    /*================================
    ============DEFAULT TIME============
    ================================*/
    public void showTimeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] defaultTimes = getResources().getStringArray(R.array.defaultTimes);
        builder.setTitle(R.string.dueTimeTitle)
                .setItems(R.array.defaultTimes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        dueTime.setText(defaultTimes[which]);
                        //SAVE TO SAVED SETTINGS
                        applyTime(which);
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void applyTime(int time){
        String timeKey = settings_holder.getTimeKey();
        settings_holder.setINTSettings(timeKey, time);
        masterRefresh();
    }


    /*
    ==========MASTER REFRESH==============
     */
    private void masterRefresh(){
        //END AND RESTART THE ACTVIITY
        finish();
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


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
