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
 * Created by binghuiliu on 27/09/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context mContext;

    public RecipeAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_cell, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.recipeImage.setImageResource(R.drawable.recipe_image_placeholder);
        holder.recipeTitle.setText("This is a baking recipe. I hope you will enjoy baking.It is not an easy job.");
    }


    @Override
    public int getItemCount() {
        return 5;
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_image)
        ImageView recipeImage;

        @BindView(R.id.recipe_title)
        TextView recipeTitle;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
