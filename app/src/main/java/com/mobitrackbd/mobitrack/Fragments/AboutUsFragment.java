package com.mobitrackbd.mobitrack.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitrackbd.mobitrack.R;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {


    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        BannerSlider slider =  view.findViewById(R.id.slider);
        List<Banner> bannerList=new ArrayList<>();

        String[] urls = getResources().getStringArray(R.array.about_slider);

        for(String x:urls){
            bannerList.add(new RemoteBanner(x));
        }
        slider.setBanners(bannerList);
        return view;
    }

}
