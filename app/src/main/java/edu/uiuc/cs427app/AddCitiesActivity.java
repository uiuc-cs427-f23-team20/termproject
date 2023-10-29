package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Map;

public class AddCitiesActivity extends AppCompatActivity implements View.OnClickListener {
    edu.uiuc.cs427app.DataHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cities);
        myDB = new edu.uiuc.cs427app.DataHelper(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        LinearLayout outerLayout = (LinearLayout) findViewById(R.id.details);
        int linearLayoutHeight = (int) getResources().getDimension(R.dimen.LinearLayout_height);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, linearLayoutHeight);
        int buttonWidth = (int) getResources().getDimension(R.dimen.button_width);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(buttonWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.weight = 1;

        Map<String, String> citiesIdToName = myDB.getAllCities();
        for (String cityId : citiesIdToName.keySet()) {
            LinearLayout cityLayout = new LinearLayout(this);
            cityLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            cityLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView cityNameView = new TextView(this);
            cityNameView.setLayoutParams(textViewParams);
            cityNameView.setText(citiesIdToName.get(cityId));

            Button addCityButton = new Button(this);
            addCityButton.setLayoutParams(buttonParams);
            addCityButton.setText("Add City");
            addCityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myDB.insertUsersCities(extras.getString("username"), cityId);
                    Intent intent;
                    intent = new Intent(view.getContext(), HomeActivity.class);
                    intent.putExtra("username",extras.getString("username"));
                    startActivity(intent);
                }
            });

            cityLayout.addView(cityNameView);
            cityLayout.addView(addCityButton);
            outerLayout.addView(cityLayout);
        }
    }

    @Override
    public void onClick(View view) {

    }


}
