package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.modelclass.parameter.ReviewParameter;
import com.cwt.phonerepair.modelclass.response.getStoreallProdcut.GetStoreAllProdcutModel;
import com.cwt.phonerepair.modelclass.response.review.ReviewReponse;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateUsActivity extends AppCompatActivity implements View.OnClickListener {

    RatingBar rbRateUs;
    Button btnSubmit;
    EditText etReview;

    Context context;
    GetStoreAllProdcutModel prodcutModel;

    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;
    ArrayList<GetStoreAllProdcutModel> getStoreAllProdcutModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        initView();
        getData();

    }

    private void getData() {
        try {
            Intent intent = getIntent();
            if (intent != null) {

                prodcutModel = (GetStoreAllProdcutModel) intent.getSerializableExtra("data");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {

        jsonPlaceHolderApi= ApiUtils.getAPIService();
        context=RateUsActivity.this;
        sessionManager= new SessionManager(context);
        getStoreAllProdcutModels=new ArrayList<>();

        rbRateUs=findViewById(R.id.rbRateUs);
        btnSubmit=findViewById(R.id.btnSubmit);
        etReview=findViewById(R.id.etReview);

        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnSubmit:
                reviews();
                break;
            default:
                break;

        }


    }

    //.................Api Review.................//


    private void reviews() {
        String productId=prodcutModel.getId().toString();
        System.out.println("pro_id...Rateus..."+prodcutModel.getId());

        String userId=prodcutModel.getUserId().toString();
        System.out.println("user_Id...Rateus..."+prodcutModel.getUserId());

        String storeId=prodcutModel.getStoreId().toString();
        System.out.println("store_Id...Rateus..."+prodcutModel.getStoreId());

        Float ratingNumber = rbRateUs.getRating();
         String review=etReview.getText().toString();

        Customprogress.showPopupProgressSpinner(context,true);
        ReviewParameter add = new ReviewParameter(productId,userId,storeId,review,ratingNumber.toString());

        Call<ReviewReponse> call=jsonPlaceHolderApi.Reviews(add,"Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<ReviewReponse>() {
            @Override
            public void onResponse(Call<ReviewReponse> call, Response<ReviewReponse> response) {
                Customprogress.showPopupProgressSpinner(context,false);

                if (response.isSuccessful()){

                    if (response.body().getStatus()){

                        Intent intent=new Intent(RateUsActivity.this,DashboardActivity.class);
                        startActivity(intent);

                    }

                    else{
                        Toast.makeText(RateUsActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ReviewReponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context,false);

            }
        });

    }

}