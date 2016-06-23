package com.bitbytebitcreations.tasks.utilz;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitbytebitcreations.tasks.DetailActivity;
import com.bitbytebitcreations.tasks.R;
import com.bitbytebitcreations.tasks.ViewPager_Frag;
import com.bitbytebitcreations.tasks.utilz.DB.DB_Controller;

import java.util.ArrayList;

/**
 * Created by JeremysMac on 6/1/16.
 */
public class ViewPager_Controller extends FragmentStatePagerAdapter {

    String TAG = "VIEWPAGER";
    String[] mTitles;
    Context context;

    public ViewPager_Controller(FragmentManager fm, String[] titles, Context activity){
        super(fm);
        this.mTitles = titles;
        this.context = activity;
    }

    @Override
    public Fragment getItem(int position) {
//        Fragment fragment = new ViewPager_Fragment();
        Fragment fragment = new ViewPager_Frag();
        DB_Controller controller = new DB_Controller();
        controller.openDB(context);
        ArrayList<Task_Object> masterList = controller.getAllTasks(mTitles[position]);
        controller.closeDB();
        Bundle bundle = new Bundle();
        bundle.putStringArray("TITLE", mTitles);
        bundle.putSerializable("TASKS", masterList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        if (mTitles != null){
            return mTitles.length;
        } else {
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

}
