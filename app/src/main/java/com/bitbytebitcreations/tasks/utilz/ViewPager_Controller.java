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
        ArrayList<String[]> masterList = controller.getAllTasks(mTitles[position]);
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


    /*
    =====================================================================
    ===============        VIEW PAGER FRAGMENT   NO LONGER USED...       ==================
    =====================================================================
     */

//    public static class ViewPager_Fragment extends Fragment {
//
//        private final static String TAG = "VIEWPAGER_FRAG";
//        private FloatingActionButton mFAB;
//        boolean mHide_FAB;
//        boolean mFAB_isVisible;
//        RecyclerView myRecycler;
//        ArrayList<String[]> masterList;
//        private String[] mTitle;
//
//        @Nullable
//        @Override
//        public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            View view = inflater.inflate(R.layout.viewpager_frag, container, false);
//
//
//            //GET BUNDLE
//            Bundle bundle = getArguments();
//            if (bundle != null){
//                mTitle = bundle.getStringArray("TITLE");
//                masterList = (ArrayList<String[]>) bundle.getSerializable("TASKS"); //FILTERED FOR THIS LIST
//            }
//
//            mFAB = (FloatingActionButton) getActivity().findViewById(R.id.fab);
//            mFAB_isVisible = true;
//
//            myRecycler = (RecyclerView) view.findViewById(R.id.myRecycler);
//            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
//            Recycler_Adapter adapter = new Recycler_Adapter(getActivity(), masterList);
//            llm.setOrientation(LinearLayoutManager.VERTICAL);
//            myRecycler.setLayoutManager(llm);
//            myRecycler.setAdapter(adapter);
//
//            myRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//                    if (mHide_FAB){
//                        if (dy > 0) {
//                            // Scrolling up
//                            if (mFAB_isVisible){
//                                mFAB.hide();
//                                mFAB_isVisible = false;
//                            }
//                        } else {
//                            // Scrolling down
//                            if (!mFAB_isVisible){
//                                mFAB.show();
//                                mFAB_isVisible = true;
//                            }
//                        }
//                    }
//                }
//            });
//
//
//            hideFabCheck();
//
//
//
//
//            mFAB.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ViewPager pager = (ViewPager) getActivity().findViewById(R.id.viewPager);
//                    int currFrag = pager.getCurrentItem();
//                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getContext(), R.anim.slide_in_top, R.anim.fade_out);
//                    Intent intent = new Intent(getActivity(), DetailActivity.class);
//                    intent.putExtra("TITLE", mTitle[currFrag]); //GETS LISTNAME
//                    intent.putExtra("NEW", true);
//                    startActivity(intent, options.toBundle());
//                    mFAB.hide();
//                }
//            });
//
//            return view;
//        }
//
//        @Override
//        public void onResume() {
//            super.onResume();
//            mFAB.show();
//            hideFabCheck();
//            Log.i("TEST", "ON RESUME RAN");
//            ViewPager pager = (ViewPager) getActivity().findViewById(R.id.viewPager);
//            pager.getAdapter().notifyDataSetChanged();
//        }
//
//        public void hideFabCheck(){
//            Settings_Holder settings_holder = new Settings_Holder(getContext());
//            String key = settings_holder.getFabKey();
//            mHide_FAB = settings_holder.getSavedSettings(key);
//        }
//
//        //TODO USE OR DELETE
//        public void fabDelay(){
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mFAB.show();
//                }
//            }, 400);
//        }
//
//    } //END OF FRAGMENT
}
