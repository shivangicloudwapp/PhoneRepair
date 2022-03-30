package com.cwt.phonerepair.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.activity.AllProductActivity;
import com.cwt.phonerepair.activity.ProductDetailsActivity;
import com.cwt.phonerepair.activity.StoreDetailsActivity;
import com.cwt.phonerepair.modelclass.PendingModel;
import com.cwt.phonerepair.modelclass.ProductModel;

import java.util.ArrayList;

public class ProdcutAdapter extends RecyclerView.Adapter<ProdcutAdapter.ViewHolder> {

    Context context;

    ArrayList<ProductModel> modelList;

    public ProdcutAdapter(ArrayList<ProductModel> modelList, Context context) {
        this.context = context;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public ProdcutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_product, viewGroup, false);
        ProdcutAdapter.ViewHolder viewHolder = new ProdcutAdapter.ViewHolder(listItem);

       viewHolder.rlProduct.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(view.getContext(), ProductDetailsActivity.class);
               context.startActivity(intent);
           }
       });

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ProdcutAdapter.ViewHolder holder, int position) {
        ProductModel model = modelList.get(position);
        holder.tvProductName.setText(model.getTvProductName());
        holder.tvProductId.setText(model.getTvProductId());
        holder.ivProdcut.setImageResource(model.getIvProdcut());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName,tvProductId;

        ImageView ivProdcut;

        RelativeLayout rlProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductId = itemView.findViewById(R.id.tvProductId);
            ivProdcut = itemView.findViewById(R.id.ivProdcut);
            rlProduct = itemView.findViewById(R.id.rlProduct);




        }
    }

}
