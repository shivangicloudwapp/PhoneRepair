package com.cwt.phonerepair.activity.serviceActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.cwt.phonerepair.R;

public class ServiceDetailsRejectActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBackReject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details_reject);

        initView();
    }

    private void initView() {
        ivBackReject=findViewById(R.id.ivBackReject);
        ivBackReject.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ivBackReject:
                onBackPressed();

                break;

            default:

        }

    }
}