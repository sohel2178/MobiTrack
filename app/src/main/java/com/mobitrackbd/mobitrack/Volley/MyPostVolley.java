package com.mobitrackbd.mobitrack.Volley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobitrackbd.mobitrack.Listener.VolleyPostListener;
import com.mobitrackbd.mobitrack.R;

import java.util.Map;

/**
 * Created by Sohel on 9/21/2016.
 */
public class MyPostVolley {

    private Activity activity;
    private Map<String,String> params;
    private String url;
    private String progressDialogMessage;

    // Listener
    private VolleyPostListener listener;

    private ProgressDialog mProgressDialog;




    public MyPostVolley(Activity activity, Map<String,String> params, String url,VolleyPostListener listener){
        this.activity=activity;
        this.params = params;
        this.url =url;
        this.progressDialogMessage = progressDialogMessage;
        this.listener = listener;

        applyPostVolley();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {

            mProgressDialog = new ProgressDialog(activity,R.style.MyTheme);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large_Inverse);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }




    private void applyPostVolley(){

        showProgressDialog();


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("BALLLLLLL",response);

                        hideProgressDialog();

                        listener.getRespose(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                       // hideProgressDialog();

                        //Toast.makeText(activity, "Problem in Fetching Data", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(postRequest);
    }





}
