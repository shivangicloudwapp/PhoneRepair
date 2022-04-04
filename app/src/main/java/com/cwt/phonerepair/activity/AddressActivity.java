package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.cwt.phonerepair.adapter.BannerAdapter;
import com.cwt.phonerepair.adapter.GetAddressAdapter;
import com.cwt.phonerepair.modelclass.response.AddAddressModel;
import com.cwt.phonerepair.modelclass.response.GetAddressModel;
import com.cwt.phonerepair.modelclass.response.GetAddressResponse;
import com.cwt.phonerepair.modelclass.response.HomeBannerModel;
import com.cwt.phonerepair.modelclass.response.HomeResponse;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAddaddress,btnSubmit;

    ImageView ivBackAddress;


    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;
    Context context;
    RecyclerView rvGetAddress;
    ArrayList<GetAddressModel> addressModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initView();


    }

    private void initView() {
        context=AddressActivity.this;
        jsonPlaceHolderApi= ApiUtils.getAPIService();
        sessionManager= new SessionManager(this);
        addressModels= new ArrayList<>();

        btnAddaddress=findViewById(R.id.btnAddaddress);
        btnSubmit=findViewById(R.id.btnSubmit);
        ivBackAddress=findViewById(R.id.ivBackAddress);
        rvGetAddress=findViewById(R.id.rvGetAddress);



        btnAddaddress.setOnClickListener(this);
        ivBackAddress.setOnClickListener(this);


        getAddress();



    }

    private void getAddress() {

        Customprogress.showPopupProgressSpinner(context,true);
        Call<GetAddressResponse> call=jsonPlaceHolderApi.GetAddress("Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<GetAddressResponse>() {
            @Override
            public void onResponse(retrofit2.Call<GetAddressResponse> call, Response<GetAddressResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {


                        addressModels= (ArrayList<GetAddressModel>) response.body().getData().getAddress();
                        GetAddressAdapter adapter = new GetAddressAdapter( addressModels,context);
                        rvGetAddress.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        rvGetAddress.setAdapter( adapter);
                        rvGetAddress.setHasFixedSize(true);

                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<GetAddressResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Toast.makeText(AddressActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddaddress:
                Intent intent =new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivity(intent);
                break;

            case R.id.ivBackAddress:
                onBackPressed();
                break;




        }




    }
}