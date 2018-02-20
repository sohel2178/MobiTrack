package com.mobitrackbd.mobitrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobitrackbd.mobitrack.Listener.VolleyPostListener;
import com.mobitrackbd.mobitrack.Response.LoginResponse;
import com.mobitrackbd.mobitrack.Utility.LocalData;
import com.mobitrackbd.mobitrack.Utility.URL;
import com.mobitrackbd.mobitrack.Volley.MyPostVolley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, VolleyPostListener {

    private EditText etMail,etPassword;
    private Button btnSubmit;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gson = new Gson();
        initView();
    }

    private void initView() {
        etMail = findViewById(R.id.et_userMail);
        etPassword = findViewById(R.id.et_userPassword);
        btnSubmit = findViewById(R.id.btn_userLogin);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String email = etMail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please Fill Up Email Field", Toast.LENGTH_SHORT).show();
            etMail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Fill Up Password Field", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("email",email);
        map.put("password",password);

        MyPostVolley myPostVolley = new MyPostVolley(this,map, URL.LOGIN,this);
    }

    @Override
    public void getRespose(String response) {

        //Log.d("RRRRRRR",response);
        LoginResponse loginResponse = gson.fromJson(response,LoginResponse.class);
        if(loginResponse.getSuccess() == 1){
            LocalData localData = new LocalData(this);
            localData.setLogin(true);
            localData.setCustomerId(loginResponse.getUser().getCustomer_id());
            localData.setCustomerName(loginResponse.getUser().getCustomer_name());
            localData.setCustomerEmail(loginResponse.getUser().getCustomer_primary_email());
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }
}
