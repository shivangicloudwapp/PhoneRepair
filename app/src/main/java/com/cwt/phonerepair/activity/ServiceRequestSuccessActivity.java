package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cwt.phonerepair.R;

public class ServiceRequestSuccessActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnBacktoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request_placed);
        btnBacktoHome=findViewById(R.id.btnBacktoHome);
        btnBacktoHome.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==btnBacktoHome){
            Intent intent =new Intent(ServiceRequestSuccessActivity.this, DashboardActivity.class);
            startActivity(intent);
        }

    }
}