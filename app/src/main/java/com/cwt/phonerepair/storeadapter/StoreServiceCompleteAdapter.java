package com.cwt.phonerepair.storeadapter;

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

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.activity.serviceActivity.ServiceDetailsAcceptActivity;
import com.cwt.phonerepair.storemodelclass.StoreServiceOrderModel;
import com.cwt.phonerepair.storesservicedetails.StoreServiceDetailsAcceptActivity;
import com.cwt.phonerepair.storesservicedetails.StoreServiceDetailsCompleteActivity;

import java.util.ArrayList;

public class StoreServiceCompleteAdapter extends RecyclerView.Adapter<StoreServiceCompleteAdapter.ViewHolder> {

    Context context;

    ArrayList<StoreServiceOrderModel> modelList;

    public StoreServiceCompleteAdapter(ArrayList<StoreServiceOrderModel> modelList, Context context) {
        this.context = context;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public StoreServiceCompleteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.store_service_list_complete, viewGroup, false);
        StoreServiceCompleteAdapter.ViewHolder viewHolder = new StoreServiceCompleteAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull StoreServiceCompleteAdapter.ViewHolder holder, int position) {
        StoreServiceOrderModel model = modelList.get(position);
        holder.tvCode.setText(model.getTvCode());
        holder.tvDate.setText(model.getTvCode());
        holder.tvName.setText(model.getTvName());

        holder.userImg.setImageResource(model.getUserImg());


        holder.cvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), StoreServiceDetailsCompleteActivity.class);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCode,tvDate,tvName;
        ImageView userImg;

        CardView cvComplete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvName = itemView.findViewById(R.id.tvName);
            userImg = itemView.findViewById(R.id.userImg);
            cvComplete = itemView.findViewById(R.id.cvComplete);




        }
    }

}