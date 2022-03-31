package com.cwt.phonerepair.storeactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cwt.phonerepair.R;

public class StoreProductOrderDetails extends AppCompatActivity implements View.OnClickListener {

    ImageView  ivBackOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_product_order_details);

        ivBackOrder=findViewById(R.id.ivBackOrder);

        ivBackOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onBackPressed();
    }
}