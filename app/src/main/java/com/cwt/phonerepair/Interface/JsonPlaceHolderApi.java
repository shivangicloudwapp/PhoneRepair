package com.cwt.phonerepair.Interface;



import com.cwt.phonerepair.modelclass.parameter.AddAddressParameter;
import com.cwt.phonerepair.modelclass.parameter.LoginParameter;
import com.cwt.phonerepair.modelclass.parameter.SendOtpParameter;
import com.cwt.phonerepair.modelclass.parameter.SignupParameter;
import com.cwt.phonerepair.modelclass.parameter.StoreDetailsParameter;
import com.cwt.phonerepair.modelclass.parameter.VerifyOtpParameter;
import com.cwt.phonerepair.modelclass.response.AddAddressResponse;
import com.cwt.phonerepair.modelclass.response.AllStoresResponse;
import com.cwt.phonerepair.modelclass.response.GetAddressResponse;
import com.cwt.phonerepair.modelclass.response.HomeResponse;
import com.cwt.phonerepair.modelclass.response.LoginResponse;
import com.cwt.phonerepair.modelclass.response.SendOtpResponse;
import com.cwt.phonerepair.modelclass.response.SignupResponse;
import com.cwt.phonerepair.modelclass.response.StoreDetailsResponse;
import com.cwt.phonerepair.modelclass.response.SubscriptionPlanResponse;
import com.cwt.phonerepair.modelclass.response.SubscriptionStoreResponse;
import com.cwt.phonerepair.modelclass.response.UpdateProfileResponse;
import com.cwt.phonerepair.modelclass.response.VerifyOtpResponse;

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



}







/*
plan_title:testing title,
        plan_image:[]
        plan_duration:one year,
        plan_items:testing item,
        plan_price:500
        start_date:2-2-22,
        end_date:8-2-22,
        registration_no:5,
        address:11, bhawarkua,
        about_store:test aboutstore,
        plan_id:1
        store_name:test name
        store_image:[]
*/