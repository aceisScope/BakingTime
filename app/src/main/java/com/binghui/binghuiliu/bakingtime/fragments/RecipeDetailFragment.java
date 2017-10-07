package com.binghui.binghuiliu.bakingtime.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binghui.binghuiliu.bakingtime.R;
import com.binghui.binghuiliu.bakingtime.RecipeDetailActivity;
import com.binghui.binghuiliu.bakingtime.RecipeStepActivity;
import com.binghui.binghuiliu.bakingtime.adapters.RecipeAdapter;
import com.binghui.binghuiliu.bakingtime.adapters.StepAdapter;
import com.binghui.binghuiliu.bakingtime.model.Ingredient;
import com.binghui.binghuiliu.bakingtime.model.Recipe;
import com.binghui.binghuiliu.bakingtime.model.Step;

import java.util.ArrayList;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

/**
 * Created by binghuiliu on 28/09/2017.
 */

public class RecipeDetailFragment extends Fragment implements StepAdapter.OnItemClickListener {

    @BindView(R.id.text_ingredient)
    TextView textIngredient;

    @BindView(R.id.step_recycler_view)
    RecyclerView stepRecyclerView;

    @BindBool(R.bool.is_pad)
    boolean is_pad;

    ArrayList<Recipe> recipeList;
    ArrayList<Step> stepList;
    Recipe currentRecipe;
    int currentIndex;
    StepAdapter stepAdapter;

    public void setRecipeList(ArrayList<Recipe> newRecpeList) {
        this.recipeList = newRecpeList;
    }

    public void setCurrentIndex(int newCurrentIndex) {
        this.currentIndex = newCurrentIndex;
        this.currentRecipe = recipeList.get(newCurrentIndex);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_detail_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        stepAdapter = new StepAdapter(getContext(), this);
        stepRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        stepRecyclerView.setAdapter(stepAdapter);

        textIngredient.setText(constructIngredientsDescription(currentRecipe.ingredients));
        stepAdapter.setSteps(currentRecipe.steps);

        if (is_pad) loadStepFragment();
    }

    @Override
    public void onItemClick(int position) {
        if (!is_pad){
            Intent intent = new Intent(getActivity(), RecipeStepActivity.class);
            startActivity(intent);
        } else {
            loadStepFragment();
        }
    }

    private void loadStepFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_recipe_step_fragment, new RecipeStepFragment()).commit();
    }

    private CharSequence constructIngredientsDescription(ArrayList<Ingredient> ingredients) {
        String ingredientsTitle = "INGREDIENTS";
        SpannableString spanTitle = new SpannableString(ingredientsTitle);
        spanTitle.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.material_typography_regular_title_text_size)), 0, ingredientsTitle.length(), SPAN_INCLUSIVE_INCLUSIVE);

        String ingredientsString = "\n";
        for (Ingredient ingredient: ingredients) {
            ingredientsString += ingredient.name + ": " + ingredient.quantity + " " + ingredient.measure + "\n";
        }
        SpannableString spanContent = new SpannableString(ingredientsString);
        spanContent.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.material_typography_regular_body_1_text_size)), 0, ingredientsString.length(), SPAN_INCLUSIVE_INCLUSIVE);

        return TextUtils.concat(spanTitle, spanContent);
    }
}
