package com.bitbytebitcreations.tasks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by JeremysMac on 6/4/16.
 */
public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
        TextView settings3 = (TextView) findViewById(R.id.textView3);
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

        settings3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG","SETTINGS THREE CLICKED...");
                if (settings3Box.isChecked()){
                    settings3Box.setChecked(false);
                } else {
                    settings3Box.setChecked(true);
                }
            }
        });

        settings3Box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG","SETTINGS THREE CLICKED...");
            }
        });

    }
}
