package com.cwt.phonerepair.activity.serviceActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.cwt.phonerepair.R;

public class ServiceDetailsAcceptActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBackAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details_accept);

        initView();
    }

    private void initView() {

        ivBackAccept=findViewById(R.id.ivBackAccept);
        ivBackAccept.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivBackAccept:
                onBackPressed();

                break;

            default:
        }


    }
}