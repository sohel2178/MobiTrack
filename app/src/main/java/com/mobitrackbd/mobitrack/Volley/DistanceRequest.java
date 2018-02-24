package com.mobitrackbd.mobitrack.Volley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.mobitrackbd.mobitrack.Listener.TravelDistanceListener;
import com.mobitrackbd.mobitrack.Model.Data;
import com.mobitrackbd.mobitrack.Model.DeviceLatLong;
import com.mobitrackbd.mobitrack.R;
import com.mobitrackbd.mobitrack.Response.SummaryReportResponse;
import com.mobitrackbd.mobitrack.Utility.MyUtil;
import com.mobitrackbd.mobitrack.Utility.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IMATPC-12 on 20-Feb-18.
 */

public class DistanceRequest {
    private static final String WEB_API_KEY="AIzaSyAbXl5sEkZxVmiLBrvSgHkhwkuZJV1Wn0k";
    //private static final String WEB_API_KEY="AIzaSyA-bYTLzcDgXrH3a2-fPHppu9vsNkZCGDk";

    private Activity activity;
    private Map<String,String> params;
    private String url = URL.SUMMARY_REPORT;
    private ProgressDialog mProgressDialog;
    private Gson gson;
    private int counter,size,processCounter;

    private List<Data> travelDistanceList;

    private TravelDistanceListener travelDistanceListener;

    public DistanceRequest(Activity activity, Map<String,String> params,TravelDistanceListener travelDistanceListener){
        this.activity=activity;
        this.params = params;
        this.travelDistanceListener = travelDistanceListener;
        gson = new Gson();
        travelDistanceList = new ArrayList<>();
        applyPostVolley();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {

            mProgressDialog = new ProgressDialog(activity, R.style.MyTheme);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large_Inverse);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void applyPostVolley(){

        showProgressDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("MYDDD",response);

                                                //hideProgressDialog();

                        SummaryReportResponse summaryReportResponse = gson.fromJson(response,SummaryReportResponse.class);

                        if(summaryReportResponse.getLatlongs() != null && summaryReportResponse.getLatlongs().size() > 0){
                            List<DeviceLatLong> processList = getProcessList(summaryReportResponse.getLatlongs());
                            size = processList.size()-1;

                            if(size>0){
                                MyThread myThread = new MyThread(processList);
                            }else{
                                hideProgressDialog();
                            }

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        hideProgressDialog();

                        Toast.makeText(activity, "Problem in Fetching Data", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(postRequest);
    }

    public void requsetForDirection(DeviceLatLong origin, DeviceLatLong destination, final int index){

        counter++;


        this.url = buildRequestUrl(origin,destination);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");

                    if(status.equals("OK")){
                        processResponse(response,index);
                    }

                    if(counter == size){
                        hideProgressDialog();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Log.d("HHH",response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();

            }
        });

        Volley.newRequestQueue(activity).add(jsonObjectRequest);

    }

    private void processResponse(JSONObject response, int index) {
        processCounter++;
        try {
            JSONArray jsonArray = response.getJSONArray("routes");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject route = jsonArray.getJSONObject(i);
                JSONObject poly = route.getJSONObject("overview_polyline");
                JSONObject leg = route.getJSONArray("legs").getJSONObject(0);

                //Todo Work on Next
                //Log.d("WWWW",leg.getJSONObject("distance").getDouble("value")+"");

                //Log.d("GGGGGGGG","Travel Distance: "+index+" "+leg.getJSONObject("distance").getDouble("value")+"");

                travelDistanceList.get(index).setDistance(leg.getJSONObject("distance").getDouble("value"));


                String polyline = poly.getString("points");

                List<LatLng> polyLines = decodePoly(polyline);


            }
            if(processCounter == size){

                if(travelDistanceListener != null){
                    travelDistanceListener.getTravelDistanceList(travelDistanceList);
                }


                //Log.d("MMMMMM",travelDistanceList.size()+"");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }


    private List<DeviceLatLong> getProcessList(List<DeviceLatLong> latLongList) {

        List<DeviceLatLong> retList= new ArrayList<>();

        DeviceLatLong start = latLongList.get(0);

        double distance=0;


        for ( int i=0 ;i<latLongList.size();i++){

            if(getDistance(start,latLongList.get(i))==0){
                retList.add(latLongList.get(i));
                distance=getDistance(start,latLongList.get(i)); // Here it is Zero
            }else {
                if(getDistance(start,latLongList.get(i))>=distance){
                    distance=getDistance(start,latLongList.get(i));
                }else {
                    retList.add(latLongList.get(i-1));
                    start = latLongList.get(i-1);
                    distance=0;
                    //
                }
            }



        }

        // If Last Location is not Contain on the List.. Add it
        if(!retList.contains(latLongList.get(latLongList.size()-1))){
            retList.add(latLongList.get(latLongList.size()-1));
        }



        return retList;

    }

    private double getDistance(DeviceLatLong begin, DeviceLatLong end){

        Location loc1 = new Location("");
        loc1.setLatitude(Double.parseDouble(begin.getLatitude()));
        loc1.setLongitude(Double.parseDouble(begin.getLongitude()));

        Location loc2 = new Location("");
        loc2.setLatitude(Double.parseDouble(end.getLatitude()));
        loc2.setLongitude(Double.parseDouble(end.getLongitude()));

        return loc1.distanceTo(loc2);

    }

    private String buildRequestUrl(DeviceLatLong origin, DeviceLatLong destination) {
        String requestUrl = "https://maps.googleapis.com/maps/api/directions/json?" +
                "mode=driving&"
                + "transit_routing_preference=less_driving&"
                + "origin=" + origin.getLatitude() + "," + origin.getLongitude() + "&"
                + "destination=" + destination.getLatitude() + "," + destination.getLongitude() +"&"
                + "key=" + WEB_API_KEY;

        return requestUrl;
    }


    class MyThread extends Thread{

        List<DeviceLatLong> processList;

        private MyThread(List<DeviceLatLong> processList){
            this.processList = processList;
            this.start();

        }


        @Override
        public void run() {
            super.run();

            DeviceLatLong start = null;
            DeviceLatLong next = null;

            for(int i = 0; i<processList.size(); i++){
                if(i == 0){
                    start = processList.get(i);
                }else{
                    next = processList.get(i);

                    Data travelDistance = new Data();
                    travelDistance.setStartTime(MyUtil.getTimeInMilis(start.getServertime()));
                    travelDistance.setEndTime(MyUtil.getTimeInMilis(next.getServertime()));

                    travelDistanceList.add(travelDistance);
                    requsetForDirection(start, next, i-1);
                    start = next;
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }
    }
}
