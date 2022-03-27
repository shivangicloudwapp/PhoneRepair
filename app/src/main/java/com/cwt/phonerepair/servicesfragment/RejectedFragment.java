package com.cwt.phonerepair.servicesfragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.adapter.PendingAdapter;
import com.cwt.phonerepair.adapter.RejectedAdapter;
import com.cwt.phonerepair.modelclass.PendingModel;

import java.util.ArrayList;

public class RejectedFragment extends Fragment {
    RecyclerView rvRejected;
    Context context;
    ArrayList<PendingModel> modelArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_rejected, container, false);
        rvRejected = view.findViewById(R.id.rvRejected);


        modelArrayList = new ArrayList<>();

        for (int i=0;i<=9;i++){

            modelArrayList.add(new PendingModel ("#CUEP8383832", "18-02-2021  12:24PM"));


        }
        RejectedAdapter rejectedAdapter = new RejectedAdapter(modelArrayList, context);
        rvRejected.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvRejected.setAdapter(rejectedAdapter);
        rvRejected.setHasFixedSize(true);
        return  view;

    }

}