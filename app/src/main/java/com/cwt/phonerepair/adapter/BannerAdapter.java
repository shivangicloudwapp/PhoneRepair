package com.cwt.phonerepair.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.Allurls;
import com.cwt.phonerepair.activity.BookServiceActivity;
import com.cwt.phonerepair.modelclass.response.home.HomeBannerModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {

    Context context;

    ArrayList<HomeBannerModel> modelList;

    public BannerAdapter(ArrayList<HomeBannerModel> modelList, Context context) {
        this.context = context;
        this.modelList = modelList;
    }



    @NonNull
    @Override
    public BannerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_banner, viewGroup, false);
        BannerAdapter.ViewHolder viewHolder = new BannerAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull BannerAdapter.ViewHolder holder, int position) {
        HomeBannerModel model = modelList.get(position);

        System.out.println("image url >>>>>>"+Allurls.ImageUrl+model.getImage());

        Picasso.with(context).load(Allurls.ImageUrl).fit().centerCrop()
                .placeholder(R.drawable.group1042)
                .into(holder.ivBanner);


        holder.ivBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), BookServiceActivity.class);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBanner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.ivBanner);




        }
    }

}
