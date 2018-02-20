package com.mobitrackbd.mobitrack.Model;

/**
 * Created by IMATPC-12 on 20-Feb-18.
 */

public class Span {

    private int spanNo;
    private long startTime;
    private long endTime;
    private double distance;


    public Span(int spanNo) {
        this.distance=0;
        this.spanNo=spanNo;
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

    public int getSpanNo() {
        return spanNo;
    }

    public void setSpanNo(int spanNo) {
        this.spanNo = spanNo;
    }

    public void addDistance(double distance){
        this.distance = this.distance+distance;
    }

    public double getDistance(){
        return this.distance;
    }
}
