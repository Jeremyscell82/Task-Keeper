package com.bitbytebitcreations.tasks.utilz;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bitbytebitcreations.tasks.DetailActivity;
import com.bitbytebitcreations.tasks.MainActivity;
import com.bitbytebitcreations.tasks.R;

/**
 * Created by JeremysMac on 6/1/16.
 */
public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.card_holder> {

private Context context;

public Recycler_Adapter(Context activity){
        this.context = activity;
        }

@Override
public Recycler_Adapter.card_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.viewpager_card, parent, false);

        return new card_holder(view);
        }

@Override
public void onBindViewHolder(Recycler_Adapter.card_holder holder, int position) {

        }

@Override
public int getItemCount() {
        return 10;
        }

/*================
INNER CARD HOLDER
=================*/
public class card_holder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private ImageView holder_image;


    public card_holder(View view) {
        super(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);

        holder_image = (ImageView) view.findViewById(R.id.priority);

    }


    @Override
    public void onClick(View v) {
        Log.i("CARD_HOLDER", "ON CLICK PRESSED");
        Intent intent = new Intent(context, DetailActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, holder_image, context.getString(R.string.detail_transition));
            context.startActivity(intent, options.toBundle());
        } else {
            context.startActivity(intent);
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
