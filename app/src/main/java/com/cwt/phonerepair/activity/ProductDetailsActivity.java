package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.adapter.AllProductAdapter;
import com.cwt.phonerepair.adapter.ProdcutAdapter;
import com.cwt.phonerepair.adapter.ProductdetailViewPagerAdapter;
import com.cwt.phonerepair.adapter.StoreDetailsViewPagerAdapter;
import com.cwt.phonerepair.modelclass.ProductModel;
import com.cwt.phonerepair.modelclass.parameter.AddtoCartParameter;
import com.cwt.phonerepair.modelclass.parameter.GetStoreAllProdcutParameter;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementModel;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementResponse;
import com.cwt.phonerepair.modelclass.response.allStores.AllStoreModel;
import com.cwt.phonerepair.modelclass.response.getStoreallProdcut.GetStoreAllProdcutModel;
import com.cwt.phonerepair.modelclass.response.getStoreallProdcut.GetStoreAllProductResponse;
import com.cwt.phonerepair.modelclass.response.storedetails.StoreDetailsModel;
import com.cwt.phonerepair.modelclass.response.storedetails.StoreDetailsProductModel;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    DotsIndicator dotsIndicator;
    TextView tvSeeAll,tvproductName,tvPrice,tvProductHighLight;
    ViewPager view_pager;
    ProductdetailViewPagerAdapter productdetailViewPagerAdapter;

    RecyclerView rv_Prodcut;
    Context context;
    ArrayList<GetStoreAllProdcutModel> modelArrayList;
    ImageView ivBackProDetail;

    Button btnAddtocart;


    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;
    ArrayList<StoreDetailsProductModel>storeDetailsProductModels;
    StoreDetailsProductModel storeDetailsProductModel;
String storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

       // getData();
        initView();

/*
        adapter=new ProductdetailViewPagerAdapter(this,modelArrayList);
        view_pager.setAdapter(adapter);
        dotsIndicator.setViewPager(view_pager);

*/
        if (Utils.checkConnection(context)) {

            productDetails();
        } else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void productDetails() {

    }

/*
    private void getData() {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                storeDetailsProductModel = (StoreDetailsProductModel) intent.getSerializableExtra("data");


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/



    private void initView() {

        jsonPlaceHolderApi= ApiUtils.getAPIService();
        context=ProductDetailsActivity.this;
        modelArrayList=new ArrayList<>() ;
        storeDetailsProductModels=new ArrayList<>() ;
        sessionManager= new SessionManager(context);
        dotsIndicator = (DotsIndicator) findViewById(R.id.dots_indicator);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        rv_Prodcut=findViewById(R.id.rv_Prodcut);
        btnAddtocart=findViewById(R.id.btnAddtocart);
        ivBackProDetail=findViewById(R.id.ivBackProDetail);
        tvSeeAll=findViewById(R.id.tvSeeAll);
        tvproductName=findViewById(R.id.tvproductName);
        tvPrice=findViewById(R.id.tvPrice);
        tvProductHighLight=findViewById(R.id.tvProductHighLight);


        btnAddtocart.setOnClickListener(this);
        ivBackProDetail.setOnClickListener(this);
        tvSeeAll.setOnClickListener(this);

        if (Utils.checkConnection(context)) {

            allProdcuts();
        } else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void allProdcuts() {

       // storeId= String.valueOf(((storeDetailsProductModel.getStoreId())));
        Customprogress.showPopupProgressSpinner(context,true);
        GetStoreAllProdcutParameter add = new GetStoreAllProdcutParameter();
        add.setStoreId(1);

       // System.out.println("Store...Id..ProductDetails"+storeDetailsProductModel.getStoreId());

        Call<GetStoreAllProductResponse> call=jsonPlaceHolderApi.GetStoreAllProduct("Bearer "+sessionManager.getSavedToken(),add);
        call.enqueue(new Callback<GetStoreAllProductResponse>() {
            @Override
            public void onResponse(Call<GetStoreAllProductResponse> call, Response<GetStoreAllProductResponse> response) {
                Customprogress.showPopupProgressSpinner(context,false);

                if (response.isSuccessful()){

                    if (response.body().getStatus()){

                        modelArrayList= (ArrayList<GetStoreAllProdcutModel>) response.body().getData().getProduct();
                        AllProductAdapter adapter=new AllProductAdapter(modelArrayList, ProductDetailsActivity.this);
                        rv_Prodcut.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                        rv_Prodcut.setAdapter(adapter);
                        rv_Prodcut.setHasFixedSize(true);


                    }
                }


            }

            @Override
            public void onFailure(Call<GetStoreAllProductResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context,true);

            }
        });




    }

    @Override
    public void onClick(View v) {



        switch (v.getId()){
            case R.id.ivBackProDetail:
                onBackPressed();
                break;

            case R.id.tvSeeAll:
                Intent intent = new Intent(ProductDetailsActivity.this,AllProductActivity.class);
                startActivity(intent);
                break;

            case R.id.btnAddtocart:


                /*
                Intent intent1 = new Intent(ProductDetailsActivity.this,CartActivity.class);
                startActivity(intent1);*/
                break;

        }

    }

}