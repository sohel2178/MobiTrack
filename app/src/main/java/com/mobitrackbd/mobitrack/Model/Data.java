package com.mobitrackbd.mobitrack.Model;

/**
 * Created by IMATPC-12 on 20-Feb-18.
 */

public class Data {
    private long startTime;
    private long endTime;
    private double distance;

    public Data() {
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getAverageSpeed(){
        double speed = distance*1000/(endTime-startTime)*3.6;
        return speed;
    }
}
