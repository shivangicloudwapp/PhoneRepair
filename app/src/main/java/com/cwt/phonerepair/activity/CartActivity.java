package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cwt.phonerepair.R;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBackCart;
    Button btnOrderNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ivBackCart=findViewById(R.id.ivBackCart);
        btnOrderNow=findViewById(R.id.btnOrderNow);

        ivBackCart.setOnClickListener(this);
        btnOrderNow.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v==ivBackCart){
          onBackPressed();
        }
        else if (v==btnOrderNow){
            Intent intent =new Intent(CartActivity.this, OrderPlacedSuccessActivity.class);
            startActivity(intent);
        }


    }
}