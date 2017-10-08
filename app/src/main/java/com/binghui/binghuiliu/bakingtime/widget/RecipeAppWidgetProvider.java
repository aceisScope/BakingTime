package com.binghui.binghuiliu.bakingtime.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.widget.RemoteViews;

import com.binghui.binghuiliu.bakingtime.R;
import com.binghui.binghuiliu.bakingtime.data.RecipeService;
import com.binghui.binghuiliu.bakingtime.model.Ingredient;
import com.binghui.binghuiliu.bakingtime.model.Recipe;

import java.util.ArrayList;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

/**
 * Created by binghuiliu on 2017/10/8.
 */

public class RecipeAppWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Recipe widgetRecipe = intent.getParcelableExtra(AppWidgetManager.EXTRA_CUSTOM_INFO);

        if (widgetRecipe != null) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
            views.setTextViewText(R.id.text_widget_recipe, RecipeService.constructIngredientsDescription(context, widgetRecipe.ingredients));
            AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, RecipeAppWidgetProvider.class),views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
}
