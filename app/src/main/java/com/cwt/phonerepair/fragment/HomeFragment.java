package com.cwt.phonerepair.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.activity.AllStoresActivity;
import com.cwt.phonerepair.activity.BookServiceActivity;
import com.cwt.phonerepair.adapter.OurExclusiveStoreAdapter;
import com.cwt.phonerepair.modelclass.OurExclusiveStoreModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener {



    RecyclerView rvourExcluStore;
    Context context;
    ArrayList<OurExclusiveStoreModel> modelArrayList;
  Button btnBookNow;
  TextView tvSeeAll;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);

        return view;

    }

    private void initView(View view) {

        context=getActivity();

        btnBookNow=view.findViewById(R.id.btnBookNow);
        tvSeeAll=view.findViewById(R.id.tvSeeAll);
        rvourExcluStore=view.findViewById(R.id.rvourExcluStore);

        tvSeeAll.setOnClickListener(this);
        btnBookNow.setOnClickListener(this);


        modelArrayList=new ArrayList<>() ;
        modelArrayList.add(new OurExclusiveStoreModel("The Apple Store","New Door No. A4-72 Ground Floor c21 Mall",R.drawable.img_2));
        modelArrayList.add(new OurExclusiveStoreModel("Dong Mobile Store","New Door No. A4-72 First Floor c21 Mall",R.drawable.img_2));
        modelArrayList.add(new OurExclusiveStoreModel("The Apple Store","New Door No. A4-72 Ground Floor c21 Mall",R.drawable.img_2));
        modelArrayList.add(new OurExclusiveStoreModel("Dong Mobile Store","New Door No. A4-72 First Floor c21 Mall",R.drawable.img_2));

        OurExclusiveStoreAdapter exclusiveStoreAdapter=new OurExclusiveStoreAdapter(modelArrayList,getContext());
        rvourExcluStore.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvourExcluStore.setAdapter(exclusiveStoreAdapter);
        rvourExcluStore.setHasFixedSize(true);
    }

    @Override
    public void onClick(View v) {
        if (v==tvSeeAll){
            Intent intent =new Intent(getActivity(), AllStoresActivity.class);
            startActivity(intent);
        }

        else if (v==btnBookNow){
            Intent intent =new Intent(getActivity(), BookServiceActivity.class);
            startActivity(intent);

        }
    }
}