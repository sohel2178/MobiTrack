package com.mobitrackbd.mobitrack.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobitrackbd.mobitrack.R;

public class FuelConsumActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_consum);
        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setTitle("Fuel Consumption Report");
    }
}
