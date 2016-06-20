package com.bitbytebitcreations.tasks.utilz;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbytebitcreations.tasks.DetailActivity;
import com.bitbytebitcreations.tasks.MainActivity;
import com.bitbytebitcreations.tasks.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by JeremysMac on 6/1/16.
 */
public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.card_holder> {

    private final String TAG = "RECYCLER";
    private Context context;
    private ArrayList<String[]> masterList;

    public Recycler_Adapter(Context activity, ArrayList<String[]> list){
        this.context = activity;
        this.masterList = list;
    }


    @Override
    public Recycler_Adapter.card_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.viewpager_card, parent, false);

        return new card_holder(view);
    }

    @Override
    public void onBindViewHolder(Recycler_Adapter.card_holder holder, int position) {
        //SET DATA FOR VIEW HOLDER
        holder.masterList = masterList.get(position);
        holder.holder_dob.setText(masterList.get(position)[2]); //SET DOB
        holder.holder_due.setText(masterList.get(position)[3]); //SET DUE
        Settings_Holder settings_holder = new Settings_Holder(context);
        String pastDueKey = settings_holder.getPastDueKey();
        if (pastDueChecker(masterList.get(position)[3]) && settings_holder.getBoolSettings(pastDueKey)){
            holder.holder_card.setCardBackgroundColor(Color.RED);
        }
        holder.holder_priority.setImageResource(priorityFilter(masterList.get(position)[4])); //PRIORITY
        holder.holder_task.setText(masterList.get(position)[5]); //TASK
    }

    /*
    PRIORITY COLOR FILTER <CONVERTS STRING VALUE TO COLOR>
     */
    public int priorityFilter(String priority){
        int value = Integer.valueOf(priority);
        switch (value){
            case 0:
                return R.color.neutral;
            case 1:
                return R.color.yellow;
            case 2:
                return R.color.orange;
            case 3:
                return R.color.red;
            default:
                return R.color.neutral;
        }
    }

    /*
    PAST DUE CHECKER
     */
    public boolean pastDueChecker(String dueDate){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date strDate = null;
        try {
            strDate = sdf.parse(dueDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        if (System.currentTimeMillis() > strDate.getTime()) {
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return masterList.size();
    }

    /*================
    INNER CARD HOLDER
    =================*/
    public class card_holder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private CardView holder_card;
        private ImageView holder_priority;
        private TextView holder_dob;
        private TextView holder_due;
        private TextView holder_task;
        private String[] masterList;


        public card_holder(View view) {
            super(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            holder_card = (CardView) view.findViewById(R.id.taskCard);
            holder_priority = (ImageView) view.findViewById(R.id.priority);
            holder_dob = (TextView) view.findViewById(R.id.taskDOB);
            holder_due = (TextView) view.findViewById(R.id.taskDUE);
            holder_task = (TextView) view.findViewById(R.id.taskBody);
        }


        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) context;
            int frag = activity.getCurrentFragNum();
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("TITLE", masterList[1]);
            intent.putExtra("TASK", masterList);
            intent.putExtra("FRAG", frag);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, holder_card, context.getString(R.string.detail_transition));
                context.startActivity(intent, options.toBundle());
            } else {
                context.startActivity(intent);
            }

        }



        @Override
        public boolean onLongClick(View v) {
            Log.i("CARD_HOLDER", "ON LONG CLICK PRESSED");
            MainActivity mainActivity = (MainActivity) context;
            mainActivity.expandBottomSheet(masterList[0]);
            return true;
        }
    }
}
