package com.mobitrackbd.mobitrack.Response;

import com.mobitrackbd.mobitrack.Model.User;
import com.mobitrackbd.mobitrack.Model.Vehicle;

import java.util.List;

/**
 * Created by IMATPC-12 on 07-Feb-18.
 */

public class VehicleResponse {
    private int success;
    private String message;
    private List<Vehicle> vehicles;

    public VehicleResponse() {
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

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
