package edu.uiuc.cs427app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    edu.uiuc.cs427app.DataHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myDB = new edu.uiuc.cs427app.DataHelper(this);

        // Task #3: Displaying User's UI Settings
        // Check for intent extras passed from login
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String user = intent.getExtras().getString("user");
            // Query database for uiConfig setting
            Boolean uiConfig = myDB.checkUIConfig(user);
            if (uiConfig) {
                // if uiConfig is set to true, enable dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}