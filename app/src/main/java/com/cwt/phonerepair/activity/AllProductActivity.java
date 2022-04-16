package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.adapter.AllProductAdapter;
import com.cwt.phonerepair.adapter.ProdcutAdapter;
import com.cwt.phonerepair.modelclass.parameter.AddtoCartParameter;
import com.cwt.phonerepair.modelclass.parameter.GetStoreAllProdcutParameter;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementModel;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementResponse;
import com.cwt.phonerepair.modelclass.response.getStoreallProdcut.GetStoreAllProdcutModel;
import com.cwt.phonerepair.modelclass.response.getStoreallProdcut.GetStoreAllProductResponse;
import com.cwt.phonerepair.modelclass.response.home.HomeStoreModel;
import com.cwt.phonerepair.modelclass.response.storedetails.StoreDetailsModel;
import com.cwt.phonerepair.modelclass.response.storedetails.StoreDetailsProductModel;
import com.cwt.phonerepair.storeactivity.ProductManagementActivity;
import com.cwt.phonerepair.storeadapter.ProductManagementAdapter;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllProductActivity extends AppCompatActivity implements View.OnClickListener {


    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;

    RecyclerView rvAllPro;
    Context context;
    ImageView ivBackAllPro;

    ArrayList<GetStoreAllProdcutModel> modelArrayList;
    ArrayList<StoreDetailsProductModel> storeDetailsProductModels;
    int store_Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);

    initView();



        getData();

    }


    //...........getData For GetId From Privious Screen.............//

    private void getData() {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                store_Id =  intent.getIntExtra("store_Id",0);

                //.................Api...AllProduct................//

                if (Utils.checkConnection(context)) {
                    AllProduct();
                } else {
                    Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initView() {

        context=this;
        jsonPlaceHolderApi= ApiUtils.getAPIService();
        sessionManager=new SessionManager(context);

        modelArrayList=new ArrayList<>();
        storeDetailsProductModels=new ArrayList<>();

        rvAllPro=findViewById(R.id.rvAllPro);
        ivBackAllPro=findViewById(R.id.ivBackAllPro);

        ivBackAllPro.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if (v==ivBackAllPro){
            onBackPressed();
        }

    }




    //.................Api...AllProduct................//


    private void AllProduct() {

        Customprogress.showPopupProgressSpinner(context,true);
        GetStoreAllProdcutParameter add = new GetStoreAllProdcutParameter();
        add.setStoreId(store_Id);

        Call<GetStoreAllProductResponse> call=jsonPlaceHolderApi.GetStoreAllProduct("Bearer "+sessionManager.getSavedToken(),add);
        call.enqueue(new Callback<GetStoreAllProductResponse>() {
            @Override
            public void onResponse(Call<GetStoreAllProductResponse> call, Response<GetStoreAllProductResponse> response) {
                Customprogress.showPopupProgressSpinner(context,false);

                if (response.isSuccessful()){
                    if (response.body().getStatus()){
                        modelArrayList= (ArrayList<GetStoreAllProdcutModel>) response.body().getData().getProduct();
                        AllProductAdapter adapter=new AllProductAdapter(modelArrayList, AllProductActivity.this);
                        rvAllPro.setLayoutManager(new GridLayoutManager(AllProductActivity.this, 2));
                        rvAllPro.setAdapter(adapter);
                        rvAllPro.setHasFixedSize(true);

                    }

                    else {
                        Toast.makeText(AllProductActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<GetStoreAllProductResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context,false);

            }
        });

    }




}