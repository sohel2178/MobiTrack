package com.mobitrackbd.mobitrack.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobitrackbd.mobitrack.Model.Span;
import com.mobitrackbd.mobitrack.R;
import com.mobitrackbd.mobitrack.Utility.MyUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IMATPC-12 on 22-Feb-18.
 */

public class DistanceAdapter extends RecyclerView.Adapter<DistanceAdapter.DistanceHolder> {

    private Context context;
    private List<Span> spanList;
    private LayoutInflater inflater;

    private String[] hourArr;

    public DistanceAdapter(Context context) {
        this.context = context;
        this.spanList = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);

        this.hourArr = context.getResources().getStringArray(R.array.hour_array);
    }

    @Override
    public DistanceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = inflater.inflate(R.layout.single_distance,parent,false);

       return new DistanceHolder(view);
    }

    @Override
    public void onBindViewHolder(DistanceHolder holder, int position) {
        Span span = spanList.get(position);

        holder.tvHour.setText(hourArr[span.getSpanNo()]);
        //holder.tvDistance.setText(String.valueOf(span.getDistance()));
        //Log.d("Check",span.getDistance()+"");
        holder.tvDistance.setText(MyUtil.getTwoDecimalFormat(span.getDistance()/1000)+" KM");
    }

    @Override
    public int getItemCount() {
        return spanList.size();
    }


    public void addSpan(Span span){
        spanList.add(span);
        int pos = spanList.indexOf(span);
        notifyItemInserted(pos);
    }

    public void clear(){
        spanList = new ArrayList<>();
        notifyDataSetChanged();
    }

    class DistanceHolder extends RecyclerView.ViewHolder{

        TextView tvHour,tvDistance;

        public DistanceHolder(View itemView) {
            super(itemView);

            tvHour = itemView.findViewById(R.id.hour);
            tvDistance = itemView.findViewById(R.id.distance);
        }
    }
}
