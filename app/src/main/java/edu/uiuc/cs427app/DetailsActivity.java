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
        String welcome = "Welcome to the "+cityName;
        String cityWeatherInfo = "Detailed information about the weather of "+cityName;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        // Initializing the GUI elements
        TextView welcomeMessage = findViewById(R.id.welcomeText);
        TextView cityInfoMessage = findViewById(R.id.cityInfo);
        welcomeMessage.setText(welcome);
        cityInfoMessage.setText(cityWeatherInfo);

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

