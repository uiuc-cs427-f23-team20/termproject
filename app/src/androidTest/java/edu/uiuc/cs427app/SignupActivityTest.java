package edu.uiuc.cs427app;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SignupActivityTest {
    @Rule
    // ActivityScenarioRule launches a given activity before the test starts and closes after test.
    public ActivityScenarioRule<SignupActivity> activityRule = new ActivityScenarioRule<>(SignupActivity.class);

    // DataHelper instance for interacting with the database
    private DataHelper dataHelper = new DataHelper(ApplicationProvider.getApplicationContext());

    @Test
    public void testUserSignup() {
        // Simulate user typing a username and password
        onView(withId(R.id.username)).perform(typeText("newuser"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("newpassword"), closeSoftKeyboard());
        onView(withId(R.id.repassword)).perform(typeText("newpassword"), closeSoftKeyboard());

        // Simulate user clicking the sign-up button
        onView(withId(R.id.btnSignUp)).perform(click());

        // Check if the user exists in the database
        boolean userExists = dataHelper.checkUsername("newuser");
        // Assert that the user should be in the database
        assertTrue("User should exist in the database after signup.", userExists);

    }

    @Test
    public void testNightModeToggle() {
        // Simulate a click on the night mode toggle switch
        onView(withId(R.id.switchSignUp)).perform(click());

        // Verify if the night mode is activated by checking if the Switch text changes to "Disable Dark Mode"
        onView(withId(R.id.switchSignUp)).check(matches(withText("Disable Dark Mode")));
    }
}