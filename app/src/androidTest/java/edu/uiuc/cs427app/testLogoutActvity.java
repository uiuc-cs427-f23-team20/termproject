package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.junit.Assert.assertEquals;

import android.content.Intent;
import android.widget.TextView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class testLogoutActvity {

    @Rule
    public ActivityScenarioRule<LogoutActivity> activityRule =
            new ActivityScenarioRule<>(LogoutActivity.class);

    @Test
    public void testLogout() {
        // Click on the logout button
        onView(withId(R.id.btnLogout)).perform(click());

        // Check if the LoginActivity is displayed after logout
         ActivityScenario<LoginActivity> loginActivityScenario =
                ActivityScenario.launch(new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), LoginActivity.class));

        // Assertion: Check if a specific button is present in the LoginActivity
        onView(withId(R.id.btnLogin))
                .check(matches(isDisplayed()));
    }
}

