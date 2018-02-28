package com.mobitrackbd.mobitrack.Activities;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Location;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.mobitrackbd.mobitrack.Model.MyLatLong;
import com.mobitrackbd.mobitrack.R;
import com.mobitrackbd.mobitrack.Utility.URL;
import com.mobitrackbd.mobitrack.Volley.MyPostVolley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapAnimationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    private List<MyLatLong> directionList;

    private List<LatLng> latLongList;

    private MyLatLong firstLatLong;
    private Marker mMarker;

    private PolylineOptions polylineOptions,darkPolyLineOption;

    private Polyline grayPolyLine,darkPolyLine;

    private Handler handler;

    private int index,next;

    private LatLng startPosition,endPosition;
    private float v;
    private double lat,lng;


    private static final int THRESHOLD=120;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_animation);

        latLongList = new ArrayList<>();

        //directionList = getIntent().getBundleExtra("datalist");
        if(getIntent() != null){
            directionList = (List<MyLatLong>) getIntent().getSerializableExtra("Data");
            firstLatLong = directionList.get(0);


            Location firstLoc = new Location("");
            Location nextLoc = new Location("");


            for(MyLatLong x: directionList){

                if(directionList.indexOf(x)==0){
                    firstLoc.setLatitude(x.getLat());
                    firstLoc.setLongitude(x.getLng());
                    latLongList.add(new LatLng(x.getLat(),x.getLng()));
                }else{
                    nextLoc.setLatitude(x.getLat());
                    nextLoc.setLongitude(x.getLng());

                    if(firstLoc.distanceTo(nextLoc)>=THRESHOLD){
                        latLongList.add(new LatLng(x.getLat(),x.getLng()));
                        firstLoc.set(nextLoc);
                    }
                }


            }

            latLongList.add(new LatLng(directionList.get(directionList.size()-1).getLat(),directionList.get(directionList.size()-1).getLng()));


        }

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
        mMap.setTrafficEnabled(false);
        mMap.setIndoorEnabled(false);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);

        //gotoFirstLatLong();

        animate();

    }

    private void gotoFirstLatLong() {
        MarkerOptions option;

        LatLng latLng = new LatLng(firstLatLong.getLat(),firstLatLong.getLng());

        option = new MarkerOptions().position(latLng).title("Vehicle Location");

        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(30).tilt(30).build();
        /*mMarker = mMap.addMarker(option);
        mMarker.setIcon(BitmapDescriptorFactory.fromBitmap(getCarBitmap(64,64)));*/
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


    private void animate(){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng x : latLongList) {
            builder.include(x);
        }

        LatLngBounds bounds = builder.build();
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
        mMap.animateCamera(mCameraUpdate);

        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.GRAY);
        polylineOptions.width(5);
        polylineOptions.startCap(new SquareCap());
        polylineOptions.endCap(new SquareCap());
        polylineOptions.jointType(JointType.ROUND);
        polylineOptions.addAll(latLongList);

        grayPolyLine = mMap.addPolyline(polylineOptions);

        darkPolyLineOption = new PolylineOptions();
        darkPolyLineOption.width(10);
        darkPolyLineOption.color(Color.RED);
        darkPolyLineOption.startCap(new SquareCap());
        darkPolyLineOption.endCap(new SquareCap());
        darkPolyLineOption.jointType(JointType.ROUND);

        darkPolyLine = mMap.addPolyline(darkPolyLineOption);

        // Add a Marker at the Start of the Path
        mMap.addMarker(new MarkerOptions()
                .position(latLongList.get(0)));

      /*  // Add a Marker at the End of the Path
        mMap.addMarker(new MarkerOptions()
                .position(latLongList.get(latLongList.size() - 1)));*/


        // Poly Line Animator
        ValueAnimator polylineAnimator = ValueAnimator.ofInt(0, 100);
        polylineAnimator.setDuration(60000);
        polylineAnimator.setInterpolator(new LinearInterpolator());
        polylineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                List<LatLng> points = grayPolyLine.getPoints();
                int percentValue = (int) valueAnimator.getAnimatedValue();
                int size = points.size();
                int newPoints = (int) (size * (percentValue / 100.0f));
                List<LatLng> p = points.subList(0, newPoints);
                darkPolyLine.setPoints(p);

                if(valueAnimator.getAnimatedFraction()==1){
                    mMap.addMarker(new MarkerOptions()
                            .position(latLongList.get(latLongList.size() - 1)));
                }
            }
        });


        polylineAnimator.start();


        mMarker = mMap.addMarker(new MarkerOptions().position(latLongList.get(0))
                .flat(true)
                .icon(BitmapDescriptorFactory.fromBitmap(getCarBitmap(64,64))));


        handler = new Handler();

        index = -1;
        next = 1;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index < latLongList.size() - 1) {
                    index++;
                    next = index + 1;
                }
                if (index < latLongList.size() - 1) {
                    startPosition = latLongList.get(index);
                    endPosition = latLongList.get(next);
                }
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setDuration(3000);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        v = valueAnimator.getAnimatedFraction();
                        lng = v * endPosition.longitude + (1 - v)* startPosition.longitude;
                        lat = v *endPosition.latitude + (1 - v) *startPosition.latitude;
                        LatLng newPos = new LatLng(lat, lng);
                        mMarker.setPosition(newPos);
                        mMarker.setAnchor(0.5f, 0.5f);
                        mMarker.setRotation(getBearing(startPosition, newPos));


                        if(index==0){
                            mMap.moveCamera(CameraUpdateFactory
                                    .newCameraPosition
                                            (new CameraPosition.Builder()
                                                    .target(newPos)
                                                    .zoom(15.5f)
                                                    .build()));
                        }

                        // Animate Camera Every 6 iteration
                        /*if(next>6){
                            if(next%6==0){
                                mMap.moveCamera(CameraUpdateFactory
                                        .newCameraPosition
                                                (new CameraPosition.Builder()
                                                        .target(newPos)
                                                        .zoom(15.5f)
                                                        .build()));
                            }

                        }else{
                            if(next%2==0){
                                mMap.moveCamera(CameraUpdateFactory
                                        .newCameraPosition
                                                (new CameraPosition.Builder()
                                                        .target(newPos)
                                                        .zoom(15.5f)
                                                        .build()));
                            }

                        }*/

                    }
                });
                valueAnimator.start();

                // Finish Anim
                if(next!=(latLongList.size()-1)){
                    handler.postDelayed(this, 2000);
                }


            }
        }, 5000);
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







}
