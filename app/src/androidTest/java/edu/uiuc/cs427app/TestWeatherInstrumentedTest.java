package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class TestWeatherInstrumentedTest {
    private Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private Integer SLEEP = 3000;
    DataHelper myDB;

    String cityWeatherInfo="";
    private final String weatherBitUrl = "https://api.weatherbit.io/v2.0/current";
    private final String weatherBitAppid = "7efdeda16cf74897aa506f15f96e4ab6"; //1500 calls/day for 21 days till Nov-31, then change key

    @Test
    public void TestWeather_1() throws Exception {
        // test for a random city and get weatherbit information using latitude and longitude

        myDB = new DataHelper(context);

        // Get random city
        Map<String, String> city = myDB.getRandomCity();
        String cityId = city.get("cityId");
        String cityName = city.get("cityName");
        String latitude = city.get("latitude").toString();
        String longitude = city.get("longitude").toString();

        String welcome = "\n\t\tWelcome to the "+cityName;
        cityWeatherInfo = "Detailed information about the weather of "+cityName;

        assertNotNull("TestWeather_1 : latitude null",latitude);
        assertTrue("TestWeather_1 : latitude has no value",latitude.trim().replace(' ','+').length()>0);
        assertNotNull("TestWeather_1 : longitude null",longitude);
        assertTrue("TestWeather_1 : longitude has no value",longitude.trim().replace(' ','+').length()>0);

        String tempWeatherBitUrl = "";
        tempWeatherBitUrl  = weatherBitUrl +  "?lat=" + latitude.trim()
                                +  "&lon=" + longitude.trim()
                                + "&key=" + weatherBitAppid;
        new GetJSONTaskWeatherBit().execute(tempWeatherBitUrl); //execute asynctask object this will resolve NetworkOnMainThreadExcection

        // Create new intent
        Intent intent = new Intent();
        intent.setClass(context, DetailsActivity.class);
        intent.putExtra("cityId", cityId);
        intent.putExtra("city", cityName);
        intent.putExtra("username", "test_user");

        // Launch DetailsActivity with custom intent
        ActivityScenario.launch(intent);
        Thread.sleep(SLEEP);
        onView(withText("SHOW WEATHER")).perform(click());
        Thread.sleep(SLEEP);

        // Checks for welcome message
        onView(withId(R.id.weatherWelcomeText)).check(matches(withText(welcome)));

        // Checks for city weather information
        onView(withId(R.id.weatherInfo)).check(matches(withText(cityWeatherInfo)));

        System.out.println("TestWeather_1 : welcome message = "+welcome);
        System.out.println("TestWeather_1 : cityWeatherInfo = "+cityWeatherInfo);

    }

    @Test
    public void TestWeather_2() throws Exception {
        // test for a random city and get weatherbit information using latitude and longitude

        myDB = new DataHelper(context);

        // Get random city
        Map<String, String> city = myDB.getRandomCity();
        String cityId = city.get("cityId");
        String cityName = city.get("cityName");
        String latitude = city.get("latitude").toString();
        String longitude = city.get("longitude").toString();

        String welcome = "\n\t\tWelcome to the "+cityName;
        cityWeatherInfo = "Detailed information about the weather of "+cityName;

        assertNotNull("TestWeather_2 : latitude null",latitude);
        assertTrue("TestWeather_2 : latitude has no value",latitude.trim().replace(' ','+').length()>0);
        assertNotNull("TestWeather_2 : longitude null",longitude);
        assertTrue("TestWeather_2 : longitude has no value",longitude.trim().replace(' ','+').length()>0);

        String tempWeatherBitUrl = "";
        tempWeatherBitUrl  = weatherBitUrl +  "?lat=" + latitude.trim()
                +  "&lon=" + longitude.trim()
                + "&key=" + weatherBitAppid;
        new GetJSONTaskWeatherBit().execute(tempWeatherBitUrl); //execute asynctask object this will resolve NetworkOnMainThreadExcection

        // Create new intent
        Intent intent = new Intent();
        intent.setClass(context, DetailsActivity.class);
        intent.putExtra("cityId", cityId);
        intent.putExtra("city", cityName);
        intent.putExtra("username", "test_user");

        // Launch DetailsActivity with custom intent
        ActivityScenario.launch(intent);
        Thread.sleep(SLEEP);
        onView(withText("SHOW WEATHER")).perform(click());
        Thread.sleep(SLEEP);

        // Checks for welcome message
        onView(withId(R.id.weatherWelcomeText)).check(matches(withText(welcome)));

        // Checks for city weather information
        onView(withId(R.id.weatherInfo)).check(matches(withText(cityWeatherInfo)));

        System.out.println("TestWeather_2 : welcome message = "+welcome);
        System.out.println("TestWeather_2 : cityWeatherInfo = "+cityWeatherInfo);

    }


    @Test
    public void TestWeather_chicago() throws Exception {
        // test for a chicago city and get weatherbit information using latitude and longitude

        myDB = new DataHelper(context);

        // Get random city
        String c1="Chicago";
        String s1="IL";
        String C1="United States";

        CityTable city = myDB.getCitiesByCity(c1,s1,C1);
        String cityId = city.getCitiId();
        String cityName = city.getCitiName();
        String latitude =  Double.toString(city.getLatitude());
        String longitude = Double.toString(city.getLongitude());
        System.out.println("CityTable = "+city);

        String welcome = "\n\t\tWelcome to the "+cityName;
        cityWeatherInfo = "Detailed information about the weather of "+cityName;

        assertNotNull("TestWeather_chicago : latitude null",latitude);
        assertTrue("TestWeather_chicago : latitude has no value",latitude.trim().replace(' ','+').length()>0);
        assertNotNull("TestWeather_chicago : longitude null",longitude);
        assertTrue("TestWeather_chicago : longitude has no value",longitude.trim().replace(' ','+').length()>0);

        String tempWeatherBitUrl = "";
        tempWeatherBitUrl  = weatherBitUrl +  "?lat=" + latitude.trim()
                +  "&lon=" + longitude.trim()
                + "&key=" + weatherBitAppid;
        new GetJSONTaskWeatherBit().execute(tempWeatherBitUrl); //execute asynctask object this will resolve NetworkOnMainThreadExcection

        // Create new intent
        Intent intent = new Intent();
        intent.setClass(context, DetailsActivity.class);
        intent.putExtra("cityId", cityId);
        intent.putExtra("city", cityName);
        intent.putExtra("username", "test_user");

        // Launch DetailsActivity with custom intent
        ActivityScenario.launch(intent);
        Thread.sleep(SLEEP);
        onView(withText("SHOW WEATHER")).perform(click());
        Thread.sleep(SLEEP);

        // Checks for welcome message
        onView(withId(R.id.weatherWelcomeText)).check(matches(withText(welcome)));

        // Checks for city weather information
        onView(withId(R.id.weatherInfo)).check(matches(withText(cityWeatherInfo)));

        System.out.println("TestWeather_chicago : welcome message = "+welcome);
        System.out.println("TestWeather_chicago : cityWeatherInfo = "+cityWeatherInfo);

    }
    private class GetJSONTaskWeatherBit extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // get data from weatherbit site using given url
        @Override
        protected String doInBackground(String... urls) {
            try {
                String aa = Utility.downloadDataFromUrlWeatherBit(urls[0]);
                return aa;
            } catch (Exception e) {
                return "Unable to retrieve data. URL may be invalid.";
            }

        }

        // after getting data from weatherbit site push to cityWeatherInfo variable
        @Override
        protected void onPostExecute(String result) {
            cityWeatherInfo =  "\t\tCurrent weather :\n\n";
            cityWeatherInfo=cityWeatherInfo + result;
        }
    }

}
