package com.cwt.phonerepair.storeactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cwt.phonerepair.R;

public class StoreProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBackProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_profile);
        ivBackProfile=findViewById(R.id.ivBackProfile);
        ivBackProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onBackPressed();
    }
}