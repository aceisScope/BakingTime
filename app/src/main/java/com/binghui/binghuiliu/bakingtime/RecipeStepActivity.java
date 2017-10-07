package com.binghui.binghuiliu.bakingtime;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.binghui.binghuiliu.bakingtime.fragments.RecipeStepFragment;
import com.binghui.binghuiliu.bakingtime.model.Step;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.ButterKnife;

public class RecipeStepActivity extends AppCompatActivity {

    @BindString(R.string.recipe_step_key)
    String recipe_step_key;

    Step step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        ButterKnife.bind(this);

        step = getIntent().getParcelableExtra(recipe_step_key);
        RecipeStepFragment detailFragment = (RecipeStepFragment) getSupportFragmentManager().findFragmentById(R.id.step_recipe_fragment);
        detailFragment.setStep(step);
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
