package com.bitbytebitcreations.tasks.utilz;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitbytebitcreations.tasks.DetailActivity;
import com.bitbytebitcreations.tasks.R;
import com.bitbytebitcreations.tasks.SettingsActivity;

/**
 * Created by JeremysMac on 6/1/16.
 */
public class ViewPager_Controller extends FragmentStatePagerAdapter {

    String[] mTitles;

    public ViewPager_Controller(FragmentManager fm, String[] taskTitles){
        super(fm);
        this.mTitles = taskTitles;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ViewPager_Fragment();

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
        boolean mFAB_isVisible;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.viewpager_frag, container, false);

//            String date = "01/12/16";
//            String dueDate = "02/21/16";
            String task = getString(R.string.description);

            mFAB = (FloatingActionButton) view.findViewById(R.id.fab);
            mFAB.show();
            mFAB_isVisible = true;

            RecyclerView myRecycler = (RecyclerView) view.findViewById(R.id.myRecycler);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            Recycler_Adapter adapter = new Recycler_Adapter(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            myRecycler.setLayoutManager(llm);
            myRecycler.setAdapter(adapter);

            //SET UP LISTENERS
            myRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
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

            });

            mFAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getContext(), R.anim.slide_in_top, R.anim.fade_out);
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("NEW", 1);
                    intent.putExtra("NEW", true);
                    startActivity(intent, options.toBundle());
                }
            });

            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
            mFAB.show();
        }

        @Override
        public void onPause() {
            super.onPause();
            mFAB.hide();
        }
    }
}
