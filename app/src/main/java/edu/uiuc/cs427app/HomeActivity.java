package edu.uiuc.cs427app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // display username on HomeActivity
        String username = getIntent().getStringExtra("username");
        setTitle("Team #20_" + username);
//        TextView usernameTextView = findViewById(R.id.usernameTextView);
//        usernameTextView.setText("Team #20_" + username);

    }
}