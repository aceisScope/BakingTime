package com.binghui.binghuiliu.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.binghui.binghuiliu.bakingtime.data.RecipeService;
import com.binghui.binghuiliu.bakingtime.model.Recipe;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by binghuiliu on 2017/10/9.
 */

@SmallTest
@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {
    @Rule
    public ActivityTestRule<RecipeDetailActivity> mActivityRule =
            new ActivityTestRule<>(RecipeDetailActivity.class, true, false);

    @Before
    public void initRecipeService() {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        RecipeService recipeService = new RecipeService(targetContext);
        recipeService.parseRecipeJsonFile();
    }

    @Test
    public void someTest() {
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent intent = new Intent(targetContext, RecipeDetailActivity.class);
        intent.putExtra("recipe_index", 0);

        mActivityRule.launchActivity(intent);

        onView(withId(R.id.step_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.text_step))
                .check(matches(isDisplayed()));
    }
}
