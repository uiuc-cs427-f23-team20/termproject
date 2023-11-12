package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


/**
 * WeatherActivity displays detailed information about weather of a specific city chosen by the user.
 * It shows the welcome message, weather information
 */
public class WeatherActivity extends AppCompatActivity implements View.OnClickListener{

    edu.uiuc.cs427app.DataHelper myDB;

    private final String weatherBitUrl = "https://api.weatherbit.io/v2.0/current";
    //private final String weatherBitAppid = "01a058676dd44bbba82977c15214baaa"; 50 calls/day
    private final String weatherBitAppid = "7efdeda16cf74897aa506f15f96e4ab6"; //1500 calls/day for 21 days till Nov-31, then change key

    String cityWeatherInfo="";
    TextView weatherInfoMessage;

    // on create weather activity page , do this activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        myDB = new edu.uiuc.cs427app.DataHelper(this);


        // Task #3: Displaying User's UI Settings
        // Check for intent extras passed from login
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String user = intent.getExtras().getString("username");
            // Task #1: set new title
            setTitle("Team #20_" + user);
            // Query database for uiConfig setting
            Boolean uiConfig = extras.getBoolean("uiConfig"); //myDB.checkUIConfig(user);

            if (uiConfig) {
                // if uiConfig is set to true, enable dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        } else {
            // defaults to light mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Process the Intent payload that has opened this Activity and show the information accordingly
        String cityId = getIntent().getStringExtra("cityId").toString();
        String cityName = getIntent().getStringExtra("city").toString();
        String welcome = "\n\t\tWelcome to the "+cityName;
        cityWeatherInfo = "Detailed information about the weather of "+cityName;

        //=====
        // test only
        //cityId="khkhkpioihfdsceswsd"; //wrong cityId
        //cityName="   ";
        //===========

        String tempWeatherBitUrl = "";
        if (cityId!=null && cityId.length()>0) {
            CityTable cityTable = myDB.getCitiesByCityId(cityId);
            if (cityTable !=null && ! Double.isNaN(cityTable.getLatitude()) && ! Double.isNaN(cityTable.getLongitude())
                                 && cityTable.getLatitude() != 0.0 && cityTable.getLongitude() != 0.0) {
                tempWeatherBitUrl  = weatherBitUrl +  "?lat=" + Double.toString(cityTable.getLatitude()).trim()
                        +  "&lon=" + Double.toString(cityTable.getLongitude()).trim()
                        + "&key=" + weatherBitAppid;
            }
            else if (cityTable !=null &&  cityTable.getCitiName()!=null && cityTable.getCitiName().trim().length()>0) {
                    tempWeatherBitUrl  = weatherBitUrl +  "?city=" + cityTable.getCitiName().trim().replace(' ','+') ;
                    if (cityTable.getState()!=null && cityTable.getState().trim().length()>0) {
                        tempWeatherBitUrl  = tempWeatherBitUrl +  "," + cityTable.getState().trim().replace(' ','+') ;
                    }
                    if (cityTable.getCountry()!=null && cityTable.getCountry().trim().length()>0) {
                        tempWeatherBitUrl  = tempWeatherBitUrl +  "&country=" + cityTable.getCountry().trim().replace(' ','+') ;
                    }
                    tempWeatherBitUrl  = tempWeatherBitUrl  +  "&key=" + weatherBitAppid;
                }
            else
                tempWeatherBitUrl  = weatherBitUrl +  "?city_id=" + cityId.trim().replace(' ','+') + "&key=" + weatherBitAppid;
        }

        new GetJSONTaskWeatherBit().execute(tempWeatherBitUrl); //execute asynctask object this will resolve NetworkOnMainThreadExcection

        // Initializing the GUI elements
        TextView welcomeMessage = findViewById(R.id.weatherWelcomeText);
        weatherInfoMessage = findViewById(R.id.weatherInfo);
        welcomeMessage.setText(welcome);
        weatherInfoMessage.setText(cityWeatherInfo);

    }

    @Override
    public void onClick(View view) {
        //Implement this, not required
        // TODO: DO NOT DELETE THIS OR APP WILL CRASH!

    }

    // execute asynctask object this will resolve NetworkOnMainThreadExcection
    // this will request http  to get data from weatherbit site using Utility.downloadDataFromUrlWeatherBit method
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

        // after getting data from weatherbit site push to weatherInfoMessage textbox to display
        @Override
        protected void onPostExecute(String result) {
            cityWeatherInfo =  "\t\tCurrent weather :\n\n";
            String detailWeather=cityWeatherInfo + result;
            weatherInfoMessage.setText(detailWeather);
        }
    }
}

