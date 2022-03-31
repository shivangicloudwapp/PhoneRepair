package com.cwt.phonerepair.storesservicedetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cwt.phonerepair.R;

public class StoreServiceDetailsRejectedActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBackServPending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_service_details_rejected);
        ivBackServPending=findViewById(R.id.ivBackServPending);

        ivBackServPending.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case  R.id.ivBackServPending:
                onBackPressed();

            default:
        }

    }
}