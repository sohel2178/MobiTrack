package com.mobitrackbd.mobitrack.Fragments;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobitrackbd.mobitrack.Activities.MapActivity;
import com.mobitrackbd.mobitrack.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private ImageView imageView;

    private RecyclerView rvVehicle;

    private TextView title;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);

        

        return view;
    }

    private void initView(View view) {
        title = view.findViewById(R.id.title);
        title.setOnClickListener(this);
        rvVehicle = view.findViewById(R.id.rv_vehicle);
        rvVehicle.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClick(View v) {

        startActivity(new Intent(getContext(), MapActivity.class));

    }
}
