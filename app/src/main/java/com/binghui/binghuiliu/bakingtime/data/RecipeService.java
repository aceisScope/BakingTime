package com.binghui.binghuiliu.bakingtime.data;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;

import com.binghui.binghuiliu.bakingtime.R;
import com.binghui.binghuiliu.bakingtime.model.Ingredient;
import com.binghui.binghuiliu.bakingtime.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

/**
 * Created by binghuiliu on 05/10/2017.
 */

public class RecipeService {

    private Context mContext;
    private Gson mGson;

    public RecipeService(Context context) {
        this.mContext = context;
    }

    public static ArrayList<Recipe> recipes = null;

    public void parseRecipeJsonFile() {
        //Reading source from local file
        InputStream inputStream = mContext.getResources().openRawResource(R.raw.baking);
        String jsonString = readJsonFile(inputStream);

        mGson = new Gson();
        recipes = mGson.fromJson(jsonString, new TypeToken<List<Recipe>>() {}.getType());
    }

    public static ArrayList<Recipe> getRecipes() {
        return RecipeService.recipes;
    }

    public static CharSequence constructIngredientsDescription(Context context, ArrayList<Ingredient> ingredients) {
        String ingredientsTitle = "INGREDIENTS";
        SpannableString spanTitle = new SpannableString(ingredientsTitle);
        spanTitle.setSpan(new AbsoluteSizeSpan(context.getResources().getDimensionPixelSize(R.dimen.material_typography_regular_title_text_size)), 0, ingredientsTitle.length(), SPAN_INCLUSIVE_INCLUSIVE);

        String ingredientsString = "\n";
        for (Ingredient ingredient: ingredients) {
            ingredientsString += ingredient.name + ": " + ingredient.quantity + " " + ingredient.measure + "\n";
        }
        SpannableString spanContent = new SpannableString(ingredientsString);
        spanContent.setSpan(new AbsoluteSizeSpan(context.getResources().getDimensionPixelSize(R.dimen.material_typography_regular_body_1_text_size)), 0, ingredientsString.length(), SPAN_INCLUSIVE_INCLUSIVE);

        return TextUtils.concat(spanTitle, spanContent);
    }

    private String readJsonFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte bufferByte[] = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(bufferByte)) != -1) {
                outputStream.write(bufferByte, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }
}
