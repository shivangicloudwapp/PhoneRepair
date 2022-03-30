package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cwt.phonerepair.R;

public class OrderPlacedSuccessActivity extends AppCompatActivity implements View.OnClickListener {



    Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed_success);
        btnHome=findViewById(R.id.btnBacktoHome);
        btnHome.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==btnHome){
            Intent intent =new Intent(OrderPlacedSuccessActivity.this, DashboardActivity.class);
            startActivity(intent);
        }

    }
}