package com.mobitrackbd.mobitrack.Model;

/**
 * Created by IMATPC-12 on 26-Feb-18.
 */

public class LocationSpan {
    private int spanNumber;
    private long startTime, endTtime;
    private double lat, lng;

    public LocationSpan() {
    }

    public LocationSpan(int spanNumber) {
        this.spanNumber = spanNumber;
    }

    public int getSpanNumber() {
        return spanNumber;
    }

    public void setSpanNumber(int spanNumber) {
        this.spanNumber = spanNumber;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTtime() {
        return endTtime;
    }

    public void setEndTime(long endTtime) {
        this.endTtime = endTtime;
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
