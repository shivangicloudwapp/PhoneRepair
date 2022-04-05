package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.GetSubscriptionData;
import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.adapter.SubscriptionAdapter;
import com.cwt.phonerepair.modelclass.response.SubscriptionPlanModel;
import com.cwt.phonerepair.modelclass.response.SubscriptionPlanResponse;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionPlanActivity extends AppCompatActivity implements View.OnClickListener {


    RecyclerView rvSubPlan;
    Context context;
    ArrayList<SubscriptionPlanModel> modelArrayList;
    ImageView ivBackSubPlan;

    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;

Button btnBuyMembership;
    SubscriptionPlanModel subscriptionPlanModelMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_plan);

        initView();
    }

    private void initView() {

        context=SubscriptionPlanActivity.this;
        jsonPlaceHolderApi= ApiUtils.getAPIService();
        sessionManager= new SessionManager(this);
        modelArrayList= new ArrayList<>();

        rvSubPlan=findViewById(R.id.rvSubPlan);
        ivBackSubPlan=findViewById(R.id.ivBackSubPlan);
        btnBuyMembership=findViewById(R.id.btnBuyMembership);

        ivBackSubPlan.setOnClickListener(this);
        btnBuyMembership.setOnClickListener(this);

        if (Utils.checkConnection(context)) {

            subscriptionPlan();
        } else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }

    private void subscriptionPlan() {
        Customprogress.showPopupProgressSpinner(context,true);
        Call<SubscriptionPlanResponse>call=jsonPlaceHolderApi.SubScriptionPlan("Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<SubscriptionPlanResponse>() {
            @Override
            public void onResponse(Call<SubscriptionPlanResponse> call, Response<SubscriptionPlanResponse> response) {

                if (response.isSuccessful()){
                    Customprogress.showPopupProgressSpinner(context, false);

                    if (response.body().getStatus()){



                        modelArrayList= (ArrayList<SubscriptionPlanModel>) response.body().getData();
                        SubscriptionAdapter adapter = new SubscriptionAdapter( modelArrayList,context, new GetSubscriptionData() {
                            @Override
                            public void getsubscriptiondata(SubscriptionPlanModel subscriptionPlanModel) {
                                subscriptionPlanModelMain=subscriptionPlanModel;

                            }
                        });
                        rvSubPlan.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        rvSubPlan.setAdapter( adapter);
                        rvSubPlan.setHasFixedSize(true);








                    }
                }

            }

            @Override
            public void onFailure(Call<SubscriptionPlanResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Toast.makeText(SubscriptionPlanActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.ivBackSubPlan:
                onBackPressed();
      break;
            case R.id.btnBuyMembership:
//                subscriptionPlan();

                Intent intent = new Intent(SubscriptionPlanActivity.this, SubscribeNewStoreActivity.class);
                intent.putExtra("data", (Serializable) subscriptionPlanModelMain);
                startActivity(intent);
                break;

            default:
                break;
        }




    }
}