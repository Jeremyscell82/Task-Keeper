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

import java.util.ArrayList;

/**
 * Created by JeremysMac on 6/11/16.
 */
public class ViewPager_Frag  extends Fragment{

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


        //GET BUNDLE
        Bundle bundle = getArguments();
        if (bundle != null){
            mTitle = bundle.getStringArray("TITLE");
            masterList = (ArrayList<String[]>) bundle.getSerializable("TASKS"); //FILTERED FOR THIS LIST
        }
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


        hideFabCheck();




        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);
                int currFrag = viewPager.getCurrentItem();
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getContext(), R.anim.slide_in_top, R.anim.fade_out);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("FRAG", currFrag);
                intent.putExtra("TITLE", mTitle[currFrag]); //GETS LISTNAME
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
        mFAB.show();
        hideFabCheck();
//        Log.i("TEST", "ON RESUME RAN");
//        reloadData();
//        myRecycler.getAdapter().notifyDataSetChanged();
//        ViewPager pager = (ViewPager) getActivity().findViewById(R.id.viewPager);
//        pager.getAdapter().notifyDataSetChanged();

    }

//    public void reloadData(){
//        //GET FRAGMENT TITLE
//        ViewPager pager = (ViewPager) getActivity().findViewById(R.id.viewPager);
//        int currFrag = pager.getCurrentItem();
//        //SET UP MASTER LIST
//        masterList.clear();
//        masterList = new ArrayList<>();
//        //CALL DB
//        DB_Controller controller = new DB_Controller();
//        controller.openDB(getActivity());
//        masterList = controller.getAllTasks(mTitle[currFrag]);
//        controller.closeDB();
//    }

    public void hideFabCheck(){
        Settings_Holder settings_holder = new Settings_Holder(getContext());
        String key = settings_holder.getFabKey();
        mHide_FAB = settings_holder.getSavedSettings(key);
    }

    //TODO USE OR DELETE
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