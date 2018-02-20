package com.mobitrackbd.mobitrack.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobitrackbd.mobitrack.R;

public class TripReportActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_report);
        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setTitle("Trip Report");
    }
}
