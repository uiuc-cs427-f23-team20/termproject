package edu.uiuc.cs427app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button btnLogin, btnResignup;
    edu.uiuc.cs427app.DataHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.usernameLogin);
        password = (EditText)findViewById(R.id.passwordLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnResignup = (Button)findViewById(R.id.btnResignup);
        myDB = new edu.uiuc.cs427app.DataHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean result = myDB.checkusernamePassword(user,pass);
                    if(result == true){
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        // pass username variable to HomeActivity to query for uiconfig
                        intent.putExtra("user",user);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            {
            }
        });

        btnResignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}