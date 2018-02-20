package com.mobitrackbd.mobitrack.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.google.android.gms.maps.model.LatLng;
import com.mobitrackbd.mobitrack.Listener.TravelDistanceListener;
import com.mobitrackbd.mobitrack.Model.Data;
import com.mobitrackbd.mobitrack.Model.DeviceLatLong;
import com.mobitrackbd.mobitrack.Model.Span;
import com.mobitrackbd.mobitrack.R;
import com.mobitrackbd.mobitrack.Utility.MyUtil;
import com.mobitrackbd.mobitrack.Volley.DistanceRequest;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SumReportActivity extends BaseActivity implements View.OnClickListener,TravelDistanceListener {

    private TextView tvDate;
    private ImageView ivCalender;
    private String deviceId;
    private double total;

    private TextView tvTest;

    private Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_report);
        tvTest = findViewById(R.id.test);
        deviceId = getIntent().getStringExtra("deviceid");
        total = 0;
        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setTitle("24Hrs Summary Report");

        initView();

        selectedDate = new Date();

        Map<String, String> param = new HashMap<>();
        param.put("deviceid",deviceId);
        param.put("date",MyUtil.getStringDateTwo(selectedDate));

        DistanceRequest distanceRequest = new DistanceRequest(this, param,this);
    }

    private void initView() {
        tvDate = findViewById(R.id.tv_date);
        ivCalender = findViewById(R.id.iv_calender);
        ivCalender.setOnClickListener(this);

        tvDate.setText("24 Hours Report on "+MyUtil.getStringDate(new Date()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_calender:
                openCalender();
                break;
        }
    }

    private void openCalender() {
        DatePickerBuilder builder = new DatePickerBuilder(this, new OnSelectDateListener() {
            @Override
            public void onSelect(List<Calendar> calendar) {
                tvDate.setText("24 Hours Report on "+MyUtil.getStringDate(calendar.get(0).getTime()));
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

    private void processDataList(List<Data> dataList) {

        List<Span> spanList = MyUtil.getSpanList(selectedDate);

       /* for(Span x: spanList){
            Log.d("JJJ",x.getSpanNo()+"");
        }*/

        for (Data x: dataList){

            if(dataList.indexOf(x)==0){
                Log.d("JJJJJJJJJJJ",x.getStartTime()+"");
                Log.d("JJJJJJJJJJJ",x.getEndTime()+"");
                Log.d("JJJJJJJJJJJ",x.getDistance()+"");
            }

            Map<String,Span> spanMap = getFirstAndLastSpan(spanList,x);

            Span startSpan = spanMap.get("start");
            Span endSpan = spanMap.get("end");

            if(startSpan.getSpanNo()==endSpan.getSpanNo()){
                spanList.get(startSpan.getSpanNo()).addDistance(x.getDistance());

                Log.d("HHHHHHHHHH","Avg Speed "+x.getAverageSpeed());

            }else{
               // Todo Do in Next

                int numberOfSpan = endSpan.getSpanNo()-startSpan.getSpanNo()+1;

                double avgSpeed= x.getAverageSpeed();

                Log.d("TTTTTTTTT","Avg Speed: "+avgSpeed);
                Log.d("TTTTTTTTT","Pan Length: "+numberOfSpan);


            }

            Log.d("JJJ",spanMap.get("start").getSpanNo()+"");
            Log.d("JJJ",spanMap.get("end").getSpanNo()+"");
        }


        for (Span ww: spanList){
            //Log.d("HHHHHHHHHH","Span No = "+ww.getSpanNo()+" Travel Distance = "+ww.getDistance()+"");
            Log.d("QQQQQQQ",ww.getStartTime()+"");
        }

        for (Span ww: spanList){
            //Log.d("HHHHHHHHHH","Span No = "+ww.getSpanNo()+" Travel Distance = "+ww.getDistance()+"");
            Log.d("RRRRRRRRRRRRRRR",ww.getEndTime()+"");
        }



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
