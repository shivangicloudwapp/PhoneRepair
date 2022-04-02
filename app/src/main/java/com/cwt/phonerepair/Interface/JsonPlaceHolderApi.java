package com.cwt.phonerepair.Interface;



import com.cwt.phonerepair.modelclass.parameter.LoginParameter;
import com.cwt.phonerepair.modelclass.parameter.SendOtpParameter;
import com.cwt.phonerepair.modelclass.parameter.SignupParameter;
import com.cwt.phonerepair.modelclass.parameter.VerifyOtpParameter;
import com.cwt.phonerepair.modelclass.response.AllStoresResponse;
import com.cwt.phonerepair.modelclass.response.HomeResponse;
import com.cwt.phonerepair.modelclass.response.LoginResponse;
import com.cwt.phonerepair.modelclass.response.SendOtpResponse;
import com.cwt.phonerepair.modelclass.response.SignupResponse;
import com.cwt.phonerepair.modelclass.response.VerifyOtpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

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




}
