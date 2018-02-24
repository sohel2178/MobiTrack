package com.mobitrackbd.mobitrack;

import android.content.Intent;
import android.os.Bundle;

import com.mobitrackbd.mobitrack.Activities.BaseActivity;
import com.mobitrackbd.mobitrack.Fragments.HomeFragment;
import com.mobitrackbd.mobitrack.Model.Span;
import com.mobitrackbd.mobitrack.Utility.LocalData;
import com.mobitrackbd.mobitrack.Utility.MyUtil;

import java.util.Date;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalData localData = new LocalData(getApplicationContext());
        if(!localData.isLogin()){
           startActivity(new Intent(this, LoginActivity.class));
           finish();
        }else{
            setUpNavigationDrawer();
            if(savedInstanceState==null){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_container,new HomeFragment()).commit();
            }
        }



    }
}
