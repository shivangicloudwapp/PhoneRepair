package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cwt.phonerepair.R;

public class OrderDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    ImageView ivBackOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        ivBackOrder=findViewById(R.id.ivBackOrder);
        ivBackOrder.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view==ivBackOrder){
            onBackPressed();
        }

    }
}