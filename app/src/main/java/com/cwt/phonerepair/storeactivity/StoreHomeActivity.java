package com.cwt.phonerepair.storeactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cwt.phonerepair.R;

public class StoreHomeActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llStoreProfile,llWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_home);
        initView();


    }

    private void initView() {

        llStoreProfile=findViewById(R.id.llStoreProfile);
        llWallet=findViewById(R.id.llWallet);
        llStoreProfile.setOnClickListener(this);
        llWallet.setOnClickListener(this);
    }






    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.llStoreProfile:
                Intent intent =new Intent(StoreHomeActivity.this, StoreProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.llWallet:
                Intent intent1 =new Intent(StoreHomeActivity.this, StoreWalletActivity.class);
                startActivity(intent1);
                break;
            default:
        }

    }
}