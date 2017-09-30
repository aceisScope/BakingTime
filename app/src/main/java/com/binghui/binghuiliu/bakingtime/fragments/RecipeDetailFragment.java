package com.binghui.binghuiliu.bakingtime.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binghui.binghuiliu.bakingtime.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by binghuiliu on 28/09/2017.
 */

public class RecipeDetailFragment extends Fragment {

    @BindView(R.id.text_ingredient)
    TextView textIngredient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_detail_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
