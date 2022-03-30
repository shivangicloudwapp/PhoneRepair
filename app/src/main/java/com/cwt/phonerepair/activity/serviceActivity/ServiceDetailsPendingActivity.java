package com.cwt.phonerepair.activity.serviceActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.activity.RateUsActivity;

public class ServiceDetailsPendingActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBackServPending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details_pending);
        initView();
    }

    private void initView() {
        ivBackServPending = findViewById(R.id.ivBackServPending);
        ivBackServPending.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBackServPending:
                onBackPressed();

                break;

            default:
        }
    }
}