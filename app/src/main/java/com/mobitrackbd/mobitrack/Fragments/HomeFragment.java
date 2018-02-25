package com.mobitrackbd.mobitrack.Fragments;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mobitrackbd.mobitrack.Activities.MapActivity;
import com.mobitrackbd.mobitrack.Adapter.VehicleAdapter;
import com.mobitrackbd.mobitrack.Listener.VehicleClickListener;
import com.mobitrackbd.mobitrack.Listener.VolleyPostListener;
import com.mobitrackbd.mobitrack.Model.Vehicle;
import com.mobitrackbd.mobitrack.R;
import com.mobitrackbd.mobitrack.Response.VehicleResponse;
import com.mobitrackbd.mobitrack.Utility.LocalData;
import com.mobitrackbd.mobitrack.Utility.URL;
import com.mobitrackbd.mobitrack.Volley.MyPostVolley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, VolleyPostListener,VehicleClickListener {

    private ImageView imageView;
    private RecyclerView rvVehicle;
    private TextView title;
    private VehicleAdapter adapter;
    private Gson gson;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new VehicleAdapter(getContext(),this);

        gson = new Gson();

        LocalData localData = new LocalData(getContext());

        Map<String, String> param = new HashMap<>();
        param.put(" customer_id", localData.getCustomerId());

        MyPostVolley myPostVolley = new MyPostVolley(getActivity(),param, URL.VEHICLE_REQ,this);
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
        //title.setOnClickListener(this);
        rvVehicle = view.findViewById(R.id.rv_vehicle);
        rvVehicle.setLayoutManager(new LinearLayoutManager(getContext()));
        rvVehicle.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getRespose(String response) {
        VehicleResponse vehicleResponse = gson.fromJson(response,VehicleResponse.class);
        Log.d("TTTTTT",response);
        if(vehicleResponse.getSuccess() == 1){
            List<Vehicle> vehicleList = vehicleResponse.getVehicles();
            for(Vehicle x:vehicleList){
                adapter.addVehicle(x);
            }
        }
    }

    @Override
    public void onClick(Vehicle vehicle) {
        Intent intent = new Intent(getContext(),MapActivity.class);
        intent.putExtra("vehicle_id",vehicle.getVehicle_id());
        intent.putExtra("device_id",vehicle.getDevice_id());
        startActivity(intent);
    }
}
