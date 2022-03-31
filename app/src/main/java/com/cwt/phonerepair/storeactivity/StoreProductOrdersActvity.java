package com.cwt.phonerepair.storeactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.modelclass.OrderModel;
import com.cwt.phonerepair.storeadapter.StoreProductOrderAdapter;
import com.cwt.phonerepair.storeadapter.StoreServiceAcceptedAdapter;
import com.cwt.phonerepair.storemodelclass.StoreServiceOrderModel;

import java.util.ArrayList;

public class StoreProductOrdersActvity extends AppCompatActivity {

    Context context;

    RecyclerView rvProductOrder;

    ArrayList<OrderModel> modelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_product_orders_actvity);
        initView();
        modelList = new ArrayList<>();

        for (int i=0;i<=9;i++){

            modelList.add(new OrderModel ("4 Items","#CUEP8383832","18-02-2021  12:24PM"));


        }
        StoreProductOrderAdapter storeServiceAcceptedAdapter = new StoreProductOrderAdapter( modelList,this);
        rvProductOrder.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvProductOrder.setAdapter(storeServiceAcceptedAdapter);
        rvProductOrder.setHasFixedSize(true);

    }

    private void initView() {
        context=this;
        rvProductOrder = findViewById(R.id.rvProductOrder);

    }

}

