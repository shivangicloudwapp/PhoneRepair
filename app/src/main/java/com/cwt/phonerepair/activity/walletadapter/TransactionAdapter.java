package com.cwt.phonerepair.activity.walletadapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.activity.serviceActivity.ServiceDetailsAcceptActivity;
import com.cwt.phonerepair.adapter.AcceptedAdapter;
import com.cwt.phonerepair.modelclass.PendingModel;
import com.cwt.phonerepair.modelclass.TransactionModel;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    Context context;

    ArrayList<TransactionModel> modelList;

    public TransactionAdapter(ArrayList<TransactionModel> modelList, Context context) {
        this.context = context;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_transaction, viewGroup, false);
        TransactionAdapter.ViewHolder viewHolder = new TransactionAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
        TransactionModel model = modelList.get(position);
        holder.tvWith.setText(model.getTvWith());
        holder.tvMonth.setText(model.getTvMonth());
        holder.tvTimeDate.setText(model.getTvTimeDate());
        holder.tvRm.setText(model.getTvRm());



    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvWith,tvMonth,tvTimeDate,tvRm;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWith = itemView.findViewById(R.id.tvWith);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvTimeDate = itemView.findViewById(R.id.tvTimeDate);
            tvRm = itemView.findViewById(R.id.tvRm);




        }
    }

}
