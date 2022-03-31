package com.cwt.phonerepair.storesservicedetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cwt.phonerepair.R;

public class StoreServiceDetailsPendingActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBackServRej;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_service_details_pending);
        ivBackServRej=findViewById(R.id.ivBackServRej);

        ivBackServRej.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case  R.id.ivBackServRej:
                onBackPressed();

            default:
        }

    }
}