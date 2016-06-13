package com.bitbytebitcreations.tasks.utilz;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.widget.RelativeLayout;

import com.bitbytebitcreations.tasks.R;

/**
 * Created by JeremysMac on 6/12/16.
 */
public class Theme_Applier extends AppCompatActivity {

    public void themeManager(int theme, Activity act, Toolbar bar, boolean isOnMain){
        switch (theme){
            case 0:
                //DO NOTHING TO APPLY ORIGINAL THEME
                break;
            case 1:
                //CALL GREEN THEME
                break;
            case 2:
                //CALL RED THEME
                break;
            case 3:
                //CALL PURPLE THEME
                break;
            case 4:
                //CALL PINK THEME
                applyPinkTheme(act, bar, isOnMain);
                break;
        }
    }

    public void applyPinkTheme(Activity activity, Toolbar toolbar, boolean isOnMain){
        if (Build.VERSION.SDK_INT >= 21) { //LOLLIPOP AND ABOVE
            activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.pinkPrimary));
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.pinkPrimaryDark));
        }
        //DEFAULT CHANGE
        toolbar.setBackgroundColor(activity.getResources().getColor(R.color.pinkPrimary));
        if (isOnMain){ //ADDITIONAL MAIN FRAGMENT VIEWS TO CHANGE + FAB
            FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
            ViewPager viewPager = (ViewPager) activity.findViewById(R.id.viewPager);
            PagerTitleStrip titleStrip = (PagerTitleStrip) activity.findViewById(R.id.pager_title_strip);
            //SET COLORS
            fab.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.pinkPrimaryLight)));
            viewPager.setBackgroundColor(activity.getResources().getColor(R.color.pinkBackground));
            titleStrip.setBackgroundColor(activity.getResources().getColor(R.color.pinkPrimary));
        }
    }
}
