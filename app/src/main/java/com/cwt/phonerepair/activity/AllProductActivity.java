package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.adapter.AllProductAdapter;
import com.cwt.phonerepair.adapter.ProdcutAdapter;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementModel;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementResponse;
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

    RecyclerView rvAllPro;
    Context context;
    ImageView ivBackAllPro;
    ArrayList<ProductManagementModel> modelArrayList;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);


    initView();


      //  modelArrayList=new ArrayList<>() ;
        /*modelArrayList.add(new ProductModel("Iphone 13","RM400",R.drawable.iphone1));
        modelArrayList.add(new ProductModel("Iphone X","RM999",R.drawable.phone3));
        modelArrayList.add(new ProductModel("Iphone 13","RM400",R.drawable.watch));
        modelArrayList.add(new ProductModel("Iphone X","RM999",R.drawable.watch3));
        modelArrayList.add(new ProductModel("Iphone 13","RM400",R.drawable.bluetooth));
        modelArrayList.add(new ProductModel("Iphone 13","RM400",R.drawable.iphone1));
        modelArrayList.add(new ProductModel("Iphone 13","RM400",R.drawable.watch));
        modelArrayList.add(new ProductModel("Iphone 13","RM400",R.drawable.phone3));
        ProdcutAdapter adapter=new ProdcutAdapter(modelArrayList,this);
        rvAllPro.setLayoutManager(new GridLayoutManager(this, 2));
        rvAllPro.setAdapter(adapter);
        rvAllPro.setHasFixedSize(true);*/


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
            AllProduct();

        } else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void AllProduct() {


        Customprogress.showPopupProgressSpinner(context,true);
        Call<ProductManagementResponse> call=jsonPlaceHolderApi.ProductManagement("Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<ProductManagementResponse>() {
            @Override
            public void onResponse(Call<ProductManagementResponse> call, Response<ProductManagementResponse> response) {
                Customprogress.showPopupProgressSpinner(context,false);

                if (response.isSuccessful()){

                    if (response.body().getStatus()){

                        modelArrayList= (ArrayList<ProductManagementModel>) response.body().getData().getProduct();

                        AllProductAdapter adapter=new AllProductAdapter(modelArrayList, AllProductActivity.this);
                        rvAllPro.setLayoutManager(new GridLayoutManager(AllProductActivity.this, 2));
                        rvAllPro.setAdapter(adapter);
                        rvAllPro.setHasFixedSize(true);

                    }
                }


            }

            @Override
            public void onFailure(Call<ProductManagementResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context,true);

            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v==ivBackAllPro){
            onBackPressed();
        }

    }
}