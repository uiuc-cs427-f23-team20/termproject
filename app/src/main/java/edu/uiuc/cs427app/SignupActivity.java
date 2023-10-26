package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SignupActivity extends AppCompatActivity {
    EditText username, password, repassword;
    Button btnSignUp, btnSignIn;
    edu.uiuc.cs427app.DataHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        myDB = new edu.uiuc.cs427app.DataHelper(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (user.equals("") || pass.equals("") || repass.equals("")) {
                    Toast.makeText(SignupActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(pass.equals(repass))
                    {
                        Boolean usercheckResult = myDB.checkUsername(user);
                        if(usercheckResult == false)
                        {
                            Boolean regResult = myDB.insertData(user,pass);
                            if (regResult == true){
                                Toast.makeText(SignupActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);

                            }
                            else
                            {
                                Toast.makeText(SignupActivity.this, "Registered Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(SignupActivity.this, "User already Exists.\n Please Sign In", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(SignupActivity.this,"Password not Matching.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

            }

        });
    }
}




