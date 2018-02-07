package com.mobitrackbd.mobitrack.Utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by IMATPC-12 on 29-Jan-18.
 */

public class LocalData {
    private static final String SP_NAME ="userDetails";
    private static final String IS_LOGIN ="IS_LOGIN";

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
}
