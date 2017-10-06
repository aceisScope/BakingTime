package com.binghui.binghuiliu.bakingtime.data;

import android.content.Context;

import com.binghui.binghuiliu.bakingtime.R;
import com.binghui.binghuiliu.bakingtime.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by binghuiliu on 05/10/2017.
 */

public class RecipeService {

    private Context mContext;
    private Gson mGson;

    public RecipeService(Context context) {
        this.mContext = context;
    }

    public List<Recipe> recipes = null;

    public void parseRecipeJsonFile() {
        //Reading source from local file
        InputStream inputStream = mContext.getResources().openRawResource(R.raw.baking);
        String jsonString = readJsonFile(inputStream);

        mGson = new Gson();
        recipes = mGson.fromJson(jsonString, new TypeToken<List<Recipe>>() {}.getType());
    }

    public List<Recipe> getRecipes() {
        return this.recipes;
    }

    public Gson gsonInstance() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
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
