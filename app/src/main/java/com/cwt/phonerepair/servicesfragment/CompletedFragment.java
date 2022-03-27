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
import com.cwt.phonerepair.adapter.CompleteAdapter;
import com.cwt.phonerepair.adapter.PendingAdapter;
import com.cwt.phonerepair.modelclass.PendingModel;

import java.util.ArrayList;

public class CompletedFragment extends Fragment {
    RecyclerView rvComplete;
    Context context;
    ArrayList<PendingModel> modelArrayList;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_completed, container, false);
        rvComplete = view.findViewById(R.id.rvComplete);


        modelArrayList = new ArrayList<>();

        for (int i=0;i<=9;i++){

            modelArrayList.add(new PendingModel ("#CUEP8383832", "18-02-2021  12:24PM"));


        }
        CompleteAdapter completeAdapter = new CompleteAdapter(modelArrayList, context);
        rvComplete.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvComplete.setAdapter(completeAdapter);
        rvComplete.setHasFixedSize(true);
        return  view;

    }

}