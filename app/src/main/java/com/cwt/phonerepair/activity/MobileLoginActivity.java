package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.modelclass.parameter.SendOtpParameter;
import com.cwt.phonerepair.modelclass.response.sendOtp.SendOtpResponse;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileLoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etPhoneNum;
    Button btnSendOtp;
    ImageView ivBackSignUp;

   Context context;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;

    String PhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_login);

        initView();

    }

    private void initView() {
        context=MobileLoginActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        sessionManager=new SessionManager(context);

        etPhoneNum = findViewById(R.id.etPhoneNum);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        ivBackSignUp = findViewById(R.id.ivBackSignUp);

        btnSendOtp.setOnClickListener(this);
        ivBackSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSendOtp:
                String phoneNum = etPhoneNum.getText().toString().trim();

                if (phoneNum.isEmpty()){
                    etPhoneNum.setError("Please enter phone number");
                    etPhoneNum.requestFocus();
                    return;
                }

                else if (phoneNum.length()<9) {
                    etPhoneNum.setError("Enter enter valid 10 digit phone number");
                }

                if (Utils.checkConnection(context)) {

                    sendOtp();
                } else {
                    Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }

                break;


            case R.id.ivBackSignUp:
                onBackPressed();
                break;

            default:

        }
    }



    //............................SendOtp......................//


    private void sendOtp() {
        Customprogress.showPopupProgressSpinner(context,true);

        SendOtpParameter sendOtpParameter= new SendOtpParameter(4,"3",etPhoneNum.getText().toString());
        Call<SendOtpResponse>call = jsonPlaceHolderApi.SendOtp(sendOtpParameter);
        call.enqueue(new Callback<SendOtpResponse>() {
            @Override
            public void onResponse(Call<SendOtpResponse> call, Response<SendOtpResponse> response) {
                Customprogress.showPopupProgressSpinner(context,false);
                if (response.isSuccessful()){

                    if (response.body().getStatus()){

                        PhoneNumber=etPhoneNum.getText().toString();
                        Intent intent =new Intent(MobileLoginActivity.this,VerifyOtpActivity.class);

                       //...........setMobileNumber............//
                        sessionManager.setSavedMobile(PhoneNumber);
                       startActivity(intent);
                       finish();

                    }
                    else {
                        Toast.makeText(MobileLoginActivity.this, "Otp...."+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<SendOtpResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }
}