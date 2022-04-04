package com.cwt.phonerepair.adapter;

import android.annotation.SuppressLint;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.activity.SubscribeNewStoreActivity;
import com.cwt.phonerepair.modelclass.response.SubscriptionPlanModel;

import java.util.ArrayList;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder> {

    Context context;

    ArrayList<SubscriptionPlanModel> modelList;
    int previousPosition = 0;

    public SubscriptionAdapter(ArrayList<SubscriptionPlanModel> modelList, Context context) {
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
    public void onBindViewHolder(@NonNull SubscriptionAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SubscriptionPlanModel model = modelList.get(position);
        holder.tvPlanName.setText(model.getTitle());
        holder.tvMonth.setText(model.getDuration());
        holder.tvPostItem.setText(model.getItems());
        holder.tvPrice.setText(model.getPrice());

        holder.llSubscriptionPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 previousPosition = position;
                   notifyDataSetChanged();

                //holder.llSubscriptionPlan.setBackgroundResource(R.drawable.linear_layout_black_border);


            }
        });
                if(position==previousPosition){
                    holder.llSubscriptionPlan.setBackgroundDrawable(context.getDrawable(R.drawable.linear_layout_black_border));

                       Intent intent = new Intent(context, SubscribeNewStoreActivity.class);
                context.startActivity(intent);
                }
                else
                {
                    holder.llSubscriptionPlan.setBackgroundDrawable(context.getDrawable(R.drawable.line_bac));

                }

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlanName,tvMonth,tvPostItem,tvPrice;

        LinearLayout llSubscriptionPlan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlanName = itemView.findViewById(R.id.tvPlanName);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvPostItem = itemView.findViewById(R.id.tvMonth);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            llSubscriptionPlan = itemView.findViewById(R.id.llSubscriptionPlan);

        }
    }

}
