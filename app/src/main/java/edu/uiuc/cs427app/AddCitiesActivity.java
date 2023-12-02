package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Map;

/**
 * AddCitiesActivity: This activity allows users to view a list of available cities and add them to their profile.
 * (subject to change to 3rd party API in milestone4.)
 * This activity displays a list of cities retrieved from the database. Users can add a selected city to their
 * profile by clicking the "Add City" button next to the city name. After adding a city, the user is redirected
 * back to the HomeActivity.
 */
public class AddCitiesActivity extends AppCompatActivity implements View.OnClickListener {
    edu.uiuc.cs427app.DataHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cities);
        myDB = new edu.uiuc.cs427app.DataHelper(this);

        // Retrieve the intent and its extras sent from the HomeActivity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        // Initialize layout parameters and dimensions
        LinearLayout outerLayout = (LinearLayout) findViewById(R.id.details);
        int linearLayoutHeight = (int) getResources().getDimension(R.dimen.LinearLayout_height);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, linearLayoutHeight);
        int buttonWidth = (int) getResources().getDimension(R.dimen.button_width);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(buttonWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.weight = 1;

        // Retrieve a map of city IDs to names from the local database
        // Iterate through the cities and dynamically create UI elements
        Map<String, String> citiesIdToName = myDB.getFilteredCities(extras.getString("inputCityName"));
        for (String cityId : citiesIdToName.keySet()) {
            LinearLayout cityLayout = new LinearLayout(this);
            cityLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            cityLayout.setOrientation(LinearLayout.HORIZONTAL);

            // Create a TextView to display the city name
            TextView cityNameView = new TextView(this);
            cityNameView.setLayoutParams(textViewParams);
            cityNameView.setText(citiesIdToName.get(cityId));

            // Create a Button for adding the city, and set an OnClickListener
            Button addCityButton = new Button(this);
            addCityButton.setLayoutParams(buttonParams);
            addCityButton.setText("Add City");
            addCityButton.setTag(citiesIdToName.get(cityId));
            addCityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Handle the click event by inserting the selected city for the user (calling data helper methods)
                    myDB.insertUsersCities(extras.getString("username"), cityId);
                    Intent intent;
                    intent = new Intent(view.getContext(), HomeActivity.class);
                    intent.putExtra("username",extras.getString("username"));
                    startActivity(intent);
                }
            });

            // Add UI elements to the layout
            cityLayout.addView(cityNameView);
            cityLayout.addView(addCityButton);
            outerLayout.addView(cityLayout);
        }
    }

    @Override
    public void onClick(View view) {
        // TODO: implement in milestone 4
    }


}
