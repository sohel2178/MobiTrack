package com.mobitrackbd.mobitrack.Model;

/**
 * Created by IMATPC-12 on 19-Feb-18.
 */

public class DeviceLatLong {
    private String servertime;
    private String latitude;
    private String longitude;
    private String address;

    public DeviceLatLong() {
    }

    public String getServertime() {
        return servertime;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
