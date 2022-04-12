package com.cwt.phonerepair.adapter;

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
import com.cwt.phonerepair.activity.serviceActivity.ServiceDetailsCompleteActivity;
import com.cwt.phonerepair.modelclass.response.cart.gettocart.GetToCartModel;
import com.cwt.phonerepair.modelclass.service.ServiceCompleteModel;

import java.util.ArrayList;

public class CartProductListAdapter extends RecyclerView.Adapter<CartProductListAdapter.ViewHolder> {

    Context context;

    ArrayList<GetToCartModel> modelList;

    public CartProductListAdapter(ArrayList<GetToCartModel> modelList, Context context) {
        this.context = context;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public CartProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_cart_product, viewGroup, false);
        CartProductListAdapter.ViewHolder viewHolder = new CartProductListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull CartProductListAdapter.ViewHolder holder, int position) {
        GetToCartModel model = modelList.get(position);
        holder.tvProduct.setText(model.getStatus());

       /* holder.cvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ServiceDetailsCompleteActivity.class);
                context.startActivity(intent);

            }
        });*/


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProduct,tvProductQty;
        CardView cvComplete;
        ImageView ivproductRemove,ivproductAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProduct = itemView.findViewById(R.id.tvProduct);
            tvProductQty = itemView.findViewById(R.id.tvProductQty);
            ivproductRemove = itemView.findViewById(R.id.ivproductRemove);
            ivproductAdd = itemView.findViewById(R.id.ivproductAdd);




        }
    }

}
