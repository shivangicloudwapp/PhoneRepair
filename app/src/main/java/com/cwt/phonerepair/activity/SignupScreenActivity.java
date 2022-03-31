package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cwt.phonerepair.R;

public class SignupScreenActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName,etEmail,etPassword,etConfPass;
   Button btnSignUp;
   TextView tvLogin;
   ImageView ivBackSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etConfPass=findViewById(R.id.etConPass);
        btnSignUp=findViewById(R.id.btnSignUp);
        tvLogin=findViewById(R.id.tvLogin);
        ivBackSignUp=findViewById(R.id.ivBackSignUp);

        tvLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        ivBackSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==tvLogin){
            Intent intent =new Intent(SignupScreenActivity.this,LoginScreenActivity.class);
            startActivity(intent);
        }
        else if (v==btnSignUp){
            Intent intent =new Intent(SignupScreenActivity.this,MobileLoginActivity.class);
            startActivity(intent);
        }
        else if (v==ivBackSignUp){
            onBackPressed();

        }

    }
}