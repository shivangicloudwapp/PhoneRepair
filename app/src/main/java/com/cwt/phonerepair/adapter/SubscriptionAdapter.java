package com.cwt.phonerepair.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.activity.SubscribeNewStoreActivity;
import com.cwt.phonerepair.activity.serviceActivity.ServiceDetailsPendingActivity;
import com.cwt.phonerepair.modelclass.PendingModel;
import com.cwt.phonerepair.modelclass.SubscriptionModel;

import java.util.ArrayList;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder> {

    Context context;

    ArrayList<SubscriptionModel> modelList;

    public SubscriptionAdapter(ArrayList<SubscriptionModel> modelList, Context context) {
        this.context = context;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public SubscriptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_subscription, viewGroup, false);
        SubscriptionAdapter.ViewHolder viewHolder = new SubscriptionAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull SubscriptionAdapter.ViewHolder holder, int position) {
        SubscriptionModel model = modelList.get(position);
       /* holder.tvPlanName.setText(model.getTvPlanName());
        holder.tvMonth.setText(model.getTvMonth());
        holder.tvPostItem.setText(model.getTvPostItem());
*/
        holder.llSubscribePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, SubscribeNewStoreActivity.class);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlanName,tvMonth,tvPostItem;

        LinearLayout llSubscribePlan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlanName = itemView.findViewById(R.id.tvPlanName);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            llSubscribePlan = itemView.findViewById(R.id.llSubscribePlan);

        }
    }

}
