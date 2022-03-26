package com.cwt.phonerepair.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.modelclass.OurExclusiveStoreModel;

import java.util.ArrayList;

public class AllStoresAdapter extends RecyclerView.Adapter<AllStoresAdapter.ViewHolder> {

    Context context;

    ArrayList<OurExclusiveStoreModel> modelList;
    private ViewHolder holder;

    public AllStoresAdapter(ArrayList<OurExclusiveStoreModel> modelList, Context context) {
        this.context = context;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public AllStoresAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_all_store, viewGroup, false);
        AllStoresAdapter.ViewHolder viewHolder = new AllStoresAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull AllStoresAdapter.ViewHolder holder, int position) {
        OurExclusiveStoreModel model = modelList.get(position);
        holder.tvStoreName.setText(model.getTvStoreName());
        holder.tvAddress.setText(model.getTvAddress());
        holder.ivStoreimg.setImageResource(model.getIvStoreimg());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStoreName,tvAddress;
        ImageView ivStoreimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            ivStoreimg = itemView.findViewById(R.id.ivStoreimg);




        }
    }

}
