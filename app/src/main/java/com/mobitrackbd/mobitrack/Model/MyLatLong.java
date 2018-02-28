package com.mobitrackbd.mobitrack.Model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by IMATPC-12 on 27-Feb-18.
 */

public class MyLatLong implements Serializable {
    private double lat;
    private double lng;

    public MyLatLong() {
    }

    public MyLatLong(LatLng latLng) {
        this.lat = latLng.latitude;
        this.lng = latLng.longitude;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
