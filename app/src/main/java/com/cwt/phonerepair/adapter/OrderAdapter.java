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
import com.cwt.phonerepair.modelclass.OrderModel;
import com.cwt.phonerepair.modelclass.OurExclusiveStoreModel;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Context context;

    ArrayList<OrderModel> modelList;

    public OrderAdapter(ArrayList<OrderModel> modelList, Context context) {
        this.context = context;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_order, viewGroup, false);
        OrderAdapter.ViewHolder viewHolder = new OrderAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        OrderModel model = modelList.get(position);
        holder.tvItemNum.setText(model.getTvItemNum());
        holder.tvCode.setText(model.getTvCode());
        holder.tvTimeDate.setText(model.getTvTimeDate());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemNum,tvCode,tvTimeDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemNum = itemView.findViewById(R.id.tvItemNum);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvTimeDate = itemView.findViewById(R.id.tvTimeDate);




        }
    }

}
