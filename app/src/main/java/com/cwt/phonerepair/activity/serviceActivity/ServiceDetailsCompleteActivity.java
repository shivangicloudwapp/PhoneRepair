package com.cwt.phonerepair.activity.serviceActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.activity.RateUsActivity;

public class ServiceDetailsCompleteActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnPayNow,btnRateSer;

    ImageView ivBackServDetailPen1,ivBackServDetailPen2;

LinearLayout llUnPaid,llPaid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details_complete);



        initView();

    }

    private void initView() {
        btnRateSer=findViewById(R.id.btnRateSer);
        btnPayNow=findViewById(R.id.btnPayNow);
        ivBackServDetailPen1=findViewById(R.id.ivBackServDetailPen1);
        ivBackServDetailPen2=findViewById(R.id.ivBackServDetailPen2);
        llUnPaid=findViewById(R.id.llUnPaid);
        llPaid=findViewById(R.id.llPaid);
        ivBackServDetailPen2=findViewById(R.id.ivBackServDetailPen2);

        ivBackServDetailPen1.setOnClickListener(this);
        ivBackServDetailPen2.setOnClickListener(this);
        btnRateSer.setOnClickListener(this);
        btnPayNow.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnPayNow:
                 llUnPaid.setVisibility(View.GONE);
                 llPaid.setVisibility(View.VISIBLE);

                break;
            case R.id.btnRateSer:
                Intent intent =new Intent(ServiceDetailsCompleteActivity.this, RateUsActivity.class);
                startActivity(intent);

                break;
            case R.id.ivBackServDetailPen1:
                onBackPressed();
            break;

            case R.id.ivBackServDetailPen2:

                llPaid.setVisibility(View.GONE);
                llUnPaid.setVisibility(View.VISIBLE);
                break;
            default:

        }


    }
}