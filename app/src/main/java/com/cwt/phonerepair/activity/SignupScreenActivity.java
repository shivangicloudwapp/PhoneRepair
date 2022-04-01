package com.cwt.phonerepair.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.Utility;
import com.cwt.phonerepair.modelclass.parameter.SignupParameter;
import com.cwt.phonerepair.modelclass.response.SignupResponse;
import com.cwt.phonerepair.utils.Customprogress;

import butterknife.internal.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupScreenActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName,etEmail,etPassword,etConfPass;
   Button btnSignUp;
   TextView tvLogin;
   ImageView ivBackSignUp,ivShowHidePass,ivShowConHidePass;
    JsonPlaceHolderApi  jsonPlaceHolderApi;
    Context context;

    boolean is_click=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);


        InitView();


    }

    private void InitView() {
        context = SignupScreenActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etConfPass=findViewById(R.id.etConPass);
        btnSignUp=findViewById(R.id.btnSignUp);
        tvLogin=findViewById(R.id.tvLogin);
        ivBackSignUp=findViewById(R.id.ivBackSignUp);
        ivShowHidePass=findViewById(R.id.ivShowHidePass);
        ivShowConHidePass=findViewById(R.id.ivShowConHidePass);

        tvLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        ivBackSignUp.setOnClickListener(this);
        ivShowHidePass.setOnClickListener(this);
        ivShowConHidePass.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tvLogin:
                Intent intent =new Intent(SignupScreenActivity.this,LoginScreenActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSignUp:
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmpass = etConfPass.getText().toString().trim();
                if (name.isEmpty()){
                    etName.setError("Enter an email address");
                    etName.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    etEmail.setError("Enter an email address");
                    etEmail.requestFocus();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError("Enter a valid email address");
                    etEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    etPassword.setError("Enter a password");
                    etPassword.requestFocus();
                    return;
                }

                if (password.length() < 8) {

                    etPassword.setError("Password Length Must be 8 Digits");
                    etPassword.requestFocus();
                    return;

                }
                if (confirmpass.isEmpty()) {
                    etConfPass.setError("Enter a  Confirm password");
                    etConfPass.requestFocus();
                    return; }

                else if(!password.equals(confirmpass)){
                    etConfPass.setError("Enter a Correct password");
                    etConfPass.requestFocus();
                    return;            }

                signup();

            /*Intent intent =new Intent(SignupScreenActivity.this,MobileLoginActivity.class);
            startActivity(intent);*/
            /* if (Constants.isInternetConnected(context)) {

            } else {
                Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }*/
            /*if (Utility.CheckNetwork(SignupScreenActivity.this)) {

              signup();
            } else {
                Utility.showErrorMessage(getString(R.string.checkinternetconnection), context);
            }
*/
                break;
            case R.id.ivBackSignUp:
                onBackPressed();
                break;


            case R.id.ivShowHidePass:
                if (is_click){
                    is_click = false;
                    ivShowHidePass.setImageDrawable(getResources().getDrawable(R.drawable.ic_eye_show_grey));
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    is_click = true;
                    ivShowHidePass.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_visibility_off_24));
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                break;

            case R.id.ivShowConHidePass:
                if (is_click){
                    is_click = false;
                    ivShowConHidePass.setImageDrawable(getResources().getDrawable(R.drawable.ic_eye_show_grey));
                    etConfPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    is_click = true;
                    ivShowConHidePass.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_visibility_off_24));
                    etConfPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;





            default:
                break;


        }



    }

    private void signup() {

        @SuppressLint("HardwareIds")
        String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Customprogress.showPopupProgressSpinner(context, true);
        SignupParameter signupParameter = new SignupParameter(etName.getText().toString().trim(),etEmail.getText().toString(),etPassword.getText().toString(),android_id,"fcmtoken");
        Call<SignupResponse> call = jsonPlaceHolderApi.Signup(signupParameter);
        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Intent intent =new Intent(SignupScreenActivity.this,MobileLoginActivity.class);
                        Log.e("response.....",response.body().getData().toString());
                        startActivity(intent);
                          finish();
                    } else {
                        Toast.makeText(SignupScreenActivity.this, "faild...."+response.body().getMassage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}