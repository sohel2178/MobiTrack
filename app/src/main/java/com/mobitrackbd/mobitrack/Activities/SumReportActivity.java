package com.mobitrackbd.mobitrack.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.maps.model.LatLng;
import com.mobitrackbd.mobitrack.Adapter.DistanceAdapter;
import com.mobitrackbd.mobitrack.Chart.MyValueFormatter;
import com.mobitrackbd.mobitrack.Listener.TravelDistanceListener;
import com.mobitrackbd.mobitrack.Model.Data;
import com.mobitrackbd.mobitrack.Model.DeviceLatLong;
import com.mobitrackbd.mobitrack.Model.MyLatLong;
import com.mobitrackbd.mobitrack.Model.Span;
import com.mobitrackbd.mobitrack.R;
import com.mobitrackbd.mobitrack.Utility.DividerItemDecoration;
import com.mobitrackbd.mobitrack.Utility.MyUtil;
import com.mobitrackbd.mobitrack.Volley.DistanceRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SumReportActivity extends BaseActivity implements  View.OnClickListener,TravelDistanceListener {

    private TextView tvDate;
    private ImageView ivCalender;
    private RecyclerView rvDistance;
    private String deviceId;
    private double total;

    private TextView tvTotalDistance;

    private ImageView ivPlayAnimation;

    private Date selectedDate;

    private DistanceAdapter adapter;

    private ScrollView svContent;

    private BarChart barChart;

    private List<DeviceLatLong> tripReportList;

    private String[] hourArr;

    private com.github.clans.fab.FloatingActionButton fabViewInMap,fabTripReport;

    private int[] myColors = {R.color.chart_color_1,R.color.chart_color_2,R.color.chart_color_3,
            R.color.chart_color_4,R.color.chart_color_5,R.color.chart_color_6};
    public static final int[] MY_COLOR = {
            Color.rgb(255, 204, 204), Color.rgb(255, 127, 127), Color.rgb(255, 50, 50),
            Color.rgb(204, 0, 0), Color.rgb(153, 0, 0)
    };


    private List<LatLng> directionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_report);

        hourArr = getResources().getStringArray(R.array.hour_array);

        tvTotalDistance = findViewById(R.id.test);
        deviceId = getIntent().getStringExtra("deviceid");
        total = 0;
        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setTitle("History");

        adapter = new DistanceAdapter(getApplicationContext());

        selectedDate = new Date();

        initView();

        getDataFromOnline();


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



    private void getDataFromOnline(){
        // Invisible Content before Fetching Data
        svContent.setVisibility(View.INVISIBLE);

        Map<String, String> param = new HashMap<>();
        param.put("deviceid",deviceId);
        param.put("date",MyUtil.getStringDateTwo(selectedDate));

//        Log.d("HHHH",deviceId);
//        Log.d("HHHH",MyUtil.getStringDateTwo(selectedDate));


        DistanceRequest distanceRequest = new DistanceRequest(this, param,this);
    }

    private void initView() {
        svContent = findViewById(R.id.content);

        barChart = findViewById(R.id.bar_chart);

        tvDate = findViewById(R.id.tv_date);
        ivCalender = findViewById(R.id.iv_calender);
        ivCalender.setOnClickListener(this);

        tvDate.setText("24 Hours Report on "+MyUtil.getStringDate(selectedDate));

        rvDistance = findViewById(R.id.rv_travel_distance);
        rvDistance.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rvDistance.setNestedScrollingEnabled(false);

        //Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.divider_black);
        //rvDistance.addItemDecoration(new android.support.v7.widget.DividerItemDecoration(getApplicationContext(), android.support.v7.widget.DividerItemDecoration.VERTICAL));
        //rvDistance.addItemDecoration(new DividerItemDecoration(drawable));
        rvDistance.setAdapter(adapter);

        ivPlayAnimation = findViewById(R.id.play_animation);
        ivPlayAnimation.setOnClickListener(this);

        fabTripReport = findViewById(R.id.menu_item_trip_report);
        fabViewInMap = findViewById(R.id.menu_item_view_in_map);

        fabTripReport.setOnClickListener(this);
        fabViewInMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_calender:
                openCalender();
                break;
            case R.id.menu_item_trip_report:
                Intent intentTripReport = new Intent(getApplicationContext(),TripReportActivity.class);
                if(tripReportList != null && tripReportList.size() > 2){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Data", (Serializable) tripReportList);
                    intentTripReport.putExtras(bundle);
                    intentTripReport.putExtra("Date",MyUtil.getStringDate(selectedDate));
                    startActivity(intentTripReport);
                }else{
                    Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menu_item_view_in_map:
                if(directionList != null && directionList.size()>0){
                    List<MyLatLong> myLatLongList = new ArrayList<>();
                    for (LatLng x:directionList){
                        myLatLongList.add(new MyLatLong(x));
                    }

                    Intent animIntent = new Intent(getApplicationContext(), MapAnimationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Data", (Serializable) myLatLongList);
                    animIntent.putExtras(bundle);
                    startActivity(animIntent);
                }
                break;
            case R.id.play_animation:

                if(directionList!=null && directionList.size()>0){
                    Intent intent = new Intent(getApplicationContext(),MapAnimationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("datalist", (ArrayList<? extends Parcelable>) directionList);
                    startActivity(intent);
                }


                break;
        }
    }

    private void openCalender() {
        DatePickerBuilder builder = new DatePickerBuilder(this, new OnSelectDateListener() {
            @Override
            public void onSelect(List<Calendar> calendar) {
                tvDate.setText("24 Hours Report on "+MyUtil.getStringDate(calendar.get(0).getTime()));

                selectedDate = calendar.get(0).getTime();
                getDataFromOnline();
            }
        }).pickerType(CalendarView.ONE_DAY_PICKER).date(Calendar.getInstance());

        DatePicker datePicker = builder.build();
        datePicker.show();
    }





    private float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }

    private float getBearing2(DeviceLatLong begin, DeviceLatLong end) {
        double lat = Math.abs(Double.parseDouble(begin.getLatitude()) - Double.parseDouble(end.getLatitude()));
        double lng = Math.abs(Double.parseDouble(begin.getLongitude()) - Double.parseDouble(end.getLongitude()));

        if (Double.parseDouble(begin.getLatitude())< Double.parseDouble(end.getLatitude()) && Double.parseDouble(begin.getLongitude()) < Double.parseDouble(end.getLongitude()))
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (Double.parseDouble(begin.getLatitude()) >= Double.parseDouble(end.getLatitude()) && Double.parseDouble(begin.getLongitude()) < Double.parseDouble(end.getLongitude()))
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (Double.parseDouble(begin.getLatitude()) >= Double.parseDouble(end.getLatitude()) && Double.parseDouble(begin.getLongitude()) >= Double.parseDouble(end.getLongitude()))
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (Double.parseDouble(begin.getLatitude()) < Double.parseDouble(end.getLatitude()) && Double.parseDouble(begin.getLongitude()) >= Double.parseDouble(end.getLongitude()))
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }


    @Override
    public void getTravelDistanceList(List<Data> dataList) {

        processDataList(dataList);

        /*for(Data x: dataList){
            Log.d("DDDDD",x.getDistance()+"");
        }*/



    }

    @Override
    public void getDirectionList(List<LatLng> latLngList) {

        directionList = latLngList;
    }

    @Override
    public void getDeviceLatLong(List<DeviceLatLong> deviceLatLongList) {
        tripReportList = deviceLatLongList;
        Log.d("MMMMMM",deviceLatLongList.size()+"");
        
    }

    private void processDataList(List<Data> dataList) {
        // VIsibl Content after Fetching Data
        svContent.setVisibility(View.VISIBLE);

        List<Span> spanList = MyUtil.getSpanList(selectedDate);

       /* for(Span x: spanList){
            Log.d("JJJ",x.getSpanNo()+"");
        }*/

        for (Data x: dataList){

            Map<String,Span> spanMap = getFirstAndLastSpan(spanList,x);

            Span startSpan = spanMap.get("start");
            Span endSpan = spanMap.get("end");

            if(startSpan.getSpanNo()==endSpan.getSpanNo()){
                spanList.get(startSpan.getSpanNo()).addDistance(x.getDistance());

            }else{

                double avgSpeed= x.getAverageSpeed();

                if(avgSpeed<=2){
                    spanList.get(endSpan.getSpanNo()).addDistance(x.getDistance());
                }else{

                    double totalFactor = (x.getEndTime()-x.getStartTime())/3600000.0;



                    for(int i = startSpan.getSpanNo();i<=endSpan.getSpanNo();i++){
                        double factor=0;
                        if(i==startSpan.getSpanNo()){
                             factor = (startSpan.getEndTime()-x.getStartTime())/3600000.0;
                        }else if(i==endSpan.getSpanNo()){
                             factor = (x.getEndTime()-endSpan.getStartTime())/3600000.0;
                        }else{
                            factor = 1;
                        }

                        spanList.get(i).addDistance(x.getDistance()*factor);


                    }

                }



            }

        }


        adapter.clear();

        double distance =0;
        for(Span x: spanList){
            //Log.d("ATIKKKKK","Travel Distance On Hour "+(x.getSpanNo()+1) +" is "+x.getDistance());
            adapter.addSpan(x);
            distance = distance+x.getDistance();
        }

        tvTotalDistance.setText("Total Distance Travel "+MyUtil.getTwoDecimalFormat(distance/1000)+" KM");


        processForChart(spanList);




    }

    private void processForChart(List<Span> spanList) {

        BarDataSet set = getBarDataSet(spanList);
        List<String> labels = getLabels(spanList);
        set.setColors(MY_COLOR);
        set.setValueTextSize(10);

        BarData data = new BarData(labels,set);
        data.setValueFormatter(new MyValueFormatter());


        barChart.setMaxVisibleValueCount(24);


        barChart.setPinchZoom(false);

        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);

        barChart.getAxis(YAxis.AxisDependency.RIGHT).setDrawAxisLine(false);
        barChart.getAxis(YAxis.AxisDependency.RIGHT).setDrawLabels(false);

        barChart.animateXY(1000,1000);

        barChart.getLegend().setEnabled(false);

        barChart.setDescription("");

        barChart.setData(data);// make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh



    }

    private List<String> getLabels(List<Span> spanList) {
        List<String> retList = new ArrayList<>();

        for(Span x : spanList){
            retList.add(String.valueOf(x.getSpanNo()+1));
        }

        return retList;
    }

    private BarDataSet getBarDataSet(List<Span> spanList){
        List<BarEntry> barEntryList = new ArrayList<>();

        for(Span x: spanList){
            BarEntry barEntry = new BarEntry((float) x.getDistance()/1000,spanList.indexOf(x));
            barEntryList.add(barEntry);
        }

        return new BarDataSet(barEntryList,"BarDataSet");
    }

    private Map<String,Span> getFirstAndLastSpan(List<Span> spanList, Data x) {
        Map<String,Span> map = new HashMap<>();

        for(Span y: spanList){

            if(x.getEndTime()<=y.getEndTime()){
                map.put("end",y);

                long stTime = x.getStartTime();

                for (Span z: spanList){

                    if(stTime<=z.getEndTime()){
                        map.put("start",z);
                        break;
                    }
                }
                break;
            }
        }


        return map;

    }

}
