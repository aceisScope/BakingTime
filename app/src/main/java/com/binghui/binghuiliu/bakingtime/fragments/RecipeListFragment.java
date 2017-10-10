package com.binghui.binghuiliu.bakingtime.fragments;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binghui.binghuiliu.bakingtime.R;
import com.binghui.binghuiliu.bakingtime.RecipeDetailActivity;
import com.binghui.binghuiliu.bakingtime.adapters.RecipeAdapter;
import com.binghui.binghuiliu.bakingtime.data.RecipeProvider;
import com.binghui.binghuiliu.bakingtime.model.Recipe;
import com.binghui.binghuiliu.bakingtime.widget.RecipeAppWidgetProvider;

import java.util.ArrayList;

import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by binghuiliu on 25/09/2017.
 */

public class RecipeListFragment extends Fragment implements RecipeAdapter.OnItemClickListener {

    @BindView(R.id.recipe_recycler_view)
    RecyclerView recipeRecyclerView;

    @BindInt(R.integer.recipe_column_count)
    int recipe_column_count;

    @BindString(R.string.recipe_index_key)
    String recipe_index_key;

    RecipeAdapter recipeAdapter;
    RecipeProvider recipeProvider;

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
        if (recipeList == null) {
            recipeProvider = new RecipeProvider(getContext());
            recipeProvider.fetchRecipeList(new Callback<ArrayList<Recipe>>() {
                @Override
                public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                    RecipeProvider.recipes = response.body();

                    recipeList = response.body();
                    recipeAdapter.setRecipes(recipeList);
                }

                @Override
                public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("recipe_list", recipeList);
    }

    @Override
    public void onItemClick(int position) {
        Intent widgetIntent = new Intent(getActivity(), RecipeAppWidgetProvider.class);
        int ids[] = AppWidgetManager.getInstance(getActivity()).getAppWidgetIds(new ComponentName(getActivity(), RecipeAppWidgetProvider.class));
        widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        widgetIntent.putExtra(AppWidgetManager.EXTRA_CUSTOM_INFO, recipeList.get(position));
        getContext().sendBroadcast(widgetIntent);

        Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
        intent.putExtra(recipe_index_key, position);
        startActivity(intent);
    }
}
