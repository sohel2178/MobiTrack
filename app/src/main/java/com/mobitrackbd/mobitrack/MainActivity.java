package com.mobitrackbd.mobitrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobitrackbd.mobitrack.Activities.BaseActivity;
import com.mobitrackbd.mobitrack.Fragments.HomeFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpNavigationDrawer();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container,new HomeFragment()).commit();
        }
    }
}
