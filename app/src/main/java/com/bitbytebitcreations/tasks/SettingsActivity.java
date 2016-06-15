package com.bitbytebitcreations.tasks;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.CheckBox;
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
    boolean HIDE_FAB = false;
    int currTheme;
    Toolbar toolbar;
    Settings_Holder settings_holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //SET UP THE SHARED PREFERENCE CLASS
        settings_holder = new Settings_Holder(this);

        //GET SAVED SETTINGS
        FAB_KEY = getString(R.string.key_fab);
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

//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().setNavigationBarColor(getResources().getColor(R.color.yellow));
//            getWindow().setStatusBarColor(getResources().getColor(R.color.yellow));
//        }
//        Theme_Applier theme = new Theme_Applier();
//        theme.applyPinkTheme(this, toolbar, false); //FALSE -- IS ON MAIN ACTIVITY


        //SETUP TOOLBAR
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);



        //SET UP UI
        TextView toolbarName = (TextView) findViewById(R.id.toolbarName);
        TextView appTheme = (TextView) findViewById(R.id.appTheme);
        TextView hideFab = (TextView) findViewById(R.id.hideFab);
        TextView taskOrder = (TextView) findViewById(R.id.taskOrder);
        TextView defaultTime = (TextView) findViewById(R.id.defaultTime);
        TextView pastDue = (TextView) findViewById(R.id.pastDue);
        TextView listOrder = (TextView) findViewById(R.id.listOrder);
        TextView listName = (TextView) findViewById(R.id.listName);
        final TextView dltList = (TextView) findViewById(R.id.dltList);
        TextView dltALL = (TextView) findViewById(R.id.dltALL);
        TextView resetSettings = (TextView) findViewById(R.id.resetSettings);
        final CheckBox fabChkBox = (CheckBox) findViewById(R.id.checkBox);

        //SET UP LISTENERS
        assert toolbarName != null;
        toolbarName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("TOOLBAR NAME");
            }
        });

        assert appTheme != null;
        appTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG","APP THEME CLICKED...");
//                showThemeDialog();
                showColorPicker();
            }
        });
        assert hideFab != null;
        hideFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFab(fabChkBox);
            }
        });
        assert fabChkBox != null;
        fabChkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFab(fabChkBox);
            }
        });
        assert taskOrder != null;
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
            }
        });
        assert pastDue != null;
        pastDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("PAST DUE");
            }
        });
        assert listOrder != null;
        listOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("LIST ORDER");
            }
        });
        assert listName != null;
        listName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("LIST NAME");
            }
        });
        assert dltList != null;
        dltList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("DELETE LIST");
            }
        });
        assert dltALL != null;
        dltALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("DELETE ALL");
            }
        });
        assert resetSettings != null;
        resetSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("RESET SETTINGS");
            }
        });

        //SET SAVED DEFAULTS FOR FAB HIDE
        if (settings_holder.getFABSettings()){
            fabChkBox.setChecked(true);
        }

    }

    private void setFab(CheckBox fabChkBox){
        Log.i("TAG","SETTINGS THREE CLICKED...");
        if (fabChkBox.isChecked()){
            //DISABLE SETTING
            fabChkBox.setChecked(false);
            settings_holder.setBOOLSettings(FAB_KEY, false);
        } else {
            //ENABLE SETTING
            fabChkBox.setChecked(true);
            settings_holder.setBOOLSettings(FAB_KEY, true);
        }
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /* ===========SET THEME=============*/
    private void setTheme(Toolbar toolbar){
        Settings_Holder settings_holder = new Settings_Holder(this);
        currTheme = settings_holder.getTHEMESettings();
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
    public void showColorPicker(){
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
            //END AND RESTART THE ACTVIITY
            finish();
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
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
