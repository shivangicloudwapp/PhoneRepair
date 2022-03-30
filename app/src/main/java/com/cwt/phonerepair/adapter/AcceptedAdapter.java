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
import com.cwt.phonerepair.activity.serviceActivity.ServiceDetailsAcceptActivity;
import com.cwt.phonerepair.activity.serviceActivity.ServiceDetailsCompleteActivity;
import com.cwt.phonerepair.modelclass.PendingModel;

import java.util.ArrayList;

public class AcceptedAdapter extends RecyclerView.Adapter<AcceptedAdapter.ViewHolder> {

    Context context;

    ArrayList<PendingModel> modelList;

    public AcceptedAdapter(ArrayList<PendingModel> modelList, Context context) {
        this.context = context;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public AcceptedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_accepted, viewGroup, false);
        AcceptedAdapter.ViewHolder viewHolder = new AcceptedAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull AcceptedAdapter.ViewHolder holder, int position) {
        PendingModel model = modelList.get(position);
        holder.tvCode.setText(model.getTvCode());
        holder.tvTimeDate.setText(model.getTvTimeDate());


        holder.cvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ServiceDetailsAcceptActivity.class);
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

        CardView cvAccept;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvTimeDate = itemView.findViewById(R.id.tvTimeDate);
            cvAccept = itemView.findViewById(R.id.cvAccept);




        }
    }

}
