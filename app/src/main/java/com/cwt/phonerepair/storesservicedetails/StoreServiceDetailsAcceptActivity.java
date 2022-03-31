package com.cwt.phonerepair.storesservicedetails;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cwt.phonerepair.R;

public class StoreServiceDetailsAcceptActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnServicedone;

    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_service_details_accept);

        initView();


    }

    private void initView() {

        ivBack=findViewById(R.id.ivBack);
        btnServicedone=findViewById(R.id.btnServicedone);

        btnServicedone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnServicedone:
                showBottomSheetDialog();

                break;

            case R.id.ivBack:
                onBackPressed();
                break;

            default:

        }

    }

    private void showBottomSheetDialog() {

        Dialog customdialog = new Dialog(this);
        customdialog.setContentView(R.layout.store_servicedetails_accept_dailog);
        Window window = customdialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        window.setGravity(Gravity.CENTER);
        customdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        Button btnSub = customdialog.findViewById(R.id.btnSub);
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customdialog.cancel();
            }
        });

        customdialog.show();
    }
}