package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.GetStoreId;
import com.cwt.phonerepair.Interface.GetSubscriptionData;
import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.Allurls;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.adapter.AllStoresAdapter;
import com.cwt.phonerepair.adapter.ProdcutAdapter;
import com.cwt.phonerepair.adapter.StoreDetailsViewPagerAdapter;
import com.cwt.phonerepair.adapter.SubscriptionAdapter;
import com.cwt.phonerepair.modelclass.parameter.StoreDetailsParameter;
import com.cwt.phonerepair.modelclass.response.allStores.AllStoreModel;
import com.cwt.phonerepair.modelclass.response.allStores.AllStoresResponse;
import com.cwt.phonerepair.modelclass.response.home.HomeStoreModel;
import com.cwt.phonerepair.modelclass.response.storedetails.StoreDetailsModel;
import com.cwt.phonerepair.modelclass.response.storedetails.StoreDetailsProductModel;
import com.cwt.phonerepair.modelclass.response.storedetails.StoreDetailsResponse;
import com.cwt.phonerepair.modelclass.response.subscriptionPlan.SubscriptionPlanModel;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;
import com.squareup.picasso.Picasso;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    SpringDotsIndicator dotsIndicator;
    TextView tvSeeAll,tvStoreDetails,tvAddress,tvStoreName;
    ViewPager view_pager;
    StoreDetailsViewPagerAdapter adapter;
    RecyclerView rv_Prodcut;
    Context context;
    ArrayList<StoreDetailsProductModel> modelArrayList;
    ImageView ivBackStoreDetail,ivStore;


    ArrayList<AllStoreModel> storeArrayList;
    int storeId;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;
    int store_Id;
    // String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);


        initView();


        getData();

        //   userId=sessionManager.getSavedUserId();
    }
    private void getData() {
        try {
            Intent intent = getIntent();
            if (intent != null) {

                store_Id = intent.getIntExtra("store_Id",0);
                System.out.println("id...store"+store_Id);

                if (Utils.checkConnection(context)) {
                    allStores();
                    storeDetails();
                } else {
                    Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void allStores() {
        Call<AllStoresResponse> call = jsonPlaceHolderApi.AllStore("Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<AllStoresResponse>() {
            @Override
            public void onResponse(Call<AllStoresResponse> call, Response<AllStoresResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        storeArrayList= (ArrayList<AllStoreModel>) response.body().getData().getStore();
                        StoreDetailsViewPagerAdapter allStoresAdapter=new StoreDetailsViewPagerAdapter(StoreDetailsActivity.this,storeArrayList);
                        view_pager.setAdapter(allStoresAdapter);
                        dotsIndicator.setViewPager(view_pager);
                    }
                }
            }

            @Override
            public void onFailure(Call<AllStoresResponse> call, Throwable t) {
                Toast.makeText(StoreDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void storeDetails() {
        Customprogress.showPopupProgressSpinner(context,true);
        StoreDetailsParameter storeDetailsParameter= new StoreDetailsParameter();
        storeDetailsParameter.setStoreId(store_Id);


        Call<StoreDetailsResponse> call=jsonPlaceHolderApi.StoreDetails(storeDetailsParameter,"Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<StoreDetailsResponse>() {
            @Override
            public void onResponse(Call<StoreDetailsResponse> call, Response<StoreDetailsResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);

                if (response.isSuccessful()){
                    if (response.body().getStatus()){
                        Customprogress.showPopupProgressSpinner(context, false);
                        Log.d("TAG","status"+response.body().getStatus());


                        tvStoreName.setText(response.body().getData().getStore().getStoreName());
                        tvStoreDetails.setText(response.body().getData().getStore().getAboutStore());
                        tvAddress.setText(response.body().getData().getStore().getAddress());



                        modelArrayList= (ArrayList<StoreDetailsProductModel>) response.body().getProduct();

                        ProdcutAdapter adapter=new ProdcutAdapter(modelArrayList,context);
                            rv_Prodcut.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                            rv_Prodcut.setAdapter(adapter);
                            rv_Prodcut.setHasFixedSize(true);



                    }else{
                        Log.d("TAG","status>>>>"+response.body().getStatus());
                    }
                }

            }

            @Override
            public void onFailure(Call<StoreDetailsResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);

                Toast.makeText(StoreDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initView() {
        jsonPlaceHolderApi= ApiUtils.getAPIService();
        context=StoreDetailsActivity.this;
        modelArrayList=new ArrayList<>() ;
        storeArrayList=new ArrayList<>() ;
        sessionManager= new SessionManager(context);
        dotsIndicator =  findViewById(R.id.dots_indicator);
        view_pager =  findViewById(R.id.view_pager);
        rv_Prodcut=findViewById(R.id.rv_Prodcut);
        ivBackStoreDetail=findViewById(R.id.ivBackStoreDetail);
        tvSeeAll=findViewById(R.id.tvSeeAll);
        tvStoreName=findViewById(R.id.tvStoreName);
        tvAddress=findViewById(R.id.tvAddress);
        tvStoreDetails=findViewById(R.id.tvStoreDetails);
        tvStoreName=findViewById(R.id.tvStoreName);
        //  ivStore=findViewById(R.id.ivStore);
        ivBackStoreDetail.setOnClickListener(this);
        tvSeeAll.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ivBackStoreDetail:
                onBackPressed();
                break;

            case R.id.tvSeeAll:
                Intent intent = new Intent(StoreDetailsActivity.this, AllProductActivity.class);
                intent.putExtra("store_Id", store_Id);


                startActivity(intent);
                break;

        }

    }
}