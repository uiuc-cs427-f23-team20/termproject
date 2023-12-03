package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import android.content.Context;
import android.content.Intent;
import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class testLogout {
    private Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private String testUsername = "test"; // replace this with the user you need to test

    @Before
    public void launchHomeActivity() {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("username", testUsername);
        ActivityScenario.launch(intent);
    }

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
