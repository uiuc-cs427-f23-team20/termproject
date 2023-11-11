package edu.uiuc.cs427app;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;

//public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    edu.uiuc.cs427app.DataHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.mMap = googleMap;
        myDB = new edu.uiuc.cs427app.DataHelper(this);

        // Retrieve city_id from Intent
        String cityId = getIntent().getStringExtra("cityId").toString();
        String cityName = getIntent().getStringExtra("cityName");

        // Call method from DataHelper to load coordinates
        List<Double> coordinates = myDB.getCoords(cityId);
        Double latitude = coordinates.get(0);
        Double longitude = coordinates.get(1);


        // Add a marker in New York and move the camera
        LatLng position = new LatLng(latitude, longitude);
        String title = cityName + "(Lat: " + Double.toString(latitude) + ", Long: " + Double.toString(longitude) + ")";
        Marker marker = this.mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(title));
        marker.showInfoWindow();
        this.mMap.moveCamera(CameraUpdateFactory.newLatLng(position));

        // Add ZoomControls to enable zoom in/out interactive
        this.mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}

