package com.cwt.phonerepair.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.Allurls;
import com.cwt.phonerepair.activity.StoreDetailsActivity;
import com.cwt.phonerepair.modelclass.response.allStores.AllStoreModel;
import com.cwt.phonerepair.modelclass.response.home.HomeStoreModel;
import com.cwt.phonerepair.utils.RoundRectCornerImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllStoresAdapter extends RecyclerView.Adapter<AllStoresAdapter.ViewHolder> {

    Context context;

    ArrayList<AllStoreModel> modelList;
    private ViewHolder holder;

    public AllStoresAdapter(ArrayList<AllStoreModel> modelList, Context context) {
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
    public void onBindViewHolder(@NonNull AllStoresAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AllStoreModel model = modelList.get(position);
        holder.tvStoreName.setText(model.getStoreName());
        holder.tvAddress.setText(model.getAddress());



        Picasso.with(context).load(Allurls.ImageUrl+model.getStoreImage())
                .placeholder(R.drawable.group1042)
                .into(holder.ivStoreimg);

        holder.btnSeedetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StoreDetailsActivity.class);
                intent.putExtra("store_Id",modelList.get(position).getId());
                context.startActivity(intent);
            }
        });

        holder.cvAllStores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StoreDetailsActivity.class);
                intent.putExtra("store_Id",modelList.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStoreName,tvAddress;
        RoundRectCornerImageView ivStoreimg;
        Button btnSeedetail;

        CardView cvAllStores;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            ivStoreimg = itemView.findViewById(R.id.ivStoreimg);
            btnSeedetail = itemView.findViewById(R.id.btnSeedetail);
            cvAllStores = itemView.findViewById(R.id.cvAllStores);



        }
    }

}
