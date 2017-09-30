package com.binghui.binghuiliu.bakingtime.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.binghui.binghuiliu.bakingtime.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by binghuiliu on 30/09/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private Context mContext;
    private StepAdapter.OnItemClickListener mClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public StepAdapter(Context context, StepAdapter.OnItemClickListener onClickListener) {
        this.mContext = context;
        this.mClickListener = onClickListener;
    }

    @Override
    public StepAdapter.StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_cell, parent, false);
        return new StepAdapter.StepViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.stepText.setText("Step 1: Add flour into the bowl");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_step_description)
        TextView stepText;

        @BindView(R.id.icon_step_play)
        ImageView playIcon;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

            playIcon.setImageResource(android.R.drawable.ic_media_play);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(getAdapterPosition());
        }
    }
}
