package com.mobitrackbd.mobitrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobitrackbd.mobitrack.Activities.BaseActivity;
import com.mobitrackbd.mobitrack.Fragments.HomeFragment;
import com.mobitrackbd.mobitrack.Utility.LocalData;

public class MainActivity extends BaseActivity {

    private LocalData localData;

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

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container,new HomeFragment()).commit();
        }




    }
}
