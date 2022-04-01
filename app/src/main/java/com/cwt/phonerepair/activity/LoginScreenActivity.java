package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cwt.phonerepair.R;

public class LoginScreenActivity extends AppCompatActivity implements View.OnClickListener {
Button btnLogin;
TextView tvSignup;

EditText etEmail,etPassword;
ImageView ivShowHidePass;


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
                Intent intent =new Intent(LoginScreenActivity.this,MobileLoginActivity.class);
                startActivity(intent);
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

        }




    }





}