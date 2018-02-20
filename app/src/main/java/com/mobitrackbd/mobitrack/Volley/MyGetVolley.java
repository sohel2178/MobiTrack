package com.mobitrackbd.mobitrack.Volley;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.mobitrackbd.mobitrack.Listener.DistanceListener;
import com.mobitrackbd.mobitrack.Model.DeviceLatLong;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genius 03 on 7/23/2017.
 */

public class MyGetVolley {
    private static final String WEB_API_KEY="AIzaSyA-bYTLzcDgXrH3a2-fPHppu9vsNkZCGDk";
    //private static final String WEB_API_KEY="AIzaSyDpIfAvz9xaXCiBueo3SNYOj6rgqvRJxFk";
    private static final String ROOT_URL="https://maps.googleapis.com/maps/api/directions/json?";

    private Context context;
    private String url;
    private VolleyGetListener listener;

    private DistanceListener distanceListener;


    public MyGetVolley(Context context) {
        this.context = context;
    }

    public void setVollyGetListener(VolleyGetListener listener){
        this.listener=listener;
    }

    public void setDistanceListener(DistanceListener distanceListener){
        this.distanceListener = distanceListener;
    }


    public void apply(Location origin, Location destination){

        this.url = buildRequestUrl(origin,destination);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");
                    Log.d("HHH",status);

                    if(status.equals("OK")){
                        processResponse(response);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Log.d("HHH",response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(context).add(jsonObjectRequest);

    }

    public void apply(LatLng origin, LatLng destination){

        this.url = buildRequestUrl(origin,destination);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");
                    Log.d("HHH",status);

                    if(status.equals("OK")){
                        processResponse(response);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Log.d("HHH",response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(context).add(jsonObjectRequest);

    }

    public void apply(DeviceLatLong origin, DeviceLatLong destination){

        this.url = buildRequestUrl(origin,destination);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");
                    Log.d("HHH",status);

                    if(status.equals("OK")){
                        processResponse(response);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Log.d("HHH",response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(context).add(jsonObjectRequest);

    }

    private void processResponse(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray("routes");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject route = jsonArray.getJSONObject(i);
                JSONObject poly = route.getJSONObject("overview_polyline");
                JSONObject leg = route.getJSONArray("legs").getJSONObject(0);

                //Todo Work on Next
                Log.d("WWWW",leg.getJSONObject("distance").getDouble("value")+"");

                if(distanceListener!=null){
                    distanceListener.getDistance(leg.getJSONObject("distance").getDouble("value"));
                }

                String polyline = poly.getString("points");

                List<LatLng> polyLines = decodePoly(polyline);

                if(listener!=null){
                    listener.response(polyLines);
                }
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


    public interface VolleyGetListener{
        public void response(List<LatLng> data);
    }


    private String buildRequestUrl(Location origin,Location destination) {
        String requestUrl = "https://maps.googleapis.com/maps/api/directions/json?" +
                "mode=driving&"
                + "transit_routing_preference=less_driving&"
                + "origin=" + origin.getLatitude() + "," + origin.getLongitude() + "&"
                + "destination=" + destination.getLatitude() + "," + destination.getLongitude() +"&"
                + "key=" + WEB_API_KEY;

        return requestUrl;
    }

    private String buildRequestUrl(LatLng origin,LatLng destination) {
        String requestUrl = "https://maps.googleapis.com/maps/api/directions/json?" +
                "mode=driving&"
                + "transit_routing_preference=less_driving&"
                + "origin=" + origin.latitude + "," + origin.longitude + "&"
                + "destination=" + destination.latitude + "," + destination.longitude +"&"
                + "key=" + WEB_API_KEY;

        return requestUrl;
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
}
