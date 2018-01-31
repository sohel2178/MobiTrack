package com.mobitrackbd.mobitrack.Model;

/**
 * Created by Sohel on 1/31/2018.
 */

public class Vehicle {

    private int id;
    private double lat;
    private double lng;

    public Vehicle() {
    }

    public Vehicle(int id, double lat, double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
