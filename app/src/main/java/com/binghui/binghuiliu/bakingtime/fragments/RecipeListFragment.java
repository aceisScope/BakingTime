package com.binghui.binghuiliu.bakingtime.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binghui.binghuiliu.bakingtime.R;
import com.binghui.binghuiliu.bakingtime.RecipeDetailActivity;
import com.binghui.binghuiliu.bakingtime.adapters.RecipeAdapter;
import com.binghui.binghuiliu.bakingtime.data.RecipeService;
import com.binghui.binghuiliu.bakingtime.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindBool;
import butterknife.BindInt;
import butterknife.BindString;
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

    @BindString(R.string.recipe_list_key)
    String recipe_list_key;

    @BindString(R.string.recipe_index_key)
    String recipe_index_key;

    RecipeAdapter recipeAdapter;
    RecipeService recipeService;

    ArrayList<Recipe> recipeList;

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

        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList("recipe_list") != null) {
            recipeList = savedInstanceState.getParcelableArrayList("recipe_list");
            recipeAdapter.setRecipes(recipeList);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        recipeService = new RecipeService(getContext());
        recipeService.parseRecipeJsonFile();

        if (recipeList == null) {
            recipeList = recipeService.getRecipes();
            recipeAdapter.setRecipes(recipeList);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("recipe_list", recipeList);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
        intent.putParcelableArrayListExtra(recipe_list_key, recipeList);
        intent.putExtra(recipe_index_key, position);
        startActivity(intent);
    }
}
