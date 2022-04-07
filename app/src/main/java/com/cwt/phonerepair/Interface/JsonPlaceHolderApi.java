package com.cwt.phonerepair.Interface;



import com.cwt.phonerepair.modelclass.parameter.AddAddressParameter;
import com.cwt.phonerepair.modelclass.parameter.AddtoCartParameter;
import com.cwt.phonerepair.modelclass.parameter.GetStoreAllProdcutParameter;
import com.cwt.phonerepair.modelclass.parameter.LoginParameter;
import com.cwt.phonerepair.modelclass.parameter.SendOtpParameter;
import com.cwt.phonerepair.modelclass.parameter.SignupParameter;
import com.cwt.phonerepair.modelclass.parameter.StoreDetailsParameter;
import com.cwt.phonerepair.modelclass.parameter.VerifyOtpParameter;
import com.cwt.phonerepair.modelclass.response.AddProduct.AddProductResponse;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementResponse;
import com.cwt.phonerepair.modelclass.response.addAddress.AddAddressResponse;
import com.cwt.phonerepair.modelclass.response.allStores.AllStoresResponse;
import com.cwt.phonerepair.modelclass.response.cart.AddtoCartResponse;
import com.cwt.phonerepair.modelclass.response.getAddress.GetAddressResponse;
import com.cwt.phonerepair.modelclass.response.getStoreallProdcut.GetStoreAllProductResponse;
import com.cwt.phonerepair.modelclass.response.home.HomeResponse;
import com.cwt.phonerepair.modelclass.response.login.LoginResponse;
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



/*
    @Multipart
    @POST("User/userfeed_add")
    Call<CommonResponse> addPost(@Header("authtoken") String authtoken,
                                 @Part("user_id") RequestBody user_id,
                                 @Part("description") RequestBody description,
                                 @Part("location") RequestBody location,
                                 @Part("location_lat") RequestBody location_lat,
                                 @Part("location_long") RequestBody location_long,
                                 @Part("post_time") RequestBody post_time,
                                 @Part List<MultipartBody.Part> video);*/


    @POST("user/product-management")
    Call<ProductManagementResponse> ProductManagement(@Header("Authorization") String header );




    @POST("user/getstore-allproduct")
    Call<GetStoreAllProductResponse> GetStoreAllProduct(@Header("Authorization") String header,
                                                        @Body GetStoreAllProdcutParameter parameter);






   /* @POST("user/add-to-cart")
    Call<AddtoCartResponse> AddtoCart(@Body AddtoCartParameter parameter,
                                      @Header("Authorization") String header );
*/



}





