package com.mobitrackbd.mobitrack.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobitrackbd.mobitrack.Model.Vehicle;
import com.mobitrackbd.mobitrack.R;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    private Vehicle vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        vehicle = new Vehicle(1,23.746466,90.376015);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //polyLineList = new ArrayList<>();

        mapFragment.getMapAsync(MapActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(false);
        mMap.setIndoorEnabled(false);
        mMap.setBuildingsEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);



        gotoVehicleLocation(vehicle);

    }

    private void gotoVehicleLocation(Vehicle vehicle) {
        LatLng latLng = new LatLng(vehicle.getLat(),vehicle.getLng());

        MarkerOptions option = new MarkerOptions().position(latLng).title("Vehicle Location");
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).tilt(30).build();
        Marker marker = mMap.addMarker(option);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}
