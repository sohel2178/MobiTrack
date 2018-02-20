package com.mobitrackbd.mobitrack.Service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mobitrackbd.mobitrack.Listener.VolleyPostListener;
import com.mobitrackbd.mobitrack.Utility.URL;
import com.mobitrackbd.mobitrack.Volley.MyPostVolley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IMATPC-12 on 10-Feb-18.
 */

public class LocationService extends IntentService implements VolleyPostListener{

    private boolean isRunning;
    private String deviceId;

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("CALL","onReceive Called");
            Log.d("CALL",intent.getIntExtra("status",-1)+"");
            if(intent.getIntExtra("status",-1)==0){
                stopSelf();
                unregisterReceiver(myReceiver);
            }
        }
    };

    public LocationService() {
        super("LocationService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isRunning=true;

        IntentFilter intentFilter= new IntentFilter();
        intentFilter.addAction("www.mobitrack.service.stopper");
        registerReceiver(myReceiver,intentFilter);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        deviceId = intent.getStringExtra("device_id");
        Log.d("AAAA",deviceId);
        if (deviceId != null) {
            while (isRunning) {
                try {
                    Thread.sleep(15000);
                    Map<String, String> map = new HashMap<>();
                    map.put("device_id",deviceId);
                    MyPostVolley myPostVolley = new MyPostVolley(getApplicationContext(), map, URL.LASTLOC_REQ,this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void getRespose(String response) {
        Log.d("RRRR", response);
    }

    @Override
    public void onDestroy() {
//        isRunning=false;

        super.onDestroy();
    }
}
