package com.bitbytebitcreations.tasks.utilz;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.support.design.widget.BottomSheetBehavior;
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

import java.util.ArrayList;
import java.util.List;

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
        Log.i("TEST", "RECYCLER CARD IS LOADING FROM LIST: " + masterList.get(position)[1]);
        //SET DATA FOR VIEW HOLDER
        holder.masterList = masterList.get(position);
        holder.holder_dob.setText(masterList.get(position)[2]);
        holder.holder_due.setText(masterList.get(position)[3]);
        Log.i("TEST", "PRIORITY LIST SWITCH: " + masterList.get(position)[4]);
        switch (Integer.valueOf(masterList.get(position)[4])){
            case 0:
                holder.holder_priority.setImageResource(R.color.neutral);
                Log.i("TEST", "PRIORITY LIST SWITCH: " + masterList.get(position)[4]);
                break;
            case 1:
                holder.holder_priority.setImageResource(R.color.yellow);
                Log.i("TEST", "PRIORITY LIST SWITCH: " + masterList.get(position)[4]);
                break;
            case 2:
                holder.holder_priority.setImageResource(R.color.orange);
                Log.i("TEST", "PRIORITY LIST SWITCH: " + masterList.get(position)[4]);
                break;
            case 3:
                holder.holder_priority.setImageResource(R.color.red);
                Log.i("TEST", "PRIORITY LIST SWITCH: " + masterList.get(position)[4]);
                break;
        }
        holder.holder_task.setText(masterList.get(position)[5]);
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
            Log.i("CARD_HOLDER", "ON CLICK PRESSED");
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("TITLE", masterList[1]);
            intent.putExtra("TASK", masterList);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//                String transitionName = context.getString(R.string.detail_transition);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, holder_card, context.getString(R.string.detail_transition));
                context.startActivity(intent, options.toBundle());
            } else {
                context.startActivity(intent);
            }

        }

        public int checkPriority(ColorDrawable drawable){
            switch (drawable.getColor()){
                case R.color.neutral:
                    return 0;
                case R.color.yellow:
                    return 1;
                case R.color.orange:
                    return 2;
                case R.color.red:
                    return 3;
                default:
                    return 0;
            }
        }



        @Override
        public boolean onLongClick(View v) {
            Log.i("CARD_HOLDER", "ON LONG CLICK PRESSED");
            MainActivity mainActivity = (MainActivity) context;
            mainActivity.expandBottomSheet(v);
            return true;
        }
    }
}
