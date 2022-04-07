package com.cwt.phonerepair.servicesfragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.activity.AllProductActivity;
import com.cwt.phonerepair.activity.LoginScreenActivity;
import com.cwt.phonerepair.adapter.AllProductAdapter;
import com.cwt.phonerepair.adapter.PendingAdapter;
import com.cwt.phonerepair.modelclass.PendingModel;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementModel;
import com.cwt.phonerepair.modelclass.service.ServicePendingModel;
import com.cwt.phonerepair.modelclass.service.ServiceResponse;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingFragment extends Fragment {


    RecyclerView rvPending;
    Context context;
    ArrayList<ServicePendingModel> modelArrayList;
JsonPlaceHolderApi jsonPlaceHolderApi;
SessionManager sessionManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pending, container, false);

        initView(view);
        return  view;

    }

    private void initView(View view) {

        context=getActivity();
        sessionManager=new SessionManager(context);
        jsonPlaceHolderApi= ApiUtils.getAPIService();
        rvPending = view.findViewById(R.id.rvPending);
        modelArrayList = new ArrayList<>();


        if (Utils.checkConnection(context)) {

            servicePending();

        } else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void servicePending() {

        Customprogress.showPopupProgressSpinner(context,true);
        Call<ServiceResponse>call=jsonPlaceHolderApi.Services("Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                if (response.isSuccessful()){
                    Customprogress.showPopupProgressSpinner(context, false);

                    if (response.body().getStatus()){

                      modelArrayList= (ArrayList<ServicePendingModel>) response.body().getData().getPending();
                        PendingAdapter adapter=new PendingAdapter(modelArrayList,context);
                        rvPending.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        rvPending.setAdapter(adapter);
                        rvPending.setHasFixedSize(true);



                    }
                    else {
                        Toast.makeText(getActivity(), "faild...."+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }

}