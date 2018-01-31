package com.mobitrackbd.mobitrack.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.TextView;

import com.mobitrackbd.mobitrack.NavigationDrawer;
import com.mobitrackbd.mobitrack.R;

/**
 * Created by Sohel on 1/31/2018.
 */

public class BaseActivity extends AppCompatActivity {
    private TextView tvTitle;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private FragmentManager manager;
    private NavigationDrawer drawerFragment;


    public void setupToolbar() {
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }


    public void setUpNavigationDrawer(){
        setupToolbar();
        manager = getSupportFragmentManager();
        mDrawerLayout =  findViewById(R.id.drawer_layout);
        drawerFragment =
                (NavigationDrawer) manager.findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, mDrawerLayout, toolbar);
        //getSupportActionBar().setTitle(Constant.HOME);
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }



}
