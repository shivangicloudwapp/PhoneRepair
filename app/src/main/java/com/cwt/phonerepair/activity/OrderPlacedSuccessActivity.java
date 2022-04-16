package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.modelclass.parameter.AddtoCartParameter;
import com.cwt.phonerepair.modelclass.parameter.PlaceOrderParameter;
import com.cwt.phonerepair.modelclass.response.PlaceOrderResponse;
import com.cwt.phonerepair.modelclass.response.cart.addcart.AddtoCartResponse;
import com.cwt.phonerepair.modelclass.response.getAddress.GetAddressModel;
import com.cwt.phonerepair.modelclass.response.subscriptionPlan.SubscriptionPlanModel;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderPlacedSuccessActivity extends AppCompatActivity implements View.OnClickListener {


   Context context;
    Button btnHome;

    GetAddressModel getAddressModelMain;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed_success);

        initView();
    getData();

    }

    private void getData() {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                getAddressModelMain = (GetAddressModel) intent.getSerializableExtra("data");
                System.out.println("store.Data.." + getAddressModelMain.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initView() {
        context=OrderPlacedSuccessActivity.this;
        jsonPlaceHolderApi= ApiUtils.getAPIService();
        sessionManager= new SessionManager(context);

        btnHome=findViewById(R.id.btnHome);

        btnHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnHome:
                if (Utils.checkConnection(context)) {
                    OrderPlaced();
                }
                else {
                    Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }


    }

    private void OrderPlaced()  {

        Customprogress.showPopupProgressSpinner(context,true);
        PlaceOrderParameter add = new PlaceOrderParameter();
        add.setAddressId(getAddressModelMain.getId());
        System.out.println("Address_id..."+getAddressModelMain.getId());
        Call<PlaceOrderResponse> call=jsonPlaceHolderApi.PlaceOrder(add,"Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<PlaceOrderResponse>() {
            @Override
            public void onResponse(Call<PlaceOrderResponse> call, Response<PlaceOrderResponse> response) {
                Customprogress.showPopupProgressSpinner(context,false);

                if (response.isSuccessful()){
                    if (response.body().getStatus()){
                        Intent intent =new Intent(OrderPlacedSuccessActivity.this, DashboardActivity.class);
                startActivity(intent);


                    }

                    else {
                        Toast.makeText(OrderPlacedSuccessActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<PlaceOrderResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context,false);

            }
        });

    }
}