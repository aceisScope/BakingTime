package com.binghui.binghuiliu.bakingtime.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binghui.binghuiliu.bakingtime.R;

import butterknife.ButterKnife;

/**
 * Created by binghuiliu on 25/09/2017.
 */

public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_recipe_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
