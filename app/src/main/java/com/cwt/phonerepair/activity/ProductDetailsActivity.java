package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import com.cwt.phonerepair.adapter.AllProductAdapter;
import com.cwt.phonerepair.adapter.ProductdetailViewPagerAdapter;
import com.cwt.phonerepair.modelclass.parameter.AddtoCartParameter;
import com.cwt.phonerepair.modelclass.parameter.GetProductParameter;
import com.cwt.phonerepair.modelclass.parameter.GetStoreAllProdcutParameter;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementModel;
import com.cwt.phonerepair.modelclass.response.cart.addcart.AddtoCartResponse;
import com.cwt.phonerepair.modelclass.response.getStoreallProdcut.GetStoreAllProdcutModel;
import com.cwt.phonerepair.modelclass.response.getStoreallProdcut.GetStoreAllProductResponse;
import com.cwt.phonerepair.modelclass.response.getproduct.GetProductModel;
import com.cwt.phonerepair.modelclass.response.getproduct.GetProductReponse;
import com.cwt.phonerepair.modelclass.response.home.HomeStoreModel;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;

    DotsIndicator dotsIndicator;
    ViewPager view_pager;
    RecyclerView rv_Prodcut,rvCustomerFeedback;

    TextView tvSeeAll,tvproductName,tvPrice,tvProductHighLight,tvCustomerFeedback;
    ImageView ivBackProDetail,image_view;
    Button btnAddtocart;

    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;

    GetStoreAllProdcutModel prodcutModel;
    ArrayList <ProductManagementModel> productManagementModel;
    ArrayList<GetProductModel>getProductModelArrayList;
    ArrayList<GetStoreAllProdcutModel> getStoreAllProdcutModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        initView();
        getData();
    }

    private void getData() {
        try {
            Intent intent = getIntent();
            if (intent != null) {

                prodcutModel = (GetStoreAllProdcutModel) intent.getSerializableExtra("data");

                if (Utils.checkConnection(context)) {

                    productDetails();

                    allProdcuts();

                } else {
                    Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {

        jsonPlaceHolderApi= ApiUtils.getAPIService();
        context=ProductDetailsActivity.this;
        sessionManager= new SessionManager(context);

        getProductModelArrayList=new ArrayList<>() ;
        getStoreAllProdcutModels=new ArrayList<>() ;
        productManagementModel=new ArrayList<>();

          dotsIndicator = (DotsIndicator) findViewById(R.id.dots_indicator);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
       rv_Prodcut=findViewById(R.id.rv_Prodcut);
        btnAddtocart=findViewById(R.id.btnAddtocart);
        ivBackProDetail=findViewById(R.id.ivBackProDetail);
        tvSeeAll=findViewById(R.id.tvSeeAll);
        tvproductName=findViewById(R.id.tvproductName);
        tvPrice=findViewById(R.id.tvPrice);
        image_view=findViewById(R.id.image_view);
        tvProductHighLight=findViewById(R.id.tvProductHighLight);
        rvCustomerFeedback=findViewById(R.id.rvCustomerFeedback);
        tvCustomerFeedback=findViewById(R.id.tvCustomerFeedback);


        btnAddtocart.setOnClickListener(this);
        ivBackProDetail.setOnClickListener(this);
        tvSeeAll.setOnClickListener(this);
        tvCustomerFeedback.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivBackProDetail:
                onBackPressed();
                break;
           /* case R.id.tvSeeAll:
                Intent intent = new Intent(ProductDetailsActivity.this,AllProductActivity.class);
                intent.putExtra("store_Id",homeStoreModel);
                System.out.println("store_Id...seeAll..Product...."+homeStoreModel);
                break;*/
            case R.id.tvCustomerFeedback:
                Intent intent1 = new Intent(ProductDetailsActivity.this,RateUsActivity.class);
                intent1.putExtra("data",prodcutModel);
                System.out.println("product_id...."+prodcutModel);
                startActivity(intent1);
                break;
            case R.id.btnAddtocart:
                addToCart();
                /*
                Intent intent1 = new Intent(ProductDetailsActivity.this,CartActivity.class);
                startActivity(intent1);*/
                break;

        }

    }


    //..................allProdcuts Api Call....................//

    private void allProdcuts() {

        Customprogress.showPopupProgressSpinner(context,true);
        GetStoreAllProdcutParameter add = new GetStoreAllProdcutParameter();
        add.setStoreId(prodcutModel.getStoreId());
        System.out.println("Store...Id...Product..all"+prodcutModel.getStoreId());
        Call<GetStoreAllProductResponse> call=jsonPlaceHolderApi.GetStoreAllProduct("Bearer "+sessionManager.getSavedToken(),add);
        call.enqueue(new Callback<GetStoreAllProductResponse>() {
            @Override
            public void onResponse(Call<GetStoreAllProductResponse> call, Response<GetStoreAllProductResponse> response) {
                Customprogress.showPopupProgressSpinner(context,false);

                if (response.isSuccessful()){

                    if (response.body().getStatus()){

                        getStoreAllProdcutModels= (ArrayList<GetStoreAllProdcutModel>) response.body().getData().getProduct();
                        AllProductAdapter adapter=new AllProductAdapter(getStoreAllProdcutModels, ProductDetailsActivity.this);
                        rv_Prodcut.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                        rv_Prodcut.setAdapter(adapter);
                        rv_Prodcut.setHasFixedSize(true);

                    }
                }

            }

            @Override
            public void onFailure(Call<GetStoreAllProductResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context,false);

            }
        });

    }


    //..................ProductDetails Api Call....................//

    private void productDetails() {

        GetProductParameter getProductParameter= new GetProductParameter();
        getProductParameter.setProductId(prodcutModel.getId());

        //   System.out.println("Store...Id...Product..all"+homeStoreModel.getId());

        Call<GetProductReponse> call=jsonPlaceHolderApi.GetProduct(getProductParameter,"Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<GetProductReponse>() {
            @Override
            public void onResponse(Call<GetProductReponse> call, Response<GetProductReponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus()){

                        tvproductName.setText(response.body().getData().getProduct().getTitle());
                        tvPrice.setText(response.body().getData().getProduct().getPrice().toString());
                        tvProductHighLight.setText(response.body().getData().getProduct().getDiscription());


                        //....................Convert ComaSeprated Image Into List.............//

                        String image=response.body().getData().getProduct().getProductImage();
                        List<String> result = Arrays.asList(image.split("\\s*,\\s*",4));

                        System.out.println("productImg....."+result);

                        ProductdetailViewPagerAdapter productdetailViewPagerAdapter=new ProductdetailViewPagerAdapter(ProductDetailsActivity.this,result);
                        view_pager.setAdapter(productdetailViewPagerAdapter);
                        dotsIndicator.setViewPager(view_pager);

                    }
                }

            }

            @Override
            public void onFailure(Call<GetProductReponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context,false);

            }
        });



    }


    //..................Add To cart....................//

    private void addToCart()  {

        Customprogress.showPopupProgressSpinner(context,true);
        AddtoCartParameter add = new AddtoCartParameter();
        add.setProductId(prodcutModel.getId());
        System.out.println("productId...forCart..."+prodcutModel.getId());

        //............................ When Add To cart Qty of Product Default 1 ..........................//
        add.setQty("1");


        Call<AddtoCartResponse> call=jsonPlaceHolderApi.AddtoCart(add,"Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<AddtoCartResponse>() {
            @Override
            public void onResponse(Call<AddtoCartResponse> call, Response<AddtoCartResponse> response) {
                Customprogress.showPopupProgressSpinner(context,false);

                if (response.isSuccessful()){
                    if (response.body().getStatus()){
                        Intent intent= new Intent(ProductDetailsActivity.this,CartActivity.class);
                        startActivity(intent);
                    }

                    else{
                        Toast.makeText(ProductDetailsActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<AddtoCartResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context,false);

            }
        });

    }





}