package com.cwt.phonerepair.activity.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.activity.walletadapter.TransactionAdapter;
import com.cwt.phonerepair.adapter.OrderAdapter;
import com.cwt.phonerepair.modelclass.OrderModel;
import com.cwt.phonerepair.modelclass.TransactionModel;

import java.util.ArrayList;

public class WalletTransactionFragment extends Fragment {



    RecyclerView rvWith;
    Context context;
    ArrayList<TransactionModel> modelArrayList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_wallet_transaction, container, false);

        rvWith = view.findViewById(R.id.rvWith);


        modelArrayList = new ArrayList<>();

            modelArrayList.add(new TransactionModel("Withdrawal", "Debit", "18-02-2021  12:24PM","RM150"));
            modelArrayList.add(new TransactionModel("Withdrawal", "Debit", "18-02-2021  12:24PM","RM150"));
            modelArrayList.add(new TransactionModel("Withdrawal", "Debit", "18-02-2021  12:24PM","RM150"));
            modelArrayList.add(new TransactionModel("Withdrawal", "Debit", "18-02-2021  12:24PM","RM150"));
            modelArrayList.add(new TransactionModel("Withdrawal", "Debit", "18-02-2021  12:24PM","RM150"));
            modelArrayList.add(new TransactionModel("Withdrawal", "Debit", "18-02-2021  12:24PM","RM150"));


        TransactionAdapter orderAdapter = new TransactionAdapter(modelArrayList, context);
        rvWith.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvWith.setAdapter(orderAdapter);
        rvWith.setHasFixedSize(true);
        return  view;

    }
    }
