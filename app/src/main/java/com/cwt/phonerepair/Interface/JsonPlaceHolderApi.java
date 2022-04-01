package com.cwt.phonerepair.Interface;



import com.cwt.phonerepair.modelclass.parameter.SendOtpParameter;
import com.cwt.phonerepair.modelclass.parameter.SignupParameter;
import com.cwt.phonerepair.modelclass.response.SendOtpResponse;
import com.cwt.phonerepair.modelclass.response.SignupResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {


   /* @POST("Authenticate/MeetingDevice")
    Call<LoginResponse> login(@Body LoginParams loginParams);*/

    @POST("register")
    Call<SignupResponse> Signup(@Body SignupParameter parameter);

    @POST("sendotp")
    Call<SendOtpResponse> SendOtp(@Body SendOtpParameter parameter);

}
