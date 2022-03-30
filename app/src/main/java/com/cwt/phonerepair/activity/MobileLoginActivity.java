package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cwt.phonerepair.R;

public class MobileLoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etPhoneNum;

    Button btnSendOtp;

    ImageView ivBackSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_login);
        etPhoneNum=findViewById(R.id.etPhoneNum);
        btnSendOtp=findViewById(R.id.btnSendOtp);
        ivBackSignUp=findViewById(R.id.ivBackSignUp);

        btnSendOtp.setOnClickListener(this);
        ivBackSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v==btnSendOtp){
            Intent intent =new Intent(MobileLoginActivity.this,VerifyOtpActivity.class);
            startActivity(intent);
        }

        if (v==ivBackSignUp){
           onBackPressed();
        }
    }
}