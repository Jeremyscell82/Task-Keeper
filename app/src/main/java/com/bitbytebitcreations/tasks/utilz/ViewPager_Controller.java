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
//        this.controller = cont;
        Log.i(TAG, "HOW MANY TIMES DO YOU RUN???");
    }

//    public ArrayList<String[]> getMasterLIST(){
//
//    }
//    public String[] getmTitles(){
//        controller.openDB(context);
//    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ViewPager_Fragment();
        DB_Controller controller = new DB_Controller();
        controller.openDB(context);
        Log.i("TEST", "CURRENT FRAG TITLE: " +mTitles[position]);
        ArrayList<String[]> masterList = controller.getAllTasks(mTitles[position]);
        controller.closeDB();
        Bundle bundle = new Bundle();
        bundle.putStringArray("TITLE", mTitles);
        for (int i = 0; i < masterList.size(); i++){
            Log.i("TEST", "LOOP THROUGH AND DISPLAY TITLE: " + masterList.get(i)[1]); //SHOULD DISPLAY LIST
        }

        bundle.putSerializable("TASKS", masterList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
//        mTitles = getmTitles();
        if (mTitles != null){
            return mTitles.length;
        } else {
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        return mTitles[position];
    }


    /*
    =====================================================================
    ===============        VIEW PAGER FRAGMENT         ==================
    =====================================================================
     */

    public static class ViewPager_Fragment extends Fragment {

        private final static String TAG = "VIEWPAGER_FRAG";
        private FloatingActionButton mFAB;
        boolean mHide_FAB;
        boolean mFAB_isVisible;
        RecyclerView myRecycler;
        ArrayList<String[]> masterList;
        private String[] mTitle;

        @Nullable
        @Override
        public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.viewpager_frag, container, false);


//            String date = "01/12/16";
//            String dueDate = "02/21/16";
            String task = getString(R.string.description);


            //GET BUNDLE
            Bundle bundle = getArguments();
            if (bundle != null){
                mTitle = bundle.getStringArray("TITLE");
                Log.i(TAG, "TEST - VIEWPAGER HAS LOADED: "+ mTitle);
                masterList = (ArrayList<String[]>) bundle.getSerializable("TASKS"); //FILTERED FOR THIS LIST
//                mTitle = masterList.get(0)[1];
            } else {
                Log.i(TAG, "SOMETHING WENT WRONG");
            }

            mFAB = (FloatingActionButton) getActivity().findViewById(R.id.fab);
//            mFAB.show();
            mFAB_isVisible = true;

            myRecycler = (RecyclerView) view.findViewById(R.id.myRecycler);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            if (masterList.size() > 0){
                Log.i("TEST", "CURRENT RECYCLER LIST: " + masterList.get(0)[1]);
            }

            Recycler_Adapter adapter = new Recycler_Adapter(getActivity(), masterList);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            myRecycler.setLayoutManager(llm);
            myRecycler.setAdapter(adapter);

            myRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (mHide_FAB){
                        if (dy > 0) {
                            // Scrolling up
                            if (mFAB_isVisible){
                                mFAB.hide();
                                mFAB_isVisible = false;
                            }
                        } else {
                            // Scrolling down
                            if (!mFAB_isVisible){
                                mFAB.show();
                                mFAB_isVisible = true;
                            }
                        }
                    }
                }
            });



            //CHECK PREF FOR SETTINGS
//            Settings_Holder settings_holder = new Settings_Holder();
//            boolean settings = settings_holder.getSavedSettings(getActivity(), "FAB");
            //
            hideFabCheck();




            mFAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewPager pager = (ViewPager) getActivity().findViewById(R.id.viewPager);
                    int currFrag = pager.getCurrentItem();
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getContext(), R.anim.slide_in_top, R.anim.fade_out);
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    Log.i(TAG, "LIST NAME: " + mTitle);
                    intent.putExtra("TITLE", mTitle[currFrag]); //GETS LISTNAME
                    intent.putExtra("NEW", true);
                    startActivity(intent, options.toBundle());
                    mFAB.hide();
//                    Log.i(TAG, "TEST - FAB WAS PRESSED ON: " + mTitle);

                    Log.i("TEST", "VIEW PAGER NUMBER: "+mTitle[pager.getCurrentItem()]);
                }
            });

            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
//            mFAB.hide(); //TODO CREATE STATEMENT FOR VIEWPAGER DELAY
//            fabDelay();
            mFAB.show();
            hideFabCheck();

//            Log.i("ONRESUME", "ON RESUME WAS CALLED");
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.i(TAG, "WHATS GOING ON");
        }



        public void hideFabCheck(){
            Log.i(TAG, "HIDE FAB RUNNING");
            Settings_Holder settings_holder = new Settings_Holder(getContext());
            String key = settings_holder.getFabKey();
            mHide_FAB = settings_holder.getSavedSettings(key);
        }

        public void fabDelay(){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFAB.show();
                }
            }, 400);
        }

    } //END OF FRAGMENT
}
