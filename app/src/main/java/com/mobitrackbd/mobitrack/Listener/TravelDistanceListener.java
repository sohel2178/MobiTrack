package com.mobitrackbd.mobitrack.Listener;

import com.google.android.gms.maps.model.LatLng;
import com.mobitrackbd.mobitrack.Model.Data;

import java.util.List;

/**
 * Created by IMATPC-12 on 20-Feb-18.
 */

public interface TravelDistanceListener {
    public void getTravelDistanceList(List<Data> travelDistanceList);

    public void getDirectionList(List<LatLng> latLngList);
}
