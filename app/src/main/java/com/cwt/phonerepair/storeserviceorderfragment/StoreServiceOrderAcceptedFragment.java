package com.cwt.phonerepair.storeserviceorderfragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.storeadapter.StoreServiceAcceptedAdapter;
import com.cwt.phonerepair.storemodelclass.StoreServiceOrderModel;

import java.util.ArrayList;

public class StoreServiceOrderAcceptedFragment extends Fragment {

    RecyclerView rvAccept;
    Context context;
    ArrayList<StoreServiceOrderModel> modelArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_store_service_order_accepted, container, false);


        initView(view);


        modelArrayList = new ArrayList<>();

        for (int i=0;i<=9;i++){

            modelArrayList.add(new StoreServiceOrderModel ("#CUEP8383832", "18-02-2021  12:24PM","Jhon Deo",R.drawable.profile));


        }
        StoreServiceAcceptedAdapter storeServiceAcceptedAdapter = new StoreServiceAcceptedAdapter(modelArrayList, context);
        rvAccept.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvAccept.setAdapter(storeServiceAcceptedAdapter);
        rvAccept.setHasFixedSize(true);
        return  view;

    }

    private void initView(View view) {
        context=getActivity();
        rvAccept = view.findViewById(R.id.rvAccept);

    }

}