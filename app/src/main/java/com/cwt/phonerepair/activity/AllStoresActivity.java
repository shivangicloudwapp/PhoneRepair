package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.adapter.AllStoresAdapter;
import com.cwt.phonerepair.modelclass.response.AllStoreModel;
import com.cwt.phonerepair.modelclass.response.AllStoresResponse;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllStoresActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView  rvAllStores;
    Context context;
    ImageView ivBackAllStores;
    ArrayList<AllStoreModel> storeArrayList;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_stores);

        initView();




    }

    private void initView() {
        context=AllStoresActivity.this;
        storeArrayList=new ArrayList<>();
        jsonPlaceHolderApi= ApiUtils.getAPIService();
        sessionManager = new SessionManager(context);
        rvAllStores=findViewById(R.id.rvAllStores);
        ivBackAllStores=findViewById(R.id.ivBackAllStores);

        ivBackAllStores.setOnClickListener(this);

        allStores();


    }

    private void allStores() {

        Customprogress.showPopupProgressSpinner(context, true);
        Call<AllStoresResponse> call = jsonPlaceHolderApi.AllStore("Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<AllStoresResponse>() {
            @Override
            public void onResponse(Call<AllStoresResponse> call, Response<AllStoresResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {


                        storeArrayList= (ArrayList<AllStoreModel>) response.body().getData().getStore();

                        AllStoresAdapter allStoresAdapter=new AllStoresAdapter(storeArrayList,AllStoresActivity.this);
                        rvAllStores.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        rvAllStores.setAdapter(allStoresAdapter);
                        rvAllStores.setHasFixedSize(true);


                    }
                }
            }

            @Override
            public void onFailure(Call<AllStoresResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Toast.makeText(AllStoresActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v==ivBackAllStores){
            onBackPressed();
        }

    }
}