package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.adapter.AllStoresAdapter;
import com.cwt.phonerepair.modelclass.OurExclusiveStoreModel;

import java.util.ArrayList;

public class AllStoresActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView  rvAllStores;
    Context context;
    ArrayList<OurExclusiveStoreModel> modelArrayList;
    ImageView ivBackAllStores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_stores);
        rvAllStores=findViewById(R.id.rvAllStores);
        ivBackAllStores=findViewById(R.id.ivBackAllStores);

        ivBackAllStores.setOnClickListener(this);



        modelArrayList=new ArrayList<>() ;

        modelArrayList.add(new OurExclusiveStoreModel("The Apple Store","New Door No. A4-72 Ground Floor c21 Mall",R.drawable.store1));
        modelArrayList.add(new OurExclusiveStoreModel("Dong Mobile Store","New Door No. A4-72 First Floor c21 Mall",R.drawable.store2));
        modelArrayList.add(new OurExclusiveStoreModel("No One Mobile Store","New Door No. A4-72 Ground Floor c21 Mall",R.drawable.store3));
        modelArrayList.add(new OurExclusiveStoreModel("Dong Mobile Store","New Door No. A4-72 First Floor c21 Mall",R.drawable.store4));
        modelArrayList.add(new OurExclusiveStoreModel("Dong Mobile Store","New Door No. A4-72 First Floor c21 Mall",R.drawable.store4));
        modelArrayList.add(new OurExclusiveStoreModel("Dong Mobile Store","New Door No. A4-72 First Floor c21 Mall",R.drawable.store2));



        AllStoresAdapter allStoresAdapter=new AllStoresAdapter(modelArrayList,this);
        rvAllStores.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvAllStores.setAdapter(allStoresAdapter);
        rvAllStores.setHasFixedSize(true);


    }

    @Override
    public void onClick(View v) {

        if (v==ivBackAllStores){
            onBackPressed();
        }

    }
}