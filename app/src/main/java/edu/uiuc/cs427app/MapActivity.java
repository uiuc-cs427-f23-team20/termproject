package edu.uiuc.cs427app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

public class MapActivity extends AppCompatActivity {

    edu.uiuc.cs427app.DataHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        myDB = new edu.uiuc.cs427app.DataHelper(this);

        // Retrieve city_id from Intent
        String cityId = getIntent().getStringExtra("cityId").toString();

        // Call method from DataHelper to load coordinates (hard-code for test)
        List<String> coordinates = myDB.getCoords(cityId);
        String latitude = coordinates.get(0);
        String longitude = coordinates.get(1);

        // Update Webview settings
        WebView webView = (WebView) findViewById(R.id.webViewMap);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);

        // Load WebView with Google Map Embed
        String mapUrl = "https://maps.google.com/maps?q=" + latitude + "," + longitude + "&t=&z=15&ie=UTF8&iwloc=&output=embed";
        String iFrameHTML = "<iframe width=\"100%\" height=\"100%\" src=" + mapUrl + " title=\"Google Map\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        webView.loadData(iFrameHTML, "text/html","utf-8");
        webView.setWebChromeClient(new WebChromeClient());
    }
}