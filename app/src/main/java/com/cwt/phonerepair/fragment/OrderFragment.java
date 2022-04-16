package com.cwt.phonerepair.fragment;

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
import com.cwt.phonerepair.adapter.OrderAdapter;
import com.cwt.phonerepair.adapter.PendingAdapter;
import com.cwt.phonerepair.modelclass.OrderModel;
import com.cwt.phonerepair.modelclass.service.ServicePendingModel;
import com.cwt.phonerepair.modelclass.service.ServiceResponse;
import com.cwt.phonerepair.modelclass.storeorder.StoreOrderModel;
import com.cwt.phonerepair.modelclass.storeorder.StoreOrderResponse;
import com.cwt.phonerepair.storemodelclass.StoreServiceOrderModel;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderFragment extends Fragment {

    RecyclerView rvOrder;
    Context context;
    ArrayList<StoreOrderModel> modelArrayList;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        initView(view);
        return view;


    }

    private void initView(View view) {

        rvOrder = view.findViewById(R.id.rvOrder);
        context=getActivity();
        modelArrayList = new ArrayList<>();
        sessionManager=new SessionManager(context);
        jsonPlaceHolderApi= ApiUtils.getAPIService();


        if (Utils.checkConnection(context)) {

            Order();

        } else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }

    private void Order() {

        Customprogress.showPopupProgressSpinner(context,true);
        Call<StoreOrderResponse> call=jsonPlaceHolderApi.StoreOrder("Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<StoreOrderResponse>() {
            @Override
            public void onResponse(Call<StoreOrderResponse> call, Response<StoreOrderResponse> response) {
                if (response.isSuccessful()){
                    Customprogress.showPopupProgressSpinner(context, false);

                    if (response.body().getStatus()){

                        modelArrayList= (ArrayList<StoreOrderModel>) response.body().getData().getOrder();
                        OrderAdapter adapter=new OrderAdapter(modelArrayList,context);
                        rvOrder.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        rvOrder.setAdapter(adapter);
                        rvOrder.setHasFixedSize(true);



                    }
                    else {
                        Toast.makeText(getActivity(), "Please Check Internet Connection "+response.body().getMassage(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<StoreOrderResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }
}