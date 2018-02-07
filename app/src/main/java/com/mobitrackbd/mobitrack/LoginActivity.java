package com.mobitrackbd.mobitrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mobitrackbd.mobitrack.Listener.VolleyPostListener;
import com.mobitrackbd.mobitrack.Volley.MyPostVolley;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,VolleyPostListener{

    private EditText etMail,etPassword;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        MyPostVolley myPostVolley = new MyPostVolley(this,new HashMap<String, String>(),"HHHH",this);

    }


    @Override
    public void getRespose(String response) {

    }
}
