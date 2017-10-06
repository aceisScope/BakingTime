package com.binghui.binghuiliu.bakingtime;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        ArrayList<Recipe> recipeList = getIntent().getParcelableArrayListExtra(recipe_list_key);
        RecipeDetailFragment detailFragment = (RecipeDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_recipe_fragment);
        detailFragment.setRecipeList(recipeList);
        detailFragment.setCurrentIndex(getIntent().getIntExtra(recipe_index_key, 0));
    }
}
