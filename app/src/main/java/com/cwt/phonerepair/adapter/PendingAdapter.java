package com.cwt.phonerepair.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.modelclass.OrderModel;
import com.cwt.phonerepair.modelclass.PendingModel;

import java.util.ArrayList;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.ViewHolder> {

    Context context;

    ArrayList<PendingModel> modelList;

    public PendingAdapter(ArrayList<PendingModel> modelList, Context context) {
        this.context = context;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public PendingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_pending, viewGroup, false);
        PendingAdapter.ViewHolder viewHolder = new PendingAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull PendingAdapter.ViewHolder holder, int position) {
        PendingModel model = modelList.get(position);
        holder.tvCode.setText(model.getTvCode());
        holder.tvTimeDate.setText(model.getTvTimeDate());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCode,tvTimeDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvTimeDate = itemView.findViewById(R.id.tvTimeDate);




        }
    }

}
