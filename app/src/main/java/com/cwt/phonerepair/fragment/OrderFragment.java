package com.cwt.phonerepair.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.adapter.OrderAdapter;
import com.cwt.phonerepair.modelclass.OrderModel;

import java.util.ArrayList;


public class OrderFragment extends Fragment {

    RecyclerView rvOrder;
    Context context;
    ArrayList<OrderModel> modelArrayList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        rvOrder = view.findViewById(R.id.rvOrder);

             context=getActivity();
        modelArrayList = new ArrayList<>();

        for (int i=0;i<=9;i++){

            modelArrayList.add(new OrderModel("4 Items", "#CUEP8383832", "18-02-2021  12:24PM"));


        }
        OrderAdapter orderAdapter = new OrderAdapter(modelArrayList, context);
        rvOrder.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvOrder.setAdapter(orderAdapter);
        rvOrder.setHasFixedSize(true);
        return  view;

    }
}