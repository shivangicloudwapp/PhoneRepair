package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.utils.SessionManager;

public class SplashScreenActivity extends AppCompatActivity {

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sessionManager = new SessionManager(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionManager.isLoggedIn()){
                    Intent i=new Intent(SplashScreenActivity.this,
                            DashboardActivity.class);

                    startActivity(i);

                    finish();
                }else {
                    Intent i=new Intent(SplashScreenActivity.this,
                            LoginScreenActivity.class);

                    startActivity(i);

                    finish();
                }

                //the current activity will get finished.
            }
        }, 1000);

    }
}