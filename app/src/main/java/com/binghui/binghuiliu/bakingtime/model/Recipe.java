package com.binghui.binghuiliu.bakingtime.model;

import java.util.List;

/**
 * Created by binghuiliu on 05/10/2017.
 */

public class Recipe {
    public String id;
    public String name;
    public int servings;
    public String image;
    public List<Ingredient> ingredients;
    public List<Step> steps;
}
