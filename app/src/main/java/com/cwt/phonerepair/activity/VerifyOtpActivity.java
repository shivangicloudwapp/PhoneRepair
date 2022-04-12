package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.modelclass.parameter.VerifyOtpParameter;
import com.cwt.phonerepair.modelclass.verifyOtp.VerifyOtpResponse;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;

import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBackVerifyotp;
    Button btnVerifyOtp;
    String phone_num;
    TextView  tvPhoneNumber;
    OtpTextView otp_view;
    String otp;
Context context;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        initView();

       /* Intent intent=getIntent();
        phone_num=intent.getStringExtra("phone_num");

        spannBuilder();*/

/*
         sessionManager.getSavedMobile();*/
        spannBuilder();

    }

    private void spannBuilder() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        String strclr = "Enter the four digit code we sent to you ";
        SpannableString strSpan= new SpannableString(strclr);
        strSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#868889")), 0, strclr.length(), 0);
        builder.append(strSpan);

        String phonnum = sessionManager.getSavedMobile();
        SpannableString phoneSpan= new SpannableString(phonnum);
        phoneSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), 0, phonnum.length(), 0);
        builder.append(phoneSpan);


        tvPhoneNumber.setText(builder, TextView.BufferType.SPANNABLE);

    }

    private void initView() {
        jsonPlaceHolderApi= ApiUtils.getAPIService();
        context=VerifyOtpActivity.this;
        sessionManager= new SessionManager(context);

        ivBackVerifyotp= findViewById(R.id.ivBackVerifyotp);
        btnVerifyOtp= findViewById(R.id.btnVerifyOtp);
        tvPhoneNumber= findViewById(R.id.tvPhoneNumber);
        otp_view= findViewById(R.id.otp_view);

        ivBackVerifyotp.setOnClickListener(this);
        btnVerifyOtp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.ivBackVerifyotp:
                onBackPressed();
                break;
            case R.id.btnVerifyOtp:
                otp=otp_view.getOTP().toString().trim();

                if (TextUtils.isEmpty(otp)){

                    Toast.makeText(context, "Please Enter Otp", Toast.LENGTH_SHORT).show();

                }
                else if (otp.length()<4){
                    Toast.makeText(context, "Please Enter 4 digits of Otp", Toast.LENGTH_SHORT).show();

                }

                else{
                    if (Utils.checkConnection(context)) {

                        verifyOtp();
                    } else {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }


                break;
            default:


        }

    }

    private void verifyOtp() {

        Customprogress.showPopupProgressSpinner(context,true);
        VerifyOtpParameter verifyOtpParameter= new  VerifyOtpParameter(4,otp);

        Call<VerifyOtpResponse> call=jsonPlaceHolderApi.VerifyOtp(verifyOtpParameter);
        call.enqueue(new Callback<VerifyOtpResponse>() {

            @Override
            public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {

                if (response.isSuccessful()){
                    if (response.body().getStatus()){

                        Intent intent =new Intent(VerifyOtpActivity.this,DashboardActivity.class);
                        startActivity(intent);

                    }
                }

            }

            @Override
            public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}