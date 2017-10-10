package com.binghui.binghuiliu.bakingtime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.binghui.binghuiliu.bakingtime.data.RecipeProvider;
import com.binghui.binghuiliu.bakingtime.fragments.RecipeDetailFragment;
import com.binghui.binghuiliu.bakingtime.model.Recipe;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity {

    @BindString(R.string.recipe_list_key)
    String recipe_list_key;

    @BindString(R.string.recipe_index_key)
    String recipe_index_key;

    ArrayList<Recipe> recipeList;
    int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            recipeList = savedInstanceState.getParcelableArrayList(recipe_list_key);
            currentIndex = savedInstanceState.getInt(recipe_index_key, 0);
        } else {
            recipeList = RecipeProvider.recipes;
            currentIndex = getIntent().getIntExtra(recipe_index_key, 0);
        }
        RecipeDetailFragment detailFragment = (RecipeDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_recipe_fragment);
        detailFragment.setRecipeList(recipeList);
        detailFragment.setCurrentRecipeIndex(currentIndex);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(recipe_list_key, recipeList);
        outState.putInt(recipe_index_key, currentIndex);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
