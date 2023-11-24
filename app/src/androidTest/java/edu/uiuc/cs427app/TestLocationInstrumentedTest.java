package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class TestLocationInstrumentedTest {
    private Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private Integer SLEEP = 3000;
    edu.uiuc.cs427app.DataHelper myDB;

    @Test
    public void TestLocationA() throws Exception {
        myDB = new edu.uiuc.cs427app.DataHelper(context);

        // Get random city
        Map<String, String> city = myDB.getRandomCity();
        String cityId = city.get("cityId");
        String cityName = city.get("cityName");
        String latitude = city.get("latitude").toString();
        String longitude = city.get("longitude").toString();

        // Create new intent
        Intent intent = new Intent();
        intent.setClass(context, DetailsActivity.class);
        intent.putExtra("cityId", cityId);
        intent.putExtra("city", cityName);
        intent.putExtra("username", "test_user");

        // Launch DetailsActivity with custom intent
        ActivityScenario.launch(intent);
        Thread.sleep(SLEEP);
        onView(withText("SHOW MAP")).perform(click());
        Thread.sleep(SLEEP);

        // Checks for city name
        onView(withId(R.id.mapCityName)).check(matches(withText(cityName)));

        // Checks for city coordinates
        onView(withId(R.id.mapCityCoords)).check(matches(withText(containsString(latitude))));
        onView(withId(R.id.mapCityCoords)).check(matches(withText(containsString(longitude))));
    }

    @Test
    public void TestLocationB() throws Exception {
        myDB = new edu.uiuc.cs427app.DataHelper(context);

        // Get random city
        Map<String, String> city = myDB.getRandomCity();
        String cityId = city.get("cityId");
        String cityName = city.get("cityName");
        String latitude = city.get("latitude").toString();
        String longitude = city.get("longitude").toString();

        // Create new intent
        Intent intent = new Intent();
        intent.setClass(context, DetailsActivity.class);
        intent.putExtra("cityId", cityId);
        intent.putExtra("city", cityName);
        intent.putExtra("username", "test_user");

        // Launch DetailsActivity with custom intent
        ActivityScenario.launch(intent);
        Thread.sleep(SLEEP);
        onView(withText("SHOW MAP")).perform(click());
        Thread.sleep(SLEEP);

        // Checks for city name
        onView(withId(R.id.mapCityName)).check(matches(withText(cityName)));

        // Checks for city coordinates
        onView(withId(R.id.mapCityCoords)).check(matches(withText(containsString(latitude))));
        onView(withId(R.id.mapCityCoords)).check(matches(withText(containsString(longitude))));
    }

}
