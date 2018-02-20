package com.mobitrackbd.mobitrack.Response;

import com.mobitrackbd.mobitrack.Model.MyLocation;

/**
 * Created by IMATPC-12 on 10-Feb-18.
 */

public class LastLocationResponse {
    private int success;
    private String message;
    private MyLocation location;

    public LastLocationResponse() {
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

    public MyLocation getLocation() {
        return location;
    }

    public void setLocation(MyLocation location) {
        this.location = location;
    }
}
