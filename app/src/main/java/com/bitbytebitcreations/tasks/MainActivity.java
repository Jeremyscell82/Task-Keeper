package com.bitbytebitcreations.tasks;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bitbytebitcreations.tasks.utilz.DB.DB_Controller;
import com.bitbytebitcreations.tasks.utilz.ViewPager_Controller;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final private String TAG = "MAIN_ACTIVITY";
    BottomSheetBehavior bottomSheetBehavior;
    DB_Controller controller;
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = new DB_Controller();

        //LOAD UP THE VIEWPAGER
//        ArrayList<String[]> masterList =
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        String[] titleList = getTitles();
//        String[] taskTitles = getResources().getStringArray(R.array.taskTitles);
        ViewPager_Controller viewPager_controller = new ViewPager_Controller(getSupportFragmentManager(), titleList, this);
        mViewPager.setAdapter(viewPager_controller);
//        setViewPager(titleList, false);
        Log.i(TAG, "MASTERLIST HAS RUN");

//        mViewPager = (ViewPager) findViewById(R.id.viewPager);
//        String[] taskTitles = getResources().getStringArray(R.array.taskTitles);
//        ViewPager_Controller viewPager_controller = new ViewPager_Controller(getSupportFragmentManager(), MasterTitlesList, this);
//        mViewPager.setAdapter(viewPager_controller);


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
//                SettingsActivity settingsActivity = new SettingsActivity(this);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_top, R.anim.slide_out_bottom);
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent, options.toBundle());
                break;
        }
        return super.onOptionsItemSelected(item);
    }/*END OF MENU*/

    public void expandBottomSheet(View v){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    public void displayKeyboard(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void setViewPager(String[] titles, boolean update){
        if (!update){
            String[] taskTitles = getResources().getStringArray(R.array.taskTitles);
            ViewPager_Controller viewPager_controller = new ViewPager_Controller(getSupportFragmentManager(), titles, this);
            mViewPager.setAdapter(viewPager_controller);
        } else {
            String[] taskTitles = getResources().getStringArray(R.array.taskTitles);
            ViewPager_Controller viewPager_controller = new ViewPager_Controller(getSupportFragmentManager(), titles, this);
            viewPager_controller.notifyDataSetChanged();
        }

    }

//    public ArrayList<String[]> getDBData(){
//        DB_Controller controller = new DB_Controller();
//        controller.openDB(this);
////        String[]
//        ArrayList<String[]> masterList = controller.getListTasks();
//        return masterList;
//    }

    /*=============
    ADD LIST DIALOG
     =============*/
    public void showDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.addlist_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle(R.string.addlist_dialog_title);
        dialogBuilder.setMessage(R.string.addlist_dialog_hint);
        dialogBuilder.setPositiveButton(R.string.addlist_dialog_create, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                String title = edt.getText().toString();
                if (!title.isEmpty() && !title.startsWith(" ")){
                    saveTitle(title);
                } else {
                    showToast("What are you really trying to do?");
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
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /*
    DB CALLS
     */
    public void saveTitle(String title){
        controller.openDB(this);
        controller.addTitle(title);
        controller.closeDB();
        Log.i(TAG, "SAVE TITLE RAN");
        String[] titleList = getTitles();
//        setViewPager(titleList, true);
//        mViewPager.getAdapter().notifyDataSetChanged();
        Toast.makeText(this, title+" has been created.", Toast.LENGTH_SHORT).show();
//        Intent intent = getIntent();
//        finish();
//        startActivity(intent);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("NEW", true);
        startActivity(intent);

//        Intent intent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putString("TITLE", title);
//        DetailActivity activity = new DetailActivity();
//        intent.putExtra("TITLE", title);
//        intent.setClass(this, DetailActivity.class);
//        startActivity(intent);
    }

    public String[] getTitles(){
        controller.openDB(this);
        ArrayList<String[]> masterList = controller.getAllTitles();
        controller.closeDB();
        List<String> titleList = new ArrayList<String>();
        for (int i = 0; i < masterList.size(); i++){
            titleList.add(masterList.get(i)[1]);
            Log.i(TAG, "RUNNING...");
        }
        if (titleList.size() != 0){
            String[] titles = new String[titleList.size()];
            titles = titleList.toArray(titles);
            return titles;
        }
        Log.i(TAG, "NO TITLES SAVED...");
        return null;
    }
    public ArrayList<String[]> getTasks(){
        ArrayList<String[]> list = new ArrayList<>();
        controller.openDB(this);
        list = controller.getAllTasks("test");
        controller.closeDB();
        return list;
    }
}
