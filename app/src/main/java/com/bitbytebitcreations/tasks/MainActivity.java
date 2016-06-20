package com.bitbytebitcreations.tasks;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitbytebitcreations.tasks.utilz.DB.DB_Controller;
import com.bitbytebitcreations.tasks.utilz.Settings_Holder;
import com.bitbytebitcreations.tasks.utilz.Theme_Applier;
import com.bitbytebitcreations.tasks.utilz.ViewPager_Controller;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final private String TAG = "MAIN_ACTIVITY";
    BottomSheetBehavior bottomSheetBehavior;
    DB_Controller controller;
    ViewPager mViewPager;
    String[] mTitleList;
    long ROWID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("");


        controller = new DB_Controller();

        //LOAD UP THE VIEWPAGER
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTitleList = getTitles();
        ViewPager_Controller viewPager_controller = new ViewPager_Controller(getSupportFragmentManager(), mTitleList, this);
        mViewPager.setAdapter(viewPager_controller);
        Log.i(TAG, "MASTERLIST HAS RUN");

        //BOTTOM SHEET
        final View bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        RelativeLayout deletebttn = (RelativeLayout) findViewById(R.id.deleteBttn);
        RelativeLayout sharebttn = (RelativeLayout) findViewById(R.id.shareBttn);
        //DECLARE LISTENERS FOR BOTTOM SHEET
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                if (fab != null && newState == 4)fab.show();
//                Log.i(TAG, "THIS IS THE STATE: " + newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        //BOTTOM SHEET BUTTONS
        assert deletebttn != null;
        deletebttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                deleteTask();
                String[] delete = getResources().getStringArray(R.array.delete_dialog);
                showAlert(delete);
            }
        });
        assert sharebttn != null;
        sharebttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });


        //CHECK IF INNER CALL
        boolean innerCall = getIntent().getBooleanExtra("INNER", false);
        if (innerCall){
            int frag = getIntent().getIntExtra("FRAG", 0);
            mViewPager.setCurrentItem(frag);
        }

        //IF NO LISTS..SHOW USER THE DIALOG
        if (mTitleList == null)showDialog();

        /* GET SAVED SETTINGS */
        //SET THEME
        setTheme(toolbar);
        //SET TOOLBAR TITLE
        setToolbar(toolbar);

//        Theme_Applier theme = new Theme_Applier();
//        theme.applyPinkTheme(this, toolbar, true); //TRUE FOR IS ON SETTINGS

    }

    public int getCurrentFragNum(){
        return mViewPager.getCurrentItem();
    }


    /*
    ========MENU========
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_add_list:
                //LOAD ADD LIST DIALOG
                showDialog();
                break;
            case R.id.action_settings:
                //LOAD SETTINGS ACTIVITY
//                revealView();
                startSettings();
                break;
        }
        return super.onOptionsItemSelected(item);
    }/*END OF MENU*/

    /*============REFRESH ACTIVITY==============*/
    public void refreshList(){
        final Intent intent = new Intent(this, MainActivity.class);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }, 200);
    }

    /* ===========EXPAND BOTTOM SHEET=============*/
    public void expandBottomSheet(String rowID){
        this.ROWID = Long.parseLong(rowID);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                fab.show();
            }
        }, 10000);
    }

    /* ===========SET THEME=============*/
    private void setTheme(Toolbar toolbar){
        Settings_Holder settings_holder = new Settings_Holder(this);
        String themeKey = settings_holder.getThemeKey();
        int theme = settings_holder.getINTSettings(themeKey);
        //NOW APPLY THEME
        Theme_Applier applyTheme = new Theme_Applier();
        applyTheme.themeManager(theme, this, toolbar, true); //THEME ACTIVITY TOOLBAR IS-ON-MAIN
    }

    /* ===========SET THEME=============*/
    private void setToolbar(Toolbar toolbar){
        Settings_Holder settings_holder = new Settings_Holder(this);
        String toolbarTitle = settings_holder.getToolbarSettings();
        toolbar.setTitle(toolbarTitle);
    }

    /*=============ADD LIST DIALOG=============*/
    private void showDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.addlist_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText listName = (EditText) dialogView.findViewById(R.id.titleName);

        dialogBuilder.setTitle(R.string.addlist_dialog_title);
        dialogBuilder.setMessage(R.string.addlist_dialog_hint);
        dialogBuilder.setPositiveButton(R.string.addlist_dialog_create, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                String title = listName.getText().toString();
                if (!title.isEmpty() && !title.startsWith(" ")){
                    saveTitle(title);
                    dialog.dismiss();
                } else {
                    showToast("What are you really trying to do?");
                    dialog.dismiss();
                }


            }
        });
        dialogBuilder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();

    }
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /*
    DB CALLS
     */
    public void saveTitle(String title){
        controller.openDB(this);
        controller.addTitle(title);
        controller.closeDB();

//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        int fragNum;
        if (mTitleList != null){
            fragNum = mTitleList.length;
        } else {
            fragNum = 0;
        }
        Log.i("TEST", "FRAG NUM IS: --> "+fragNum);
        Toast.makeText(this, title+" has been created.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("NEW", true);
        intent.putExtra("FRAG", fragNum);
        startActivity(intent);
    }

    public String[] getTitles(){
        controller.openDB(this);
        ArrayList<String[]> masterList = controller.getAllTitles();
        controller.closeDB();
        if (masterList != null){
            List<String> titleList = new ArrayList<String>();
            for (int i = 0; i < masterList.size(); i++){
                titleList.add(masterList.get(i)[1]);
                Log.i(TAG, "RUNNING...");
            }
            if (titleList.size() != 0){
                String[] titles = new String[titleList.size()];
                titles = titleList.toArray(titles);
                return titles;
            }
        }

        Log.i(TAG, "NO TITLES SAVED...");
        return null;
    }

    public void deleteTask(){
        controller.openDB(this);
        controller.deleteTask(ROWID);
        controller.closeDB();
        refreshList();
    }
//    public ArrayList<String[]> getTasks(){
//        ArrayList<String[]> list = new ArrayList<>();
//        controller.openDB(this);
//        list = controller.getAllTasks("test");
//        controller.closeDB();
//        return list;
//    }


    /*
    REVEAL VIEW CODE
     */
    public void revealView(){
        final View view = findViewById(R.id.mainRevealView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //LOLLIPOP ANIMATIONS
            int cx = view.getMeasuredWidth();
            int cy = 0;
            float finalRadius = (float) Math.hypot(cx, view.getHeight());
            final Animator reveal = ViewAnimationUtils.createCircularReveal(
                    view,
                    cx,
                    cy,
                    0,
                    finalRadius
            );
            reveal.setDuration(200);
            view.setVisibility(View.VISIBLE);
            reveal.start();
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    launchSatellite();
//                }
//            }, 200);
            reveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
//                    dismissView();
//                    closeActivity();
                    startSettings();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.setVisibility(View.GONE);
                        }
                    }, 800);

                }
            });
        } else {
            //NON LOLLIPOP ANIMATIONS
            Log.i(TAG, "NOT RUNNING LOLLIPOP ANIMATIONS");
//            closeActivity();
            startSettings();
        }
    }



    /*
    ====================== SETTINGS ======================
     */
    public void startSettings(){
        ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.scale_in_corner, R.anim.fade_out);
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent, options.toBundle());
    }


    /* ================== ALERT DIALOG ================== */
    private void showAlert(String[] items){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(items[0])
                .setMessage(items[1])
                .setNegativeButton(items[2], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(items[3], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteTask();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
