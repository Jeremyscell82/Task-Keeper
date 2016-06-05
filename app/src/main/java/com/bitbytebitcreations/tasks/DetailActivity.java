package com.bitbytebitcreations.tasks;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by JeremysMac on 6/2/16.
 */
public class DetailActivity extends AppCompatActivity {

    final String TAG = "DETAIL_ACTIVITY";
    FloatingActionButton mFAB;

    boolean EDIT_MODE;
    MenuItem editMenu;
    MenuItem saveMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //SETUP TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //SET UP UI
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        mFAB = (FloatingActionButton) findViewById(R.id.fab_detail);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "TASK SAVED", Toast.LENGTH_SHORT).show();
                //TODO SAVE TASK, REMOVE FROM STACK AND RELOAD MAIN ACTIVITY
                onBackPressed();
            }
        });
        //FAB DELAY




        boolean newTask = getIntent().getBooleanExtra("NEW", false);


        loadTask(newTask);


    }


    public void loadTask(boolean newTask){
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("NEW", newTask);
        fragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.detail_container, fragment)
                .commit();
    }

    /*
    ========MENU========
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
//        editMenu = menu.findItem(R.id.action_edit);
//        saveMenu = menu.findItem(R.id.action_save);
//        saveMenu.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_save:
                onBackPressed();
                break;
            case R.id.action_delete:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setEditMode(boolean editMode){
//        if (editMode){
//            saveMenu.setVisible(true);
//            editMenu.setVisible(false);
//        } else {
//            saveMenu.setVisible(false);
//            editMenu.setVisible(true);
//        }
    }


    @Override
    public void onBackPressed() {
        mFAB.hide();
        super.onBackPressed();
    }
}
