package edu.uiuc.cs427app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.navigation.ui.AppBarConfiguration;
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

        // Initializing the UI components
        // The list of locations should be customized per user (change the implementation so that
        // buttons are added to layout programmatically
        Button buttonChampaign = findViewById(R.id.buttonChampaign);
        Button buttonChicago = findViewById(R.id.buttonChicago);
        Button buttonLA = findViewById(R.id.buttonLA);
        Button buttonNew = findViewById(R.id.buttonAddLocation);

        buttonChampaign.setOnClickListener(this);
        buttonChicago.setOnClickListener(this);
        buttonLA.setOnClickListener(this);
        buttonNew.setOnClickListener(this);

        Button buttonLogout = findViewById(R.id.btnLogout);
        buttonLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.buttonChampaign:
                intent = new Intent(this, DetailsActivity.class);
                intent.putExtra("city", "Champaign");
                startActivity(intent);
                break;
            case R.id.buttonChicago:
                intent = new Intent(this, DetailsActivity.class);
                intent.putExtra("city", "Chicago");
                startActivity(intent);
                break;
            case R.id.buttonLA:
                intent = new Intent(this, DetailsActivity.class);
                intent.putExtra("city", "Los Angeles");
                startActivity(intent);
                break;
            case R.id.btnLogout:
                intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.buttonAddLocation:
                // Implement this action to add a new location to the list of locations
                break;
        }
    }

//    public void logout(View view) {
//        Intent intent = new Intent(this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        finish();
//    }
}