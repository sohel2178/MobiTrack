package com.mobitrackbd.mobitrack;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mobitrackbd.mobitrack.Activities.BaseActivity;
import com.mobitrackbd.mobitrack.Fragments.HomeFragment;
import com.mobitrackbd.mobitrack.Utility.LocalData;

public class MainActivity extends BaseActivity {

    private LocalData localData;

    public static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localData = new LocalData(getApplicationContext());

       /* if(!localData.isLogin()){
            finish();

            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }else {
            setUpNavigationDrawer();

            if(savedInstanceState==null){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_container,new HomeFragment()).commit();
            }
        }*/

       if(!isGooglePlayServicesAvailable()){
          acquireGooglePlayServices();
       }else{
           acquireGooglePlayServices();
           if(savedInstanceState==null){
               getSupportFragmentManager().beginTransaction()
                       .add(R.id.main_container,new HomeFragment()).commit();
           }
       }






    }

    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }


    public void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    private void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    acquireGooglePlayServices();

                } /*else {
                    getResultsFromApi();
                }*/
                break;
        }
    }
}
