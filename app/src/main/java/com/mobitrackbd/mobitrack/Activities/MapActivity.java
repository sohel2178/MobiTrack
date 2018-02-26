package com.mobitrackbd.mobitrack.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.mobitrackbd.mobitrack.Listener.VolleyPostListener;
import com.mobitrackbd.mobitrack.MarkerAnimation.LatLngInterpolator;
import com.mobitrackbd.mobitrack.MarkerAnimation.MarkerAnimation;
import com.mobitrackbd.mobitrack.Model.BoomIcon;
import com.mobitrackbd.mobitrack.Model.MyLocation;
import com.mobitrackbd.mobitrack.Model.Vehicle;
import com.mobitrackbd.mobitrack.R;
import com.mobitrackbd.mobitrack.Response.LastLocationResponse;
import com.mobitrackbd.mobitrack.Service.LocationService;
import com.mobitrackbd.mobitrack.Service.MyService;
import com.mobitrackbd.mobitrack.Utility.URL;
import com.mobitrackbd.mobitrack.Volley.MyPostVolley;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,VolleyPostListener,
        View.OnClickListener{

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    private String vehicleId;
    private String deviceId;

    private Marker mMarker;

    private Intent serviceIntent;

    private Gson gson;

    private LatLng currentLocation;

    private FloatingActionButton fabReport;

    private int[] iconArray = new int[]{R.drawable.summary_report,R.drawable.trip_report,R.drawable.distance_report,R.drawable.fuel_consumtion};


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            MyLocation myLocation = (MyLocation) intent.getSerializableExtra("my_location");

            LatLng latLng = new LatLng(Double.parseDouble(myLocation.getLatitude()),Double.parseDouble(myLocation.getLongitude()));

            if(getDistance(latLng,currentLocation)>10){
                MarkerAnimation.animateMarkerToGB(mMarker, latLng, new LatLngInterpolator.Spherical());

                /*CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(18).tilt(30).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/

                currentLocation = latLng;
            }


        }



    };

    private double getDistance(LatLng latLng1,LatLng latLng2){
        Location loc1 = new Location("");
        loc1.setLatitude(latLng1.latitude);
        loc1.setLongitude(latLng1.longitude);

        Location loc2 = new Location("");
        loc2.setLatitude(latLng2.latitude);
        loc2.setLongitude(latLng2.longitude);

        float distanceInMeters = loc1.distanceTo(loc2);

        return distanceInMeters;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        vehicleId = getIntent().getStringExtra("vehicle_id");
        deviceId = getIntent().getStringExtra("device_id");
        serviceIntent = new Intent(getApplicationContext(), MyService.class);
        serviceIntent.putExtra("device_id",deviceId);
        gson = new Gson();


        fabReport = findViewById(R.id.fab_report);

        fabReport.setOnClickListener(this);


        //vehicle = new Vehicle(1,23.746466,90.376015);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //polyLineList = new ArrayList<>();

        mapFragment.getMapAsync(MapActivity.this);
    }


    private void startActivity(int index){
        switch (index){
            case 0:
                Intent intent = new Intent(getApplicationContext(),SumReportActivity.class);
                intent.putExtra("deviceid",deviceId);
                startActivity(intent);

                break;

            case 1:
                startActivity(new Intent(getApplicationContext(),TripReportActivity.class));
                break;

            case 2:
                startActivity(new Intent(getApplicationContext(),DistanceReportActivity.class));
                break;

            case 3:
                startActivity(new Intent(getApplicationContext(),FuelConsumActivity.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(serviceIntent);

        IntentFilter intentFilter= new IntentFilter();
        intentFilter.addAction(getPackageName());
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        stopService(serviceIntent);

        unregisterReceiver(broadcastReceiver);
        super.onStop();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(false);
        mMap.setBuildingsEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);


        Map<String,String> map = new HashMap<>();
        map.put("device_id", deviceId);

        MyPostVolley myPostVolley = new MyPostVolley(this, map, URL.LASTLOC_REQ,this);



        //gotoVehicleLocation();

    }

    private void gotoVehicleLocation() {
        LatLng latLng = new LatLng(23.746466,90.376015);

        MarkerOptions option = new MarkerOptions().position(latLng).title("Vehicle Location");
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).tilt(30).build();
        mMarker = mMap.addMarker(option);
        mMarker.setIcon(BitmapDescriptorFactory.fromBitmap(getCarBitmap(96,96)));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    private void gotoVehicleLocation(LatLng latLng) {
        MarkerOptions option;

        if(getAddress(latLng)!=null){
             option = new MarkerOptions().position(latLng).title(getAddress(latLng));
        }else{
             option = new MarkerOptions().position(latLng).title("Vehicle Location");
        }


        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(18).tilt(30).build();
        mMarker = mMap.addMarker(option);
        mMarker.setIcon(BitmapDescriptorFactory.fromBitmap(getCarBitmap(64,64)));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }


    private Bitmap getCarBitmap(int newWidth, int newHeight) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_car);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return resizedBitmap;
    }

    @Override
    public void getRespose(String response) {
        LastLocationResponse lastLocationResponse = gson.fromJson(response,LastLocationResponse.class);
        if(lastLocationResponse.getSuccess()==1){
            MyLocation myLocation = lastLocationResponse.getLocation();

            currentLocation = new LatLng(Double.parseDouble(myLocation.getLatitude()),Double.parseDouble(myLocation.getLongitude()));

            gotoVehicleLocation(currentLocation);
        }
    }


    private String getAddress(LatLng latLng){
        String address = null;
        Geocoder geocoder  =new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            address = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }

    @Override
    public void onClick(View view) {
        startActivity(0);
    }
}
