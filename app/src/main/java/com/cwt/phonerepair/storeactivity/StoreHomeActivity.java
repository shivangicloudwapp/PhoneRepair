package com.cwt.phonerepair.storeactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cwt.phonerepair.R;

public class StoreHomeActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llStoreProfile,llWallet,llProOder,llServiceOrder,llProductMgmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_home);
        initView();


    }

    private void initView() {

        llStoreProfile=findViewById(R.id.llStoreProfile);
        llWallet=findViewById(R.id.llWallet);
        llServiceOrder=findViewById(R.id.llServiceOrder);
        llProOder=findViewById(R.id.llProOder);
        llProductMgmt=findViewById(R.id.llProductMgmt);

        llStoreProfile.setOnClickListener(this);
        llWallet.setOnClickListener(this);
        llServiceOrder.setOnClickListener(this);
        llProOder.setOnClickListener(this);
        llProductMgmt.setOnClickListener(this);
    }






    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.llStoreProfile:
                Intent intent =new Intent(StoreHomeActivity.this, StoreProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.llWallet:
                Intent intent2 =new Intent(StoreHomeActivity.this, StoreWalletActivity.class);
                startActivity(intent2);
                break;

            case R.id.llServiceOrder:
                Intent intent3 =new Intent(StoreHomeActivity.this, StoreServiceOrderActivity.class);
                startActivity(intent3);
                break;

            case R.id.llProOder:
                Intent intent4 =new Intent(StoreHomeActivity.this, StoreProductOrdersActvity.class);
                startActivity(intent4);
                break;

            case R.id.llProductMgmt:
                Intent intent5 =new Intent(StoreHomeActivity.this, ProductManagementActivity.class);
                startActivity(intent5);
                break;


            default:
        }

    }
}