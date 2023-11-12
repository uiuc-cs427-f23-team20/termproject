package edu.uiuc.cs427app;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


/**
 * DetailsActivity displays detailed information about a specific city chosen by the user.
 * It shows the welcome message, weather information, and provides options to view the city on a map
 * (will be implemented in milestone 4)
 * and delete the city from the user's saved cities. It communicates with the database to perform
 * user-specific operations such as deleting a city.
 */
public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{

    edu.uiuc.cs427app.DataHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        myDB = new edu.uiuc.cs427app.DataHelper(this);

        // Process the Intent payload that has opened this Activity and show the information accordingly
        String cityName = getIntent().getStringExtra("city").toString();
        String welcome = "\n\t\tWelcome to the "+cityName;
        //String cityWeatherInfo = "Detailed information about the weather of "+cityName;

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

        // Initializing the GUI elements
        TextView welcomeMessage = findViewById(R.id.welcomeText);
        //TextView cityInfoMessage = findViewById(R.id.cityInfo);
        welcomeMessage.setText(welcome);
        //cityInfoMessage.setText(cityWeatherInfo);

        // Button for showing weather of city
        Button buttonWeather = findViewById(R.id.weatherButton);
        buttonWeather.setOnClickListener(this);

        buttonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(view.getContext(), WeatherActivity.class);
                intent.putExtra("city", extras.getString("city"));
                intent.putExtra("username", extras.getString("username"));
                intent.putExtra("cityId", extras.getString("cityId"));
                intent.putExtra("uiConfig", extras.getBoolean("uiConfig"));
                startActivity(intent);
            }
        });

        // Button for showing city on map
        Button buttonMap = findViewById(R.id.mapButton);
        //buttonMap.setOnClickListener(this);


        // Set click listener for the map button
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("cityId", extras.getString("cityId"));
                intent.putExtra("cityName", extras.getString("city"));
                startActivity(intent);
            }
        });







        // Button for deleting the city from user's saved cities
        Button buttonDelete = new Button(this);
        buttonDelete.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        buttonDelete.setText("Delete City");

        // Set click listener for the delete button
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the database helper method to delete the city for the logged-in user
                myDB.deleteUserCity(extras.getString("username"), extras.getString("cityId"));
                // Navigate back to HomeActivity after deleting the city
                Intent intent;
                intent = new Intent(view.getContext(), HomeActivity.class);
                intent.putExtra("username",extras.getString("username"));
                startActivity(intent);
            }
        });

        // Get the outer layout container and add the delete button to the layout
        LinearLayout outerlayout = findViewById(R.id.details);
        outerlayout.addView(buttonDelete);

    }

    @Override
    public void onClick(View view) {
        //Implement this (create an Intent that goes to a new Activity, which shows the map)
        // TODO: DO NOT DELETE THIS OR APP WILL CRASH!

    }
}

