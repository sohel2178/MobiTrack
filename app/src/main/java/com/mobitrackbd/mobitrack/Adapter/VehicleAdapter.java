package com.mobitrackbd.mobitrack.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobitrackbd.mobitrack.Listener.VehicleClickListener;
import com.mobitrackbd.mobitrack.Model.Vehicle;
import com.mobitrackbd.mobitrack.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sohel on 1/31/2018.
 */

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleHolder> {

    private Context context;
    private List<Vehicle> vehicleList;
    private LayoutInflater inflater;
    private VehicleClickListener clickListener;


    public VehicleAdapter(Context context, VehicleClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
        this.vehicleList = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public VehicleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = inflater.inflate(R.layout.single_vehicle,parent,false);

       VehicleHolder holder = new VehicleHolder(view);
       return holder;
    }

    public void addVehicle(Vehicle vehicle){
        vehicleList.add(vehicle);
        int position = vehicleList.indexOf(vehicle);
        notifyItemInserted(position);
    }

    @Override
    public void onBindViewHolder(VehicleHolder holder, int position) {

        Vehicle vehicle = vehicleList.get(position);

        holder.tvModel.setText(vehicle.getVehicle_number());
        holder.tvNumber.setText(vehicle.getVehicle_year_make_model());
        holder.tvLocation.setText(vehicle.getVehicle_address());



        //holder.tvModel.setText();

    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public class VehicleHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvModel,tvNumber,tvLocation;

        public VehicleHolder(View itemView) {
            super(itemView);

            tvModel = itemView.findViewById(R.id.model);
            tvNumber = itemView.findViewById(R.id.number);
            tvLocation = itemView.findViewById(R.id.location);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(vehicleList.get(getAdapterPosition()));

        }
    }
}
