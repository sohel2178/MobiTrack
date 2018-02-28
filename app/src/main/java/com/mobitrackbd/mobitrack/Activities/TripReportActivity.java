package com.mobitrackbd.mobitrack.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.mobitrackbd.mobitrack.Adapter.TripReportAdapter;
import com.mobitrackbd.mobitrack.Model.DeviceLatLong;
import com.mobitrackbd.mobitrack.R;

import java.util.List;

public class TripReportActivity extends BaseActivity {

    private List<DeviceLatLong> tripReportList;
    private RecyclerView rvTripReport;
    private TextView tvTitle;
    private String date;

    private TripReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_report);
        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setTitle("Trip Report");

        if(getIntent() != null){
            tripReportList = (List<DeviceLatLong>) getIntent().getSerializableExtra("Data");
            date = getIntent().getStringExtra("Date");
            adapter = new TripReportAdapter(getApplicationContext(), tripReportList);
        }

        initView();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if(id==android.R.id.home){
            onBackPressed();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        rvTripReport = findViewById(R.id.rv_trip_report);
        tvTitle = findViewById(R.id.title);
        tvTitle.setText("Trip Report on "+ date);
        rvTripReport.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        if(adapter != null){
            rvTripReport.setAdapter(adapter);
        }
    }

}
