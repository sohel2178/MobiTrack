package com.mobitrackbd.mobitrack.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.mobitrackbd.mobitrack.R;
import com.mobitrackbd.mobitrack.Volley.MyGetVolley;

public class DistanceReportActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_report);
        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        setTitle("Distance Report");

        MyGetVolley myGetVolley = new MyGetVolley(getApplicationContext());
        LatLng latLngOne = new LatLng(23.873751,90.396454);
        LatLng latLngTwo = new LatLng(23.746466,90.376015);
        myGetVolley.apply(latLngOne,latLngTwo);
    }
}
