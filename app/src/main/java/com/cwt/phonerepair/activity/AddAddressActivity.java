package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cwt.phonerepair.R;

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBackAddAddress;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        btnSubmit=findViewById(R.id.btnSubmit);
        ivBackAddAddress=findViewById(R.id.ivBackAddAddress);

        ivBackAddAddress.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }










    @Override
    public void onClick(View v) {
        if (v==btnSubmit){
            Intent intent =new Intent(AddAddressActivity.this, ServiceRequestSuccessActivity.class);
            startActivity(intent);
        }

        else if (v==ivBackAddAddress){
            onBackPressed();
        }

    }
}