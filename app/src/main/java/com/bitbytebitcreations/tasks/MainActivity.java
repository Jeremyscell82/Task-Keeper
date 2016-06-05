package com.bitbytebitcreations.tasks;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.bitbytebitcreations.tasks.utilz.ViewPager_Controller;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final private String TAG = "MAIN_ACTIVITY";
    BottomSheetBehavior bottomSheetBehavior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //LOAD UP THE VIEWPAGER
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        String[] taskTitles = getResources().getStringArray(R.array.taskTitles);
        ViewPager_Controller viewPager_controller = new ViewPager_Controller(getSupportFragmentManager(), taskTitles);
        viewPager.setAdapter(viewPager_controller);


        //FLOATING ACTION BUTTON
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        View bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_add_list:
                //LOAD ADD LIST DIALOG
                showDialog();
                break;
            case R.id.action_settings:
                //LOAD SETTINGS ACTIVITY
                ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_top, R.anim.slide_out_bottom);
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent, options.toBundle());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void expandBottomSheet(View v){

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    /*=============
    ADD LIST DIALOG
     =============*/
    public void showDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle(R.string.addlist_dialog_title);
        dialogBuilder.setMessage(R.string.addlist_dialog_hint);
        dialogBuilder.setPositiveButton(R.string.addlist_dialog_create, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                String toastTXT = edt.getText().toString() + " has been created.";
                Toast.makeText(getApplicationContext(), toastTXT, Toast.LENGTH_SHORT).show();
            }
        });
        dialogBuilder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
