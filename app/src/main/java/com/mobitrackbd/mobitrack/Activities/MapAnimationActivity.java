package com.mobitrackbd.mobitrack.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.mobitrackbd.mobitrack.R;
import com.mobitrackbd.mobitrack.Utility.URL;
import com.mobitrackbd.mobitrack.Volley.MyPostVolley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapAnimationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    private List<LatLng> directionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_animation);

        //directionList = getIntent().getBundleExtra("datalist");

        Log.d("JJJJJ",directionList.size()+"");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //polyLineList = new ArrayList<>();

        mapFragment.getMapAsync(MapAnimationActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(false);
        mMap.setBuildingsEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);


    }
}
