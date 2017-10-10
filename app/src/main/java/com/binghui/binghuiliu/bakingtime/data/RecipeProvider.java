package com.binghui.binghuiliu.bakingtime.data;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;

import com.binghui.binghuiliu.bakingtime.R;
import com.binghui.binghuiliu.bakingtime.model.Ingredient;
import com.binghui.binghuiliu.bakingtime.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

/**
 * Created by binghuiliu on 05/10/2017.
 */

public class RecipeProvider {

    public interface RecipeService {
        @GET("android-baking-app-json")
        Call<ArrayList<Recipe>> recipeList();
    }

    private Context mContext;

    public RecipeProvider(Context context) {
        this.mContext = context;
    }

    public static ArrayList<Recipe> recipes = null;

    public void fetchRecipeList(Callback<ArrayList<Recipe>> callback) {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://go.udacity.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RecipeService recipeService = retrofit.create(RecipeService.class);
        Call<ArrayList<Recipe>> call = recipeService.recipeList();
        call.enqueue(callback);
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
