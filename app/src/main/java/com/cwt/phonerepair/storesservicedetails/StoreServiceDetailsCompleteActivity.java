package com.cwt.phonerepair.storesservicedetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cwt.phonerepair.R;

public class StoreServiceDetailsCompleteActivity extends AppCompatActivity implements View.OnClickListener {


    ImageView ivBackComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_service_details_complete);

        initView();

    }


    private void initView() {
        ivBackComplete=findViewById(R.id.ivBackComplete);
        ivBackComplete.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ivBackComplete:
                onBackPressed();
                break;

            default:

        }

    }
}