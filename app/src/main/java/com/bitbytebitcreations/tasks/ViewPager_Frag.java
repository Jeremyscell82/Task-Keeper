package com.bitbytebitcreations.tasks;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bitbytebitcreations.tasks.utilz.Recycler_Adapter;
import com.bitbytebitcreations.tasks.utilz.Settings_Holder;
import com.bitbytebitcreations.tasks.utilz.Task_Object;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by JeremysMac on 6/11/16.
 */
public class ViewPager_Frag  extends Fragment{

    private final static String TAG = "VIEWPAGER_FRAG";
    private FloatingActionButton mFAB;
    boolean mHide_FAB;
    boolean mFAB_isVisible;
    RecyclerView myRecycler;
    ArrayList<Task_Object> masterList;
    private ArrayList<String[]> mTitle;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_frag, container, false);


        //GET BUNDLE
        Bundle bundle = getArguments();
        if (bundle != null){
            mTitle = (ArrayList<String[]>) bundle.getSerializable("TITLE"); //CONTAINS ROWID AND TITLE
            //GET ORDER STYLE FROM SHARED PREFERENCES
            Settings_Holder settings_holder = new Settings_Holder(getActivity());

            masterList = (ArrayList<Task_Object>) bundle.getSerializable("TASKS"); //FILTERED FOR THIS LIST
            
        }
        //GET SAVED SETTINGS
        hideFabCheck();

        mFAB = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFAB_isVisible = true;
        myRecycler = (RecyclerView) view.findViewById(R.id.myRecycler);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
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

        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);
                int currFrag = viewPager.getCurrentItem();
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getContext(), R.anim.slide_in_top, R.anim.fade_out);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("FRAG", currFrag);
                intent.putExtra("TITLE", mTitle.get(currFrag)); //GETS LISTNAME
                intent.putExtra("NEW", true);
                startActivity(intent, options.toBundle());
                mFAB.hide();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        hideFabCheck();
    }

    public void hideFabCheck(){
        Settings_Holder settings_holder = new Settings_Holder(getContext());
        String fabKey = settings_holder.getFabKey();
        mHide_FAB = settings_holder.getBoolSettings(fabKey);
    }

    //TODO USE OR DELETE
    public void fabDelay(){
        Log.i("TEST", "FAB DELAY RUNNING");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFAB.show();
            }
        }, 1000);
    }


} //END OF FRAGMENT