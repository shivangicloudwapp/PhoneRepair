package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cwt.phonerepair.R;

public class ConfirmServiceActtivity extends AppCompatActivity implements View.OnClickListener {

    Button btnBookService;
    ImageView ivBackConSer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_service_acttivity);


        btnBookService=findViewById(R.id.btnBookService);
        ivBackConSer=findViewById(R.id.ivBackConSer);

        btnBookService.setOnClickListener(this);
        ivBackConSer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==btnBookService){

                Intent intent =new Intent(ConfirmServiceActtivity.this, AddressActivity.class);
                startActivity(intent);
        }
       else if (v==ivBackConSer){

            onBackPressed();
        }
    }
}