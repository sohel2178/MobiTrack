package com.mobitrackbd.mobitrack.Utility;

/**
 * Created by Sohel on 2/3/2018.
 */

public class URL {

    private static final String BASE_URL="http://api.mobitrackbd.com/";
    public static final String LOGIN=BASE_URL+"login/userlogin";
    public static final String VEHICLE_REQ=BASE_URL+"vehicles/allvehiclesbycid";
    public static final String LASTLOC_REQ=BASE_URL+"report/lastlocation";

    private static final String SECOND_URL = "http://api.mobitrackbd.com/";
    public static final String SUMMARY_REPORT = SECOND_URL + "report/get24hourslatlong";


}
