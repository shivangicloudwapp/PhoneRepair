package com.cwt.phonerepair.adapter;

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
import com.cwt.phonerepair.activity.OrderDetailsActivity;
import com.cwt.phonerepair.modelclass.OrderModel;
import com.cwt.phonerepair.modelclass.storeorder.StoreOrderModel;
import com.cwt.phonerepair.modelclass.storeorder.StoreOrderProduct;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Context context;

    ArrayList<StoreOrderModel> modelList;

    public OrderAdapter(ArrayList<StoreOrderModel> modelList, Context context) {
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
        StoreOrderModel model = modelList.get(position);
        holder.tvItemNum.setText(model.getTotal());
        holder.tvCode.setText(model.getOrderId());
        holder.tvTimeDate.setText(model.getCreatedAt());
        holder.cvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OrderDetailsActivity.class);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemNum,tvCode,tvTimeDate;
        CardView cvOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemNum = itemView.findViewById(R.id.tvItemNum);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvTimeDate = itemView.findViewById(R.id.tvTimeDate);
            cvOrder = itemView.findViewById(R.id.cvOrder);




        }
    }

}
