package com.cwt.phonerepair.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.Allurls;
import com.cwt.phonerepair.activity.StoreDetailsActivity;
import com.cwt.phonerepair.modelclass.response.HomeStoreModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OurExclusiveStoreAdapter extends RecyclerView.Adapter<OurExclusiveStoreAdapter.ViewHolder> {

     Context context;

     ArrayList<HomeStoreModel> modelList;

    public OurExclusiveStoreAdapter(ArrayList<HomeStoreModel> modelList, Context context) {
        this.context = context;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_our_exclusive_store, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeStoreModel model = modelList.get(position);
        holder.tvStoreName.setText(model.getStoreName());
        holder.tvAddress.setText(model.getAddress());


        Picasso.with(context).load(Allurls.ImageUrl).fit().centerCrop()
                .placeholder(R.drawable.group1042)
                .into(holder.ivStoreimg);




        holder.cvSeeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StoreDetailsActivity.class);
                context.startActivity(intent);            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStoreName,tvAddress,tvSeedetail;
        ImageView ivStoreimg;
        CardView cvSeeDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            ivStoreimg = itemView.findViewById(R.id.ivStoreimg);
            tvSeedetail = itemView.findViewById(R.id.tvSeedetail);
            cvSeeDetails = itemView.findViewById(R.id.cvSeeDetails);




        }
    }

}
