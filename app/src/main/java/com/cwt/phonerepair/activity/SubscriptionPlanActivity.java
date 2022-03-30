package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.adapter.ProdcutAdapter;
import com.cwt.phonerepair.adapter.StoreDetailsViewPagerAdapter;
import com.cwt.phonerepair.adapter.SubscriptionAdapter;
import com.cwt.phonerepair.modelclass.ProductModel;
import com.cwt.phonerepair.modelclass.SubscriptionModel;

import java.util.ArrayList;

public class SubscriptionPlanActivity extends AppCompatActivity implements View.OnClickListener {

    StoreDetailsViewPagerAdapter adapter;

    RecyclerView rvSubPlan;
    Context context;
    ArrayList<SubscriptionModel> modelArrayList;
    ImageView ivBackSubPlan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_plan);

        initView();
    }

    private void initView() {

        context=getApplicationContext();
        rvSubPlan=findViewById(R.id.rvSubPlan);
        ivBackSubPlan=findViewById(R.id.ivBackSubPlan);

        ivBackSubPlan.setOnClickListener(this);



        modelArrayList=new ArrayList<>() ;

        modelArrayList.add(new SubscriptionModel("Silver ","3 Months","Post 3 Items"));
        modelArrayList.add(new SubscriptionModel("Bronze ","6 Months","Post 5 Items"));
        modelArrayList.add(new SubscriptionModel("Gold ","12 Months","Post 10 Items"));
        modelArrayList.add(new SubscriptionModel("Silver ","3 Months","Post 3 Items"));
        modelArrayList.add(new SubscriptionModel("Gold ","12 Months","Post 10 Items"));


        SubscriptionAdapter adapter=new SubscriptionAdapter(modelArrayList,this);
        rvSubPlan.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvSubPlan.setAdapter(adapter);
        rvSubPlan.setHasFixedSize(true);

    }

    @Override
    public void onClick(View view) {
        if (view==ivBackSubPlan){
            onBackPressed();
        }



    }
}