package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cwt.phonerepair.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class SubscribeNewStoreActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSave;
    ImageView ivBackSubNewstore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_new_store);
        initView();


    }

    private void initView() {

        btnSave=findViewById(R.id.btnSave);
        ivBackSubNewstore=findViewById(R.id.ivBackSubNewstore);
        btnSave.setOnClickListener(this);
        ivBackSubNewstore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSave:
                showBottomSheetDialog();
                break;
            case R.id.ivBackSubNewstore:
                onBackPressed();

            default:
        }
    }

    private void showBottomSheetDialog() {

        Dialog customdialog = new Dialog(this);
        customdialog.setContentView(R.layout.subscribe_new_store_dailog);
        Window window = customdialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        window.setGravity(Gravity.CENTER);
        customdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        Button btnSave1 = customdialog.findViewById(R.id.btnDone);
        btnSave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customdialog.cancel();
            }
        });

        customdialog.show();
    }
}