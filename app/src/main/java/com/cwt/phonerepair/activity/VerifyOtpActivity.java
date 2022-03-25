package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cwt.phonerepair.R;

public class VerifyOtpActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBackVerifyotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        ivBackVerifyotp= findViewById(R.id.ivBackVerifyotp);
        ivBackVerifyotp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==ivBackVerifyotp){
            onBackPressed();
        }

    }
}