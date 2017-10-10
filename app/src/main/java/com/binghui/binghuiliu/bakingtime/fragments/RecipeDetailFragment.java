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
import android.widget.TextView;

import com.binghui.binghuiliu.bakingtime.R;
import com.binghui.binghuiliu.bakingtime.RecipeStepActivity;
import com.binghui.binghuiliu.bakingtime.adapters.StepAdapter;
import com.binghui.binghuiliu.bakingtime.data.RecipeProvider;
import com.binghui.binghuiliu.bakingtime.model.Recipe;
import com.binghui.binghuiliu.bakingtime.model.Step;

import java.util.ArrayList;

import butterknife.BindBool;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

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

    @BindString(R.string.recipe_step_key)
    String recipe_step_key;

    ArrayList<Recipe> recipeList;
    ArrayList<Step> currentRecipeStepList;
    Recipe currentRecipe;
    Step currentStep;
    StepAdapter stepAdapter;

    public void setRecipeList(ArrayList<Recipe> newRecpeList) {
        this.recipeList = newRecpeList;
    }

    public void setCurrentRecipeIndex(int newCurrentIndex) {
        this.currentRecipe = recipeList.get(newCurrentIndex);
        this.currentRecipeStepList = currentRecipe.steps;
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

        textIngredient.setText(RecipeProvider.constructIngredientsDescription(getContext(), currentRecipe.ingredients));
        stepAdapter.setSteps(currentRecipe.steps);

        if (is_pad) {
            if (savedInstanceState != null) {
                currentStep = savedInstanceState.getParcelable(recipe_step_key);
            } else {
                currentStep = currentRecipeStepList.get(0);
            }
            loadStepFragmentOfCurrentStep(currentStep);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(recipe_step_key, currentStep);
    }

    @Override
    public void onItemClick(int position) {
        currentStep = currentRecipeStepList.get(position);
        if (!is_pad){
            Intent intent = new Intent(getActivity(), RecipeStepActivity.class);
            intent.putExtra(recipe_step_key, currentStep);
            startActivity(intent);
        } else {
            loadStepFragmentOfCurrentStep(currentStep);
        }
    }

    private void loadStepFragmentOfCurrentStep(Step currentStep) {
        RecipeStepFragment stepFragment = new RecipeStepFragment();
        // pass step object via bundle, so it can be persisted over rotation
        Bundle bundle = new Bundle();
        bundle.putParcelable(recipe_step_key, currentStep);
        stepFragment.setArguments(bundle);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_recipe_step_fragment, stepFragment).commit();
    }
}
