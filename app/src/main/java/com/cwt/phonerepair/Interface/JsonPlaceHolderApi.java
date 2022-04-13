package com.cwt.phonerepair.Interface;



import com.cwt.phonerepair.modelclass.parameter.AddAddressParameter;
import com.cwt.phonerepair.modelclass.parameter.AddtoCartParameter;
import com.cwt.phonerepair.modelclass.parameter.GetProductParameter;
import com.cwt.phonerepair.modelclass.parameter.GetStoreAllProdcutParameter;
import com.cwt.phonerepair.modelclass.parameter.LoginParameter;
import com.cwt.phonerepair.modelclass.parameter.PlaceOrderParameter;
import com.cwt.phonerepair.modelclass.parameter.ReviewParameter;
import com.cwt.phonerepair.modelclass.parameter.SendOtpParameter;
import com.cwt.phonerepair.modelclass.parameter.SignupParameter;
import com.cwt.phonerepair.modelclass.parameter.StoreDetailsParameter;
import com.cwt.phonerepair.modelclass.parameter.VerifyOtpParameter;
import com.cwt.phonerepair.modelclass.response.AddProduct.AddProductResponse;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementResponse;
import com.cwt.phonerepair.modelclass.response.PlaceOrderResponse;
import com.cwt.phonerepair.modelclass.response.addAddress.AddAddressResponse;
import com.cwt.phonerepair.modelclass.response.allStores.AllStoresResponse;
import com.cwt.phonerepair.modelclass.response.cart.addcart.AddtoCartResponse;
import com.cwt.phonerepair.modelclass.response.cart.gettocart.GetToCartReponse;
import com.cwt.phonerepair.modelclass.response.cart.updatecart.UpdateCartReponse;
import com.cwt.phonerepair.modelclass.response.getAddress.GetAddressResponse;
import com.cwt.phonerepair.modelclass.response.getStoreallProdcut.GetStoreAllProductResponse;
import com.cwt.phonerepair.modelclass.response.getproduct.GetProductReponse;
import com.cwt.phonerepair.modelclass.response.home.HomeResponse;
import com.cwt.phonerepair.modelclass.response.login.LoginResponse;
import com.cwt.phonerepair.modelclass.response.review.ReviewReponse;
import com.cwt.phonerepair.modelclass.response.sbscriptionstore.SubscriptionStoreResponse;
import com.cwt.phonerepair.modelclass.response.sendOtp.SendOtpResponse;
import com.cwt.phonerepair.modelclass.response.signup.SignupResponse;
import com.cwt.phonerepair.modelclass.response.storedetails.StoreDetailsResponse;
import com.cwt.phonerepair.modelclass.response.subscriptionPlan.SubscriptionPlanResponse;
import com.cwt.phonerepair.modelclass.response.updateProfile.UpdateProfileResponse;
import com.cwt.phonerepair.modelclass.service.ServiceResponse;
import com.cwt.phonerepair.modelclass.storeorder.StoreOrderResponse;
import com.cwt.phonerepair.modelclass.verifyOtp.VerifyOtpResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface JsonPlaceHolderApi {


    @POST("register")
    Call<SignupResponse> Signup(@Body SignupParameter parameter);

    @POST("sendotp")
    Call<SendOtpResponse> SendOtp(@Body SendOtpParameter parameter);


    @POST("verify")
    Call<VerifyOtpResponse> VerifyOtp(@Body VerifyOtpParameter parameter);



    @POST("login")
    Call<LoginResponse> Login(@Body LoginParameter parameter);

    @POST("user/home")
    Call<HomeResponse> Home(@Header("Authorization") String header);



    @POST("user/all-store")
    Call<AllStoresResponse> AllStore(@Header("Authorization") String header);



    @Multipart
    @POST("user/updateprofile")
    Call<UpdateProfileResponse> UpdateProfile(@Header("Authorization") String header,
                                              @PartMap Map<String,RequestBody> name,
                                              @Part MultipartBody.Part image);



    @POST("user/add-address")
    Call<AddAddressResponse> AddAddress(@Body AddAddressParameter parameter,
                                        @Header("Authorization") String header );

    @POST("user/get-address")
    Call<GetAddressResponse> GetAddress(@Header("Authorization") String header );



    @POST("user/subscription-plan")
    Call<SubscriptionPlanResponse> SubScriptionPlan(@Header("Authorization") String header );



    @Multipart
    @POST("user/subscription-store")
    Call<SubscriptionStoreResponse> SubscriptionStore(@Header("Authorization") String header,
                                                      @PartMap Map<String,RequestBody>data,
                                                      @Part List<MultipartBody.Part> plan_image,
                                                      @Part List<MultipartBody.Part> store_image);


    @POST("user/store-detail")
    Call<StoreDetailsResponse> StoreDetails(@Body StoreDetailsParameter parameter,
                                            @Header("Authorization") String header );


    @Multipart
    @POST("user/add-product")
    Call<AddProductResponse> AddProduct(@Header("Authorization") String header,
                                        @PartMap Map<String,RequestBody>data,
                                        @Part List<MultipartBody.Part> product_image);


    @POST("user/services")
    Call<ServiceResponse> Services(@Header("Authorization") String header);


    @POST("user/store-order")
    Call<StoreOrderResponse> StoreOrder(@Header("Authorization") String header );


    @POST("user/get-product")
    Call<GetProductReponse> GetProduct(@Body GetProductParameter parameter,
                                       @Header("Authorization") String header );



    @POST("user/review")
    Call<ReviewReponse> Reviews(@Body ReviewParameter parameter,
                                   @Header("Authorization") String header );



    @POST("user/get-to-cart")
    Call<GetToCartReponse> GetToCart(@Header("Authorization") String header );





    @POST("user/product-management")
    Call<ProductManagementResponse> ProductManagement(@Header("Authorization") String header );




    @POST("user/getstore-allproduct")
    Call<GetStoreAllProductResponse> GetStoreAllProduct(@Header("Authorization") String header,
                                                        @Body GetStoreAllProdcutParameter parameter);






    @POST("user/add-to-cart")
    Call<AddtoCartResponse> AddtoCart(@Body AddtoCartParameter parameter,
                                      @Header("Authorization") String header );
    @Multipart
    @POST("user/update-cart")
    Call<UpdateCartReponse> UpdateCart(@Header("Authorization") String header,
                                       @PartMap Map<String,RequestBody>data);


    @POST("user/place-order")
    Call<PlaceOrderResponse> PlaceOrder(@Body PlaceOrderParameter parameter,
                                        @Header("Authorization") String header );

/*
    servicelist
    getstore-allproduct*/

}





