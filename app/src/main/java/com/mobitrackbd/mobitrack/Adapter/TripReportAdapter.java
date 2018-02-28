package com.mobitrackbd.mobitrack.Adapter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.mobitrackbd.mobitrack.Model.DeviceLatLong;
import com.mobitrackbd.mobitrack.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by IMATPC-12 on 27-Feb-18.
 */

public class TripReportAdapter extends RecyclerView.Adapter<TripReportAdapter.TripReportHolder> {

    private Context context;
    private List<DeviceLatLong> deviceLatLongList;
    private LayoutInflater inflater;

    public TripReportAdapter(Context context, List<DeviceLatLong> deviceLatLongList) {
        this.context = context;
        this.deviceLatLongList = deviceLatLongList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public TripReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_trip_report, parent, false);

        TripReportHolder holder = new TripReportHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TripReportHolder holder, int position) {
        DeviceLatLong deviceLatLong = deviceLatLongList.get(position);

        holder.tvTime.setText(deviceLatLong.getServertime());
        holder.tvAddress.setText(deviceLatLong.getAddress());

    }

    @Override
    public int getItemCount() {
        return deviceLatLongList.size();
    }


    private String getAddress(DeviceLatLong latLng){
        String address = null;
        Geocoder geocoder  =new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(latLng.getLatitude()), Double.parseDouble(latLng.getLongitude()), 1);
            address = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }

    class TripReportHolder extends RecyclerView.ViewHolder{

        TextView tvTime,tvAddress;

        public TripReportHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.time);
            tvAddress = itemView.findViewById(R.id.address);

        }
    }
}
