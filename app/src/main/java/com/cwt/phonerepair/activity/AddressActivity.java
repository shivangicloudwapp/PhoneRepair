package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cwt.phonerepair.R;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAddaddress,btnSubmit;

    ImageView ivBackAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initView();


    }

    private void initView() {

        btnAddaddress=findViewById(R.id.btnAddaddress);
        btnSubmit=findViewById(R.id.btnSubmit);
        ivBackAddress=findViewById(R.id.ivBackAddress);


        btnAddaddress.setOnClickListener(this);
        ivBackAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==btnAddaddress){

            Intent intent =new Intent(AddressActivity.this, AddAddressActivity.class);
            startActivity(intent);
        }


        else if (v==ivBackAddress){
            onBackPressed();
        }

    }
}