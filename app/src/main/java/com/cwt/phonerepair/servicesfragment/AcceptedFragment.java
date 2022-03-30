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
import com.cwt.phonerepair.adapter.AcceptedAdapter;
import com.cwt.phonerepair.adapter.PendingAdapter;
import com.cwt.phonerepair.modelclass.PendingModel;

import java.util.ArrayList;

public class AcceptedFragment extends Fragment {

    RecyclerView rvAccept;
    Context context;
    ArrayList<PendingModel> modelArrayList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_accepted, container, false);
        initView(view);


        modelArrayList = new ArrayList<>();

        for (int i=0;i<=9;i++){

            modelArrayList.add(new PendingModel ("#CUEP8383832", "18-02-2021  12:24PM"));


        }
        AcceptedAdapter acceptedAdapter = new AcceptedAdapter(modelArrayList, context);
        rvAccept.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvAccept.setAdapter(acceptedAdapter);
        rvAccept.setHasFixedSize(true);
        return  view;

    }

    private void initView(View view) {

        context=getActivity();
        rvAccept = view.findViewById(R.id.rvAccept);

    }

}