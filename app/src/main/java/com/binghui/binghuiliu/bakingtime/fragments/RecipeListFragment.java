package com.binghui.binghuiliu.bakingtime.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binghui.binghuiliu.bakingtime.R;
import com.binghui.binghuiliu.bakingtime.RecipeDetailActivity;
import com.binghui.binghuiliu.bakingtime.adapters.RecipeAdapter;

import butterknife.BindBool;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by binghuiliu on 25/09/2017.
 */

public class RecipeListFragment extends Fragment implements RecipeAdapter.OnItemClickListener {

    @BindView(R.id.recipe_recycler_view)
    RecyclerView recipeRecyclerView;

    @BindInt(R.integer.recipe_column_count)
    int recipe_column_count;

    RecipeAdapter recipeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_list_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recipeAdapter = new RecipeAdapter(getContext(), this);
        recipeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), recipe_column_count));
        recipeRecyclerView.setAdapter(recipeAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
        startActivity(intent);
    }
}