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

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.adapter.CartProductListAdapter;
import com.cwt.phonerepair.adapter.ProdcutAdapter;
import com.cwt.phonerepair.modelclass.parameter.AddtoCartParameter;
import com.cwt.phonerepair.modelclass.response.cart.addcart.AddtoCartResponse;
import com.cwt.phonerepair.modelclass.response.cart.gettocart.GetToCartModel;
import com.cwt.phonerepair.modelclass.response.cart.gettocart.GetToCartReponse;
import com.cwt.phonerepair.modelclass.response.storedetails.StoreDetailsProductModel;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBackCart;
    Button btnOrderNow;

    Context context;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;
    ArrayList<GetToCartModel> modelArrayList;

    RecyclerView rvCartProductList;
TextView tvTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();

    }

    private void initView() {
        context=CartActivity.this;
        ivBackCart=findViewById(R.id.ivBackCart);
        btnOrderNow=findViewById(R.id.btnOrderNow);
        rvCartProductList=findViewById(R.id.rvCartProductList);
        tvTotalAmount=findViewById(R.id.tvTotalAmount);
        jsonPlaceHolderApi= ApiUtils.getAPIService();
        sessionManager= new SessionManager(context);

        modelArrayList=new ArrayList<>();

        ivBackCart.setOnClickListener(this);
        btnOrderNow.setOnClickListener(this);

        Cart();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ivBackCart:
                onBackPressed();
                break;

            case R.id.btnOrderNow:

                Intent intent= new Intent(CartActivity.this,OrderPlacedSuccessActivity.class);
                startActivity(intent);
                break;
        }


    }

    private void Cart() {

        Customprogress.showPopupProgressSpinner(context,true);

        Call<GetToCartReponse> call=jsonPlaceHolderApi.GetToCart("Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<GetToCartReponse>() {
            @Override
            public void onResponse(Call<GetToCartReponse> call, Response<GetToCartReponse> response) {
                Customprogress.showPopupProgressSpinner(context,false);

                if (response.isSuccessful()){
                    if (response.body().getStatus()){


                        modelArrayList= (ArrayList<GetToCartModel>) response.body().getData().getCart();

                        CartProductListAdapter adapter=new CartProductListAdapter(modelArrayList,context);

                        rvCartProductList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                        rvCartProductList.setAdapter(adapter);
                        rvCartProductList.setHasFixedSize(true);


                        //tvTotalAmount.setText(response.body().getData().getCart().get(modelArrayList.));

                    }
                }

            }

            @Override
            public void onFailure(Call<GetToCartReponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context,false);

            }
        });


    }
}