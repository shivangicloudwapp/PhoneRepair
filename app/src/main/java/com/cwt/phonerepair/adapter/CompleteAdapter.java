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
import com.cwt.phonerepair.activity.serviceActivity.ServiceDetailsCompleteActivity;
import com.cwt.phonerepair.activity.serviceActivity.ServiceDetailsRejectActivity;
import com.cwt.phonerepair.modelclass.PendingModel;

import java.util.ArrayList;

public class CompleteAdapter extends RecyclerView.Adapter<CompleteAdapter.ViewHolder> {

    Context context;

    ArrayList<PendingModel> modelList;

    public CompleteAdapter(ArrayList<PendingModel> modelList, Context context) {
        this.context = context;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public CompleteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_completed, viewGroup, false);
        CompleteAdapter.ViewHolder viewHolder = new CompleteAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull CompleteAdapter.ViewHolder holder, int position) {
        PendingModel model = modelList.get(position);
        holder.tvCode.setText(model.getTvCode());
        holder.tvTimeDate.setText(model.getTvTimeDate());

        holder.cvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ServiceDetailsCompleteActivity.class);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCode,tvTimeDate;
        CardView cvComplete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvTimeDate = itemView.findViewById(R.id.tvTimeDate);
            cvComplete = itemView.findViewById(R.id.cvComplete);




        }
    }

}
