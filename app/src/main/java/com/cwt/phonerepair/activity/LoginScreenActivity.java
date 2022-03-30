package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cwt.phonerepair.R;

public class LoginScreenActivity extends AppCompatActivity implements View.OnClickListener {
Button btnLogin;
TextView tvSignup;

EditText etEmail,etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        btnLogin=findViewById(R.id.btnLogin);
        tvSignup=findViewById(R.id.tvSignup);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(this);
        tvSignup.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        if (v==btnLogin){
            Intent intent =new Intent(LoginScreenActivity.this,MobileLoginActivity.class);
            startActivity(intent);
        }

       else if (v==tvSignup){
            Intent intent =new Intent(LoginScreenActivity.this,SignupScreenActivity.class);
            startActivity(intent);
        }

    }
}