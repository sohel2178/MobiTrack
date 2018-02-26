package com.mobitrackbd.mobitrack.Utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by IMATPC-12 on 29-Jan-18.
 */

public class LocalData {
    private static final String SP_NAME ="userDetails";
    private static final String IS_LOGIN ="IS_LOGIN";
    private static final String CUSTOMER_ID ="CUSTOMER_ID";
    private static final String CUSTOMER_NAME ="CUSTOMER_NAME";
    private static final String CUSTOMER_EMAIL ="CUSTOMER_EMAIL";
    private static final String API_KEY ="API_KEY";


    private SharedPreferences sharedPreferences;
    private Context context;


    public LocalData(Context context) {
        this.context = context;
        sharedPreferences= context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
    }


    public boolean isLogin(){
        return sharedPreferences.getBoolean(IS_LOGIN,false);
    }

    public void setLogin(boolean value){
        sharedPreferences.edit().putBoolean(IS_LOGIN,value).apply();
    }

    public String getCustomerId(){
        return sharedPreferences.getString(CUSTOMER_ID,null);
    }
    public void setCustomerId(String customerId){
        sharedPreferences.edit().putString(CUSTOMER_ID,customerId).apply();
    }

    public String getCustomerName(){
        return sharedPreferences.getString(CUSTOMER_NAME,null);
    }
    public void setCustomerName(String customerName){
        sharedPreferences.edit().putString(CUSTOMER_NAME,customerName).apply();
    }

    public String getCustomerEmail(){
        return sharedPreferences.getString(CUSTOMER_EMAIL,null);
    }
    public void setCustomerEmail(String customerEmail){
        sharedPreferences.edit().putString(CUSTOMER_EMAIL,customerEmail).apply();
    }

    public int getLastIndex(){
        return sharedPreferences.getInt(API_KEY,0);
    }
    public void setLastIndex(int index){
        sharedPreferences.edit().putInt(API_KEY,index).apply();
    }

}
