package edu.uiuc.cs427app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button logoutButton = findViewById(R.id.btnLogout);

        // Initialize logout button and set click listener
        logoutButton.setOnClickListener(new View.OnClickListener() {
            // Show logout confirmation dialog
            @Override
            public void onClick(View v) {
                logoutMenu(LogoutActivity.this);
            }
        });
    }

    // Logout function, redirects to LoginActivity
    public void logout(View view) {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Show logout confirmation dialog
    private void logoutMenu(LogoutActivity logoutActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(logoutActivity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        // Positive button with logout action
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout(null);
            }
        });
        // Negative button to dismiss dialog
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
