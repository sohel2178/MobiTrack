package com.mobitrackbd.mobitrack.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.mobitrackbd.mobitrack.Listener.VolleyPostListener;
import com.mobitrackbd.mobitrack.Model.MyLocation;
import com.mobitrackbd.mobitrack.Response.LastLocationResponse;
import com.mobitrackbd.mobitrack.Utility.URL;
import com.mobitrackbd.mobitrack.Volley.MyPostVolley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by sohel on 10-02-18.
 */

public class MyService extends Service implements VolleyPostListener {

    private static final String LOG="MyService";

    private int mRandomNumber;
    private boolean isRunning;

    private final int MIN=0;
    private final int MAX=100;

    private String deviceId;

    private IBinder mBinder = new MyServiceBinder();

    private Gson gson;



    public class MyServiceBinder extends Binder {
        public MyService getService(){
            return MyService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG,"On Bind");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        gson = new Gson();
        deviceId = intent.getStringExtra("device_id");
        Log.d("AAAA",deviceId);
        isRunning =true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                //generateRandomNumber();

                startLocationReq(deviceId);
            }
        }).start();

        //Log.d(LOG,"In onStartCommand Thread Id "+Thread.currentThread().getId()+" "+Thread.currentThread().getName());
        // stopSelf();
        return START_STICKY;
    }

    private void startLocationReq(String deviceId) {

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
    public void onDestroy() {
        Log.d(LOG,"Service is Stop From Activity");
        stopRunning();
        super.onDestroy();
    }


    private void generateRandomNumber(){
        while (isRunning){
            try {
                Thread.sleep(1000);
                if(isRunning){
                    mRandomNumber  = new Random().nextInt(MAX)+MIN;
                    Log.d(LOG,"Current Thread Id "+Thread.currentThread().getId()+" Random Number "+mRandomNumber);
                }
            } catch (InterruptedException e) {
                Log.d(LOG,"Thread Interrepted");
            }
        }
    }



    private void stopRunning(){
        isRunning =false;
    }

    public int getRandomNumber(){
        return mRandomNumber;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(LOG,"On Unbind");
        return super.onUnbind(intent);
    }

    @Override
    public void getRespose(String response) {
        LastLocationResponse lastLocationResponse = gson.fromJson(response,LastLocationResponse.class);

        if(lastLocationResponse.getSuccess()==1){
            MyLocation myLocation = lastLocationResponse.getLocation();

            Intent intent = new Intent(getApplicationContext().getPackageName());
            Bundle bundle = new Bundle();
            bundle.putSerializable("my_location",myLocation);

            intent.putExtras(bundle);

            sendBroadcast(intent);
        }
    }
}
