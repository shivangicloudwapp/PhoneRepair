package com.cwt.phonerepair.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.activity.ProfileActivity;
import com.cwt.phonerepair.activity.SubscriptionPlanActivity;
import com.cwt.phonerepair.storeactivity.StoreHomeActivity;
import com.cwt.phonerepair.utils.SessionManager;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    LinearLayout llProfile,llSubscribenewStore,llSTD;

    TextView tvUserName,tvEmail,tvPhone;
    SessionManager sessionManager;
    String userName,phone,email;

Context context;
JsonPlaceHolderApi jsonPlaceHolderApi;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        initView(view);




        return view;
    }

    private void initView(View view) {
        context=getActivity();
        sessionManager= new SessionManager(context);
        jsonPlaceHolderApi= ApiUtils.getAPIService();





        llProfile=view.findViewById(R.id.llProfile);
        llSubscribenewStore=view.findViewById(R.id.llSubscribenewStore);
        llSTD=view.findViewById(R.id.llSTD);
        tvUserName=view.findViewById(R.id.tvUserName);
        tvPhone=view.findViewById(R.id.tvPhone);
        tvEmail=view.findViewById(R.id.tvEmail);

        llProfile.setOnClickListener(this);
        llSTD.setOnClickListener(this);
        llSubscribenewStore.setOnClickListener(this) ;


        userName=sessionManager.getSavedUserName();
        phone=sessionManager.getSavedMobile();
        email=sessionManager.getSavedEmail();


        tvUserName.setText(userName);
        tvPhone.setText(phone);
        tvEmail.setText(email);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.llSTD:
                Intent intent =new Intent(getActivity(), StoreHomeActivity.class);
                startActivity(intent);

                break;
            case R.id.llSubscribenewStore:
                Intent intent1 =new Intent(getActivity(), SubscriptionPlanActivity.class);
                startActivity(intent1);

                break;

            case R.id.llProfile:
                Intent intent2 =new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent2);

                break;


        }

    }
}