package com.binghui.binghuiliu.bakingtime.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by binghuiliu on 05/10/2017.
 */

public class Ingredient {
    public String quantity;
    @SerializedName("ingredient")
    public String name;
    public String measure;

    public Ingredient() {

    }
}
