package edu.uiuc.cs427app;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;


/**
 * Test city is set to Chicago, IL
 * To run this test, make sure Chicago is already in the list for the test user
 */

@RunWith(AndroidJUnit4.class)
public class TestRemoveCity {
    private Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private String testUsername = "test"; // replace this with the user you need to test
    private String testCityName = "Chicago, IL"; // replace this with the city you need to test

    @Before
    public void launchHomeActivity() {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("username", testUsername);
        ActivityScenario.launch(intent);
    }

    @Test
    public void testRemoveCity() {
        // Click the "VIEW DETAIL" button for the test city
        Espresso.onView(ViewMatchers.withTagValue(Matchers.is((Object) testCityName)))
                .perform(ViewActions.click());

//        // Ensure we are in the DetailsActivity
//        Espresso.onView(ViewMatchers.withId(R.id.welcomeText))
//                .check(ViewAssertions.matches(ViewMatchers.withText("Welcome to the " + testCityName)));

        // Click the "DELETE CITY" button
        Espresso.onView(ViewMatchers.withText("Delete City"))
                .perform(ViewActions.click());

//        // Ensure we are back in the HomeActivity
//        Espresso.onView(ViewMatchers.withId(R.id.welcomeText))
//                .check(ViewAssertions.matches(ViewMatchers.withText("DashBoard")));

        // Check if the city is removed from the UI
        Espresso.onView(ViewMatchers.withText(testCityName))
                .check(ViewAssertions.doesNotExist());

        // Check if the city is removed from the database
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DataHelper dataHelper = new DataHelper(context);
        Map<String, String> userCities = dataHelper.getUserCitiesIdToCityName(testUsername);
        assert !userCities.containsValue(testCityName);
    }

}
