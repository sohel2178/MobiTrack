package com.mobitrackbd.mobitrack.Response;

import com.mobitrackbd.mobitrack.Model.DeviceLatLong;

import java.util.List;

/**
 * Created by IMATPC-12 on 19-Feb-18.
 */

public class SummaryReportResponse {
    private int success;
    private String message;
    private List<DeviceLatLong> latlongs;

    public SummaryReportResponse() {
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DeviceLatLong> getLatlongs() {
        return latlongs;
    }

    public void setLatlongs(List<DeviceLatLong> latlongs) {
        this.latlongs = latlongs;
    }
}
