package com.mobitrackbd.mobitrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mobitrackbd.mobitrack.Activities.BaseActivity;
import com.mobitrackbd.mobitrack.Fragments.HomeFragment;
import com.mobitrackbd.mobitrack.Fragments.NoNetFragment;
import com.mobitrackbd.mobitrack.Model.Span;
import com.mobitrackbd.mobitrack.Utility.LocalData;
import com.mobitrackbd.mobitrack.Utility.MyUtil;

import java.util.Date;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(!isOnline()){

            if(savedInstanceState==null){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_container,new NoNetFragment()).commit();
            }

        }else{
            LocalData localData = new LocalData(getApplicationContext());

            Log.d("JJJ",localData.isLogin()+"");


            if(!localData.isLogin()){
                Log.d("JJJ","KKK");
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
}
