package edu.uiuc.cs427app;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.is;

@RunWith(AndroidJUnit4.class)
public class TestLoginInstrumentedTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);
    private View decorView;

    @Test
    public void testValidLogin() throws InterruptedException {
        //myDB = new edu.uiuc.cs427app.DataHelper(context);
        String TEST_USERNAME = "test_user";
        String TEST_PASSWORD = "test";

        // Enter username and password, then login
        onView(withId(R.id.usernameLogin))
                .check(matches(isDisplayed()))
                .perform(typeText(TEST_USERNAME), closeSoftKeyboard());

        onView(withId(R.id.passwordLogin))
                .check(matches(isDisplayed()))
                .perform(typeText(TEST_PASSWORD), closeSoftKeyboard());

        onView(withId(R.id.btnLogin)).perform(click());

        // Check if Details page is displayed after a successful login.
        onView(withId(R.id.details)).check(matches(isDisplayed()));
    }

    @Test
    public void testInvalidLogin() {
        String TEST_USERNAME = "test_user";
        String TEST_PASSWORD = "123";

        // Enter username and password, then login
        onView(withId(R.id.usernameLogin))
                .check(matches(isDisplayed()))
                .perform(typeText(TEST_USERNAME), closeSoftKeyboard());

        onView(withId(R.id.passwordLogin))
                .check(matches(isDisplayed()))
                .perform(typeText(TEST_PASSWORD), closeSoftKeyboard());

        onView(withId(R.id.btnLogin)).perform(click());


        // Check if the error message is displayed using ToastMatchers
        onView(withText("Invalid password"))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }
}
