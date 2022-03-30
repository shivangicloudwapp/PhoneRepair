package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.adapter.ProdcutAdapter;
import com.cwt.phonerepair.adapter.StoreDetailsViewPagerAdapter;
import com.cwt.phonerepair.modelclass.ProductModel;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    DotsIndicator dotsIndicator;
    TextView tvSeeAll;

    ViewPager view_pager;
    StoreDetailsViewPagerAdapter adapter;

    RecyclerView rv_Prodcut;
    Context context;
    ArrayList<ProductModel> modelArrayList;
    ImageView ivBackProDetail;

    Button btnAddtocart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        dotsIndicator = (DotsIndicator) findViewById(R.id.dots_indicator);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        rv_Prodcut=findViewById(R.id.rv_Prodcut);
        btnAddtocart=findViewById(R.id.btnAddtocart);
        ivBackProDetail=findViewById(R.id.ivBackProDetail);
        tvSeeAll=findViewById(R.id.tvSeeAll);


        adapter=new StoreDetailsViewPagerAdapter(this);
        view_pager.setAdapter(adapter);
        dotsIndicator.setViewPager(view_pager);

        ivBackProDetail.setOnClickListener(this);
        tvSeeAll.setOnClickListener(this);
        btnAddtocart.setOnClickListener(this);



        modelArrayList=new ArrayList<>() ;

        modelArrayList.add(new ProductModel("Iphone 13","RM400",R.drawable.iphone1));
        modelArrayList.add(new ProductModel("Iphone X","RM999",R.drawable.iphone2));
        modelArrayList.add(new ProductModel("Iphone 13","RM400",R.drawable.iphone1));
        modelArrayList.add(new ProductModel("Iphone X","RM999",R.drawable.iphone2));
        modelArrayList.add(new ProductModel("Iphone 13","RM400",R.drawable.iphone1));


        ProdcutAdapter adapter=new ProdcutAdapter(modelArrayList,this);
        rv_Prodcut.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rv_Prodcut.setAdapter(adapter);
        rv_Prodcut.setHasFixedSize(true);



    }

    @Override
    public void onClick(View v) {
        if (v==ivBackProDetail){
            onBackPressed();
        }

        else if (v==tvSeeAll){

            Intent intent = new Intent(ProductDetailsActivity.this,AllProductActivity.class);
            startActivity(intent);

        }

        else if (v==btnAddtocart){

            Intent intent = new Intent(ProductDetailsActivity.this,CartActivity.class);
            startActivity(intent);

        }

    }

}