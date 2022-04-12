package com.cwt.phonerepair.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.activity.serviceActivity.ServiceDetailsCompleteActivity;
import com.cwt.phonerepair.modelclass.service.ServiceCompleteModel;

import java.util.ArrayList;

public class CustomerFeedbackAdapter extends RecyclerView.Adapter<CustomerFeedbackAdapter.ViewHolder> {

    Context context;


    public CustomerFeedbackAdapter( Context context) {
        this.context = context;

    }


    @NonNull
    @Override
    public CustomerFeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_customer_feedback, viewGroup, false);
        CustomerFeedbackAdapter.ViewHolder viewHolder = new CustomerFeedbackAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull CustomerFeedbackAdapter.ViewHolder holder, int position) {
   //     ServiceCompleteModel model = modelList.get(position);
      /*  holder.tvCode.setText(model.getOrderId());
        holder.tvTimeDate.setText(model.getCreatedAt());
*/
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
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCustomerName,tvReviews,tvPrice;
        CardView cvComplete;
        RatingBar rbCustomerfeedback;
        ImageView ivCustomer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvReviews = itemView.findViewById(R.id.tvReviews);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            rbCustomerfeedback = itemView.findViewById(R.id.rbCustomerfeedback);
            ivCustomer = itemView.findViewById(R.id.ivCustomer);




        }
    }

}
