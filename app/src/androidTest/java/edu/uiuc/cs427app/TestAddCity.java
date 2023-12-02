package edu.uiuc.cs427app;

import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;

import android.content.Context;
import android.content.Intent;
import android.view.View;

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

public class TestAddCity {
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
    public void testAddCity() {
        // Input the city name in the add city text box
        Espresso.onView(allOf(withClassName(endsWith("EditText"))))
                .perform(replaceText("Chicago"));

        // Check add city input text is replaced
        Espresso.onView(allOf(withClassName(endsWith("EditText"))))
                .check(ViewAssertions.matches(withText("Chicago")));

        // Click the "Add a location" button from the home page
        Espresso.onView(ViewMatchers.withTagValue(Matchers.is((Object) "addCityButton")))
                .perform(ViewActions.click());

        // Ensure we are in the Add cities activity
        Espresso.onView(ViewMatchers.withId(R.id.welcomeText))
                .check(ViewAssertions.matches(withText("All Available Cities")));

        // Click the "Add City" button
        Espresso.onView(ViewMatchers.withText("Add City"))
                .perform(ViewActions.click());

        // Ensure we are back in the HomeActivity
        Espresso.onView(ViewMatchers.withId(R.id.welcomeText))
                .check(ViewAssertions.matches(withText("DashBoard")));

        // Check if the city is added to the UI
        Espresso.onView(withText(testCityName))
                .check(ViewAssertions.matches(withText(testCityName)));

        // Check if the city is added to the database
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DataHelper dataHelper = new DataHelper(context);
        Map<String, String> userCities = dataHelper.getUserCitiesIdToCityName(testUsername);
        assert userCities.containsValue(testCityName);
    }

}