package com.cwt.phonerepair.storeactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.adapter.ProdcutAdapter;
import com.cwt.phonerepair.modelclass.ProductModel;
import com.cwt.phonerepair.storeadapter.ProductManagementAdapter;

import java.util.ArrayList;

public class ProductManagementActivity extends AppCompatActivity implements View.OnClickListener {


    RecyclerView rvAllPro;
    Context context;
    ArrayList<ProductModel> modelArrayList;
    ImageView ivBackAllPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);

        context=this;

        rvAllPro=findViewById(R.id.rvAllPro);
        ivBackAllPro=findViewById(R.id.ivBackAllPro);


        ivBackAllPro.setOnClickListener(this);


        modelArrayList=new ArrayList<>() ;

        modelArrayList.add(new ProductModel("Iphone 13","RM400",R.drawable.iphone1));
        modelArrayList.add(new ProductModel("Iphone X","RM999",R.drawable.phone3));
        modelArrayList.add(new ProductModel("Iphone 13","RM400",R.drawable.watch));
        modelArrayList.add(new ProductModel("Iphone X","RM999",R.drawable.watch3));
        modelArrayList.add(new ProductModel("Iphone 13","RM400",R.drawable.bluetooth));
        modelArrayList.add(new ProductModel("Iphone 13","RM400",R.drawable.iphone1));
        modelArrayList.add(new ProductModel("Iphone 13","RM400",R.drawable.watch));
        modelArrayList.add(new ProductModel("Iphone 13","RM400",R.drawable.phone3));


        ProductManagementAdapter adapter=new ProductManagementAdapter(modelArrayList,this);
        rvAllPro.setLayoutManager(new GridLayoutManager(this, 2));
        rvAllPro.setAdapter(adapter);
        rvAllPro.setHasFixedSize(true);


    }

    @Override
    public void onClick(View v) {
        if (v==ivBackAllPro){
            onBackPressed();
        }

    }
}