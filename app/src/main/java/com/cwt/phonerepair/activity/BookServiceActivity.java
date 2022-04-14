package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cwt.phonerepair.R;

public class BookServiceActivity extends AppCompatActivity implements View.OnClickListener {


Button btnSubmit;
ImageView ivBackBookSer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);

         btnSubmit =findViewById(R.id.btnSubmit);
        ivBackBookSer =findViewById(R.id.ivBackBookSer);

        btnSubmit.setOnClickListener(this);
        ivBackBookSer.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v==btnSubmit){
            Intent intent =new Intent(BookServiceActivity.this, ConfirmServiceActtivity.class);
            startActivity(intent);
        }
        else if (v==ivBackBookSer){

            onBackPressed();
        }

    }
}