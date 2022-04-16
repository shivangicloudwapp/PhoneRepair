package com.cwt.phonerepair.storeactivity;

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
import com.cwt.phonerepair.modelclass.ProductModel;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementModel;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementResponse;
import com.cwt.phonerepair.modelclass.response.storedetails.StoreDetailsProductModel;
import com.cwt.phonerepair.modelclass.response.subscriptionPlan.SubscriptionPlanModel;
import com.cwt.phonerepair.storeadapter.ProductManagementAdapter;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductManagementActivity extends AppCompatActivity implements View.OnClickListener {


    RecyclerView rvAllPro;
    Context context;
    ArrayList<ProductManagementModel> modelArrayList;
    ImageView ivBackAllPro;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);

        initView();



    }




    private void productManagement() {
        Customprogress.showPopupProgressSpinner(context,true);
        Call<ProductManagementResponse>call=jsonPlaceHolderApi.ProductManagement("Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<ProductManagementResponse>() {
            @Override
            public void onResponse(Call<ProductManagementResponse> call, Response<ProductManagementResponse> response) {
                Customprogress.showPopupProgressSpinner(context,false);

                if (response.isSuccessful()){

                    if (response.body().getStatus()){

                        modelArrayList= (ArrayList<ProductManagementModel>) response.body().getData().getProduct();

              ProductManagementAdapter adapter=new ProductManagementAdapter(modelArrayList,ProductManagementActivity.this);
        rvAllPro.setLayoutManager(new GridLayoutManager(ProductManagementActivity.this, 2));
        rvAllPro.setAdapter(adapter);
        rvAllPro.setHasFixedSize(true);

                     }

                    else{
                        Toast.makeText(ProductManagementActivity.this, "Please Check Network Connection", Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<ProductManagementResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context,false);

            }
        });





    }

    private void initView() {

        context=this;
        modelArrayList=new ArrayList<>();
        jsonPlaceHolderApi= ApiUtils.getAPIService();

        sessionManager=new SessionManager(context);

        rvAllPro=findViewById(R.id.rvAllPro);
        ivBackAllPro=findViewById(R.id.ivBackAllPro);
        ivBackAllPro.setOnClickListener(this);

        if (Utils.checkConnection(context)) {
            productManagement();

        } else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        if (v==ivBackAllPro){
            onBackPressed();
        }

    }
}