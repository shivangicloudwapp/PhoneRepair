package com.cwt.phonerepair.servicesfragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.adapter.CompleteAdapter;
import com.cwt.phonerepair.adapter.PendingAdapter;
import com.cwt.phonerepair.modelclass.PendingModel;
import com.cwt.phonerepair.modelclass.service.ServiceCompleteModel;
import com.cwt.phonerepair.modelclass.service.ServicePendingModel;
import com.cwt.phonerepair.modelclass.service.ServiceResponse;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompletedFragment extends Fragment {
    RecyclerView rvComplete;
    Context context;
    ArrayList<ServiceCompleteModel> modelArrayList;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_completed, container, false);

        initView(view);

/*
        modelArrayList = new ArrayList<>();

        for (int i=0;i<=9;i++){

            modelArrayList.add(new PendingModel ("#CUEP8383832", "18-02-2021  12:24PM"));


        }
        CompleteAdapter completeAdapter = new CompleteAdapter(modelArrayList, context);
        rvComplete.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvComplete.setAdapter(completeAdapter);
        rvComplete.setHasFixedSize(true);
       ;*/
        return  view;
    }

    private void initView(View view) {
        context=getActivity();
        sessionManager=new SessionManager(context);
        jsonPlaceHolderApi= ApiUtils.getAPIService();
        modelArrayList = new ArrayList<>();
        rvComplete = view.findViewById(R.id.rvComplete);

        if (Utils.checkConnection(context)) {
            serviceComplete();

        } else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void serviceComplete() {

        Customprogress.showPopupProgressSpinner(context,true);
        Call<ServiceResponse> call=jsonPlaceHolderApi.Services("Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                if (response.isSuccessful()){
                    Customprogress.showPopupProgressSpinner(context, false);

                    if (response.body().getStatus()){

                        modelArrayList= (ArrayList<ServiceCompleteModel>) response.body().getData().getCompleted();
                        CompleteAdapter adapter=new CompleteAdapter(modelArrayList,context);
                        rvComplete.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        rvComplete.setAdapter(adapter);
                        rvComplete.setHasFixedSize(true);



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