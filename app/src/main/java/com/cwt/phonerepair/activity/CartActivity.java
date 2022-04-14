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

import com.cwt.phonerepair.Interface.GetAddressIdInterface;
import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.Interface.UpdateCartInterface;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.adapter.CartProductListAdapter;
import com.cwt.phonerepair.adapter.GetAddressAdapter;
import com.cwt.phonerepair.adapter.GetCartAddressAdapter;
import com.cwt.phonerepair.adapter.ProdcutAdapter;
import com.cwt.phonerepair.modelclass.parameter.AddtoCartParameter;
import com.cwt.phonerepair.modelclass.response.cart.addcart.AddtoCartResponse;
import com.cwt.phonerepair.modelclass.response.cart.gettocart.GetToCartModel;
import com.cwt.phonerepair.modelclass.response.cart.gettocart.GetToCartReponse;
import com.cwt.phonerepair.modelclass.response.cart.updatecart.UpdateCartReponse;
import com.cwt.phonerepair.modelclass.response.getAddress.GetAddressModel;
import com.cwt.phonerepair.modelclass.response.getAddress.GetAddressResponse;
import com.cwt.phonerepair.modelclass.response.storedetails.StoreDetailsProductModel;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBackCart;
    Button btnOrderNow;

    Context context;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;
   List<GetToCartModel> modelArrayList;
    GetToCartModel getToCartModelMain;
    RecyclerView rvCartProductList,rvAddress;
TextView tvTotalAmount;
    UpdateCartInterface updateCartInterface;
    ArrayList<GetAddressModel> addressModels;
    GetAddressModel getAddressModelMain;

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
        rvAddress=findViewById(R.id.rvAddress);
        jsonPlaceHolderApi= ApiUtils.getAPIService();
        sessionManager= new SessionManager(context);

        modelArrayList=new ArrayList<>();
        addressModels=new ArrayList<>();

        ivBackCart.setOnClickListener(this);
        btnOrderNow.setOnClickListener(this);

        if (Utils.checkConnection(context)) {

            Cart();
            Address();
        } else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }



    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ivBackCart:
                onBackPressed();
                break;

            case R.id.btnOrderNow:

                Intent intent= new Intent(CartActivity.this,OrderPlacedSuccessActivity.class);
                intent.putExtra("data", (Serializable) getAddressModelMain);

                startActivity(intent);
                break;
        }


    }



    private void Cart() {
        Call<GetToCartReponse> call=jsonPlaceHolderApi.GetToCart("Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<GetToCartReponse>() {
            @Override
            public void onResponse(Call<GetToCartReponse> call, Response<GetToCartReponse> response) {

                if (response.isSuccessful()){
                    if (response.body().getStatus()){
                        modelArrayList=  response.body().getData().getCart();

                        CartProductListAdapter adapter=new CartProductListAdapter(modelArrayList, context, new UpdateCartInterface() {
                            @Override
                            public void getUpdateCart(GetToCartModel getToCartModel,String action) {
                                action="add";
                                if (action.equals("add")){
                                    System.out.println("plan....."+getToCartModel.getId());
                                    getToCartModelMain=getToCartModel;

                                    updateCart("add");
                                }
                                else {
                                    System.out.println("plan....."+getToCartModel.getId());
                                    getToCartModelMain=getToCartModel;

                                    updateCart("sub");
                                }

                            }

                        });

                        rvCartProductList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        rvCartProductList.setAdapter(adapter);
                        rvCartProductList.setHasFixedSize(true);
                        double total=0.0;

                        for (int i=0;i<modelArrayList.size();i++){

                            total=total+(Double.parseDouble(modelArrayList.get(i).getQty())*Double.parseDouble(modelArrayList.get(i).getPrice().toString()));

                        }

                        tvTotalAmount.setText(String.valueOf(total));

                    }
                }

            }




            @Override
            public void onFailure(Call<GetToCartReponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context,false);

            }
        });


    }
    private void Address() {

        Customprogress.showPopupProgressSpinner(context,true);
        Call<GetAddressResponse> call=jsonPlaceHolderApi.GetAddress("Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<GetAddressResponse>() {
            @Override
            public void onResponse(retrofit2.Call<GetAddressResponse> call, Response<GetAddressResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {


                        addressModels= (ArrayList<GetAddressModel>) response.body().getData().getAddress();
                        GetCartAddressAdapter adapter = new GetCartAddressAdapter(addressModels, context, new GetAddressIdInterface() {
                            @Override
                            public void getAddressId(GetAddressModel getAddressModel) {
                                getAddressModelMain=getAddressModel;
                            }
                        });
                        rvAddress.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        rvAddress.setAdapter( adapter);
                        rvAddress.setHasFixedSize(true);

                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<GetAddressResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });





    }

    private void updateCart(String action) {
        HashMap<String, RequestBody> data = new HashMap<>();
        data.put("cart_id", createRequestBody(getToCartModelMain.getId().toString()));
        data.put("action", createRequestBody (action));


        Customprogress.showPopupProgressSpinner(context,true);
        Call<UpdateCartReponse> call=jsonPlaceHolderApi.UpdateCart("Bearer "+sessionManager.getSavedToken(),data);
        call.enqueue(new Callback<UpdateCartReponse>() {
            @Override
            public void onResponse(Call<UpdateCartReponse> call, Response<UpdateCartReponse> response) {
                Customprogress.showPopupProgressSpinner(context,false);

                if (response.isSuccessful()){
                    if (response.body().getStatus()){
                        Cart();
                    }

                    else {
                        Toast.makeText(CartActivity.this, "Check Network Connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<UpdateCartReponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context,false);

            }
        });


    }


    private RequestBody createRequestBody(String s) {

        return RequestBody.create(MediaType.parse("multipart/form-data"), s);

    }

}