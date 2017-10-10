package com.binghui.binghuiliu.bakingtime;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.Intents.intended;

/**
 * Created by binghuiliu on 2017/10/9.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityIntentTest {

    private static final String recipe_index_key = "recipe_index";
    private static final int position = 1;

    @Rule
    public IntentsTestRule<RecipeListActivity> intentsTestRule = new IntentsTestRule<>(RecipeListActivity.class);

    @Test
    public void clickOnRecipeListPutRecipeIndexToIntent() {
        CountingIdlingResource mainActivityIdlingResource = intentsTestRule.getActivity().getEspressoIdlingResourceForMainActivity();
        Espresso.registerIdlingResources(mainActivityIdlingResource);

        onView(withId(R.id.recipe_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        intended(hasExtra(recipe_index_key, position));
    }
}
