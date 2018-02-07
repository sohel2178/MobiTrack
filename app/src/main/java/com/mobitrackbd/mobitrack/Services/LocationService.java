package com.mobitrackbd.mobitrack.Services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class LocationService extends IntentService {

    LatLng start,end;

    private boolean isStart;
    private boolean isRunning;



    public LocationService() {
        super("LocationService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("KKKK","OnCreate Called");

        start = new LatLng(23.873751, 90.396454);
        end = new LatLng(23.746466,90.376015);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        isRunning = true;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (isRunning){

                    Log.d("KKKK",Thread.currentThread().getName());

                    try {
                        Thread.sleep(15000);
                        callFunction();
                        Log.d("KKKK","onHandleIntent Called");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.d("KKKK","InterruptedException Called");
                    }
                }


            }
        });

        thread.start();

    }

    private void callFunction() {
        if(isStart){
            isStart = false;

            Intent intent = new Intent(getPackageName());
            intent.putExtra("lat",end.latitude);
            intent.putExtra("long",end.longitude);
            sendBroadcast(intent);

        }else{
            isStart = true;
            Intent intent = new Intent(getPackageName());
            intent.putExtra("lat",start.latitude);
            intent.putExtra("long",start.longitude);
            sendBroadcast(intent);
        }
    }

    @Override
    public void onDestroy() {
        Log.d("KKKK",Thread.currentThread().getName());
        //isRunning=false;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        return super.onStartCommand(intent, flags, startId);
    }
}
