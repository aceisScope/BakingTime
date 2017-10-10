package com.binghui.binghuiliu.bakingtime;

import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.binghui.binghuiliu.bakingtime.data.RecipeProvider;
import com.binghui.binghuiliu.bakingtime.fragments.RecipeListFragment;
import com.binghui.binghuiliu.bakingtime.model.Recipe;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends AppCompatActivity {

    @BindString(R.string.recipe_list_key)
    String recipe_list_key;

    CountingIdlingResource espressoTestIdlingResource = new CountingIdlingResource("Network_Call");

    RecipeProvider recipeProvider;
    ArrayList<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

        espressoTestIdlingResource.increment();

        if (savedInstanceState != null) {
            recipeList = savedInstanceState.getParcelableArrayList(recipe_list_key);
            RecipeListFragment listFragment = (RecipeListFragment) getSupportFragmentManager().findFragmentById(R.id.list_recipe_fragment);
            listFragment.setRecipeList(recipeList);
        }

        if (recipeList == null) {
            recipeProvider = new RecipeProvider(this);
            recipeProvider.fetchRecipeList(new Callback<ArrayList<Recipe>>() {
                @Override
                public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                    RecipeProvider.recipes = response.body();

                    recipeList = response.body();
                    RecipeListFragment listFragment = (RecipeListFragment) getSupportFragmentManager().findFragmentById(R.id.list_recipe_fragment);
                    listFragment.setRecipeList(recipeList);

                    espressoTestIdlingResource.decrement();
                }

                @Override
                public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                    t.printStackTrace();

                    espressoTestIdlingResource.decrement();
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(recipe_list_key, recipeList);
    }

    @VisibleForTesting
    public CountingIdlingResource getEspressoIdlingResourceForMainActivity() {
        return espressoTestIdlingResource;
    }
}
