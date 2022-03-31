package com.cwt.phonerepair.storefragment;

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
import com.cwt.phonerepair.storeadapter.StoreServicePendingAdapter;
import com.cwt.phonerepair.storemodelclass.StoreServiceOrderModel;

import java.util.ArrayList;

public class StoreServicePendingFragment extends Fragment {


    RecyclerView rvPending;
    Context context;
    ArrayList<StoreServiceOrderModel> modelArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_store_service_pending, container, false);


        initView(view);


        modelArrayList = new ArrayList<>();

        for (int i=0;i<=9;i++){

            modelArrayList.add(new StoreServiceOrderModel("#CUEP8383832", "18-02-2021  12:24PM","Jhon Deo",R.drawable.profile));


        }
        StoreServicePendingAdapter adapter = new StoreServicePendingAdapter(modelArrayList, context);
        rvPending.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvPending.setAdapter(adapter);
        rvPending.setHasFixedSize(true);
        return  view;

    }

    private void initView(View view) {
        context=getActivity();
        rvPending = view.findViewById(R.id.rvPending);

    }

}