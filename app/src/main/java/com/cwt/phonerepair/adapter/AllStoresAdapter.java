package com.cwt.phonerepair.adapter;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.Allurls;
import com.cwt.phonerepair.activity.StoreDetailsActivity;
import com.cwt.phonerepair.modelclass.response.AllStoreModel;
import com.cwt.phonerepair.modelclass.response.HomeStoreModel;
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
    public void onBindViewHolder(@NonNull AllStoresAdapter.ViewHolder holder, int position) {
        AllStoreModel model = modelList.get(position);
        holder.tvStoreName.setText(model.getStoreName());
        holder.tvAddress.setText(model.getAddress());




        Picasso.with(context).load(Allurls.ImageUrl).fit().centerCrop()
                .placeholder(R.drawable.group1042)
                .into(holder.ivStoreimg);





        holder.btnSeedetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StoreDetailsActivity.class);
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
        ImageView ivStoreimg;
        Button btnSeedetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            ivStoreimg = itemView.findViewById(R.id.ivStoreimg);
            btnSeedetail = itemView.findViewById(R.id.btnSeedetail);



        }
    }

}
