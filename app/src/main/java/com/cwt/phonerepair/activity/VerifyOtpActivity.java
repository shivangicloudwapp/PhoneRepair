package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cwt.phonerepair.R;

public class VerifyOtpActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBackVerifyotp;
    Button btnVerifyOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        ivBackVerifyotp= findViewById(R.id.ivBackVerifyotp);
        btnVerifyOtp= findViewById(R.id.btnVerifyOtp);

        ivBackVerifyotp.setOnClickListener(this);
        btnVerifyOtp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==ivBackVerifyotp){
            onBackPressed();
        }

        else if(v==btnVerifyOtp){
            Intent intent =new Intent(VerifyOtpActivity.this,DashboardActivity.class);
            startActivity(intent);
        }

    }
}