package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.modelclass.parameter.LoginParameter;
import com.cwt.phonerepair.modelclass.response.login.LoginResponse;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreenActivity extends AppCompatActivity implements View.OnClickListener {
Button btnLogin;
TextView tvSignup;

EditText etEmail,etPassword;
ImageView ivShowHidePass;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;
Context context;

    boolean is_click=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        intView();

       /* ivShowHidePass.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24);
        etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        etPassword.setSelection(etPassword.getText().length());
        show=false;*/
    }

    private void intView() {
        context= LoginScreenActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        sessionManager = new SessionManager(context);

        btnLogin=findViewById(R.id.btnLogin);
        tvSignup=findViewById(R.id.tvSignup);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        ivShowHidePass=findViewById(R.id.ivShowHidePass);


        btnLogin.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
        ivShowHidePass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){


            case R.id.btnLogin:
                String email=etEmail.getText().toString().trim();
                String pass=etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    etEmail.setError("Please Enter a Email Address");
                    etEmail.requestFocus();
                    return;
                }

                else if (TextUtils.isEmpty(pass)) {
                    etPassword.setError("Enter a password");
                    etPassword.requestFocus();
                    return;
                }

                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError("Enter a valid Email address");
                    etEmail.requestFocus();
                    return;
                }

                if (Utils.checkConnection(context)) {

                    Login();

                } else {
                    Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }




                break;

            case R.id.tvSignup:
                Intent intent1 =new Intent(LoginScreenActivity.this,SignupScreenActivity.class);
                startActivity(intent1);
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
            default:
                break;

        }




    }

    private void Login() {

        Customprogress.showPopupProgressSpinner(context,true);
        LoginParameter loginParameter= new LoginParameter(etEmail.getText().toString(),etPassword.getText().toString());
        Call<LoginResponse>call=jsonPlaceHolderApi.Login(loginParameter);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    Customprogress.showPopupProgressSpinner(context, false);

                    if (response.body().getStatus()){

                        sessionManager.setSavedToken(response.body().getData().getToken());
                        sessionManager.setLogin(true);
                  Intent intent =new Intent(LoginScreenActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
                    }

                    else {
                        Toast.makeText(LoginScreenActivity.this, "faild...."+response.body().getMassage(), Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }


}