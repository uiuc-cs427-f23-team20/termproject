package edu.uiuc.cs427app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.navigation.ui.AppBarConfiguration;

import java.util.List;
import java.util.Map;

import edu.uiuc.cs427app.databinding.ActivityHomeBinding;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    edu.uiuc.cs427app.DataHelper myDB;
    private AppBarConfiguration appBarConfiguration;
    private ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // on HomeActivity init, display correct ui setting for user
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
            Boolean uiConfig = myDB.checkUIConfig(user);
            System.out.println(uiConfig);
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


        // Task #2
        LinearLayout outerLayout = (LinearLayout) findViewById(R.id.details);
        int linearLayoutHeight = (int) getResources().getDimension(R.dimen.LinearLayout_height);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, linearLayoutHeight);
        int buttonWidth = (int) getResources().getDimension(R.dimen.button_width);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(buttonWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.weight = 1;

        Map<String, String> userCities = myDB.getUserCitiesIdToCityName(intent.getExtras().getString("username"));
        for (String cityId : userCities.keySet()) {
            LinearLayout cityLayout = new LinearLayout(this);
            cityLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            cityLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView cityNameView = new TextView(this);
            cityNameView.setLayoutParams(textViewParams);
            cityNameView.setText(userCities.get(cityId));

            Button addCityButton = new Button(this);
            addCityButton.setLayoutParams(buttonParams);
            addCityButton.setText("View Detail");
            addCityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    intent = new Intent(view.getContext(), DetailsActivity.class);
                    intent.putExtra("city", userCities.get(cityId));
                    intent.putExtra("username", extras.getString("username"));
                    intent.putExtra("cityId", cityId);
                    startActivity(intent);
                }
            });

            cityLayout.addView(cityNameView);
            cityLayout.addView(addCityButton);
            outerLayout.addView(cityLayout);
        }

        Button buttonAddCity = new Button(this);
        buttonAddCity.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        buttonAddCity.setText("Add a location");
        buttonAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(view.getContext(), AddCitiesActivity.class);
                intent.putExtra("username",extras.getString("username"));
                startActivity(intent);
            }
        });
        outerLayout.addView(buttonAddCity);

        Button buttonLogout = findViewById(R.id.btnLogout);
        buttonLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnLogout:
                intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }
}