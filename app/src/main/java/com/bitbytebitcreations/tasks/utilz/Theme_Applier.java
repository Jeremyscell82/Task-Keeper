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
import android.widget.FrameLayout;
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
                applyGreenTheme(act, bar, isOnMain);
                break;
            case 2:
                //CALL RED THEME
                applyRedTheme(act, bar, isOnMain);
                break;
            case 3:
                //CALL PURPLE THEME
                applyPurpTheme(act, bar, isOnMain);
                break;
            case 4:
                //CALL PINK THEME
                applyPinkTheme(act, bar, isOnMain);
                break;
        }
    }


    //GREEN
    public void applyGreenTheme(Activity activity, Toolbar toolbar, boolean isOnMain){
        if (Build.VERSION.SDK_INT >= 21) { //LOLLIPOP AND ABOVE
            activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.greenPrimary));
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.greenPrimaryDark));
        }
        //DEFAULT CHANGE
        toolbar.setBackgroundColor(activity.getResources().getColor(R.color.greenPrimary));
        FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.detail_container);
        if (frameLayout != null)frameLayout.setBackgroundColor(activity.getResources().getColor(R.color.greenBackground));
        if (isOnMain){ //ADDITIONAL MAIN FRAGMENT VIEWS TO CHANGE + FAB
            FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
            ViewPager viewPager = (ViewPager) activity.findViewById(R.id.viewPager);
            PagerTitleStrip titleStrip = (PagerTitleStrip) activity.findViewById(R.id.pager_title_strip);
            //SET COLORS
            fab.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.greenPrimaryLight)));
            viewPager.setBackgroundColor(activity.getResources().getColor(R.color.greenBackground));
            titleStrip.setBackgroundColor(activity.getResources().getColor(R.color.greenPrimary));
        }
    }

    //RED
    public void applyRedTheme(Activity activity, Toolbar toolbar, boolean isOnMain){
        if (Build.VERSION.SDK_INT >= 21) { //LOLLIPOP AND ABOVE
            activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.redPrimary));
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.redPrimaryDark));
        }
        //DEFAULT CHANGE
        toolbar.setBackgroundColor(activity.getResources().getColor(R.color.redPrimary));
        FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.detail_container);
        if (frameLayout != null)frameLayout.setBackgroundColor(activity.getResources().getColor(R.color.redBackground));
        if (isOnMain){ //ADDITIONAL MAIN FRAGMENT VIEWS TO CHANGE + FAB
            FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
            ViewPager viewPager = (ViewPager) activity.findViewById(R.id.viewPager);
            PagerTitleStrip titleStrip = (PagerTitleStrip) activity.findViewById(R.id.pager_title_strip);
            //SET COLORS
            fab.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.redPrimaryLight)));
            viewPager.setBackgroundColor(activity.getResources().getColor(R.color.redBackground));
            titleStrip.setBackgroundColor(activity.getResources().getColor(R.color.redPrimary));
        }
    }

    //PURPLE
    public void applyPurpTheme(Activity activity, Toolbar toolbar, boolean isOnMain){
        if (Build.VERSION.SDK_INT >= 21) { //LOLLIPOP AND ABOVE
            activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.purpPrimary));
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.purpPrimaryDark));
        }
        //DEFAULT CHANGE
        toolbar.setBackgroundColor(activity.getResources().getColor(R.color.purpPrimary));
        FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.detail_container);
        if (frameLayout != null)frameLayout.setBackgroundColor(activity.getResources().getColor(R.color.purpBackground));
        if (isOnMain){ //ADDITIONAL MAIN FRAGMENT VIEWS TO CHANGE + FAB
            FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
            ViewPager viewPager = (ViewPager) activity.findViewById(R.id.viewPager);
            PagerTitleStrip titleStrip = (PagerTitleStrip) activity.findViewById(R.id.pager_title_strip);
            //SET COLORS
            fab.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.purpPrimaryLight)));
            viewPager.setBackgroundColor(activity.getResources().getColor(R.color.purpBackground));
            titleStrip.setBackgroundColor(activity.getResources().getColor(R.color.purpPrimary));
        }
    }

    //PINK
    public void applyPinkTheme(Activity activity, Toolbar toolbar, boolean isOnMain){
        if (Build.VERSION.SDK_INT >= 21) { //LOLLIPOP AND ABOVE
            activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.pinkPrimary));
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.pinkPrimaryDark));
        }
        //DEFAULT CHANGE
        toolbar.setBackgroundColor(activity.getResources().getColor(R.color.pinkPrimary));
        FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.detail_container);
        if (frameLayout != null)frameLayout.setBackgroundColor(activity.getResources().getColor(R.color.pinkBackground));
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
