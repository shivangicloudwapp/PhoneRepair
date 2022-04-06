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
import com.cwt.phonerepair.Server.Allurls;
import com.cwt.phonerepair.activity.ProductDetailsActivity;
import com.cwt.phonerepair.modelclass.response.storedetails.StoreDetailsProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProdcutAdapter extends RecyclerView.Adapter<ProdcutAdapter.ViewHolder> {

    Context context;

    ArrayList<StoreDetailsProductModel> modelList;

    public ProdcutAdapter(ArrayList<StoreDetailsProductModel> modelList, Context context) {
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
        StoreDetailsProductModel model = modelList.get(position);

        System.out.println("title....."+modelList.get(position).getTitle());
       holder.tvProductName.setText(model.getTitle());
        System.out.println("price....."+modelList.get(position).getPrice());

        holder.tvProductPrice.setText(Integer.toString(model.getPrice()));

        Picasso.with(context).load(Allurls.ImageUrl+model.getProductImage())
                .placeholder(R.drawable.group1042)
                .into(holder.ivProdcut);



    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName,tvProductPrice;

        ImageView ivProdcut;

        RelativeLayout rlProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            ivProdcut = itemView.findViewById(R.id.ivProdcut);
            rlProduct = itemView.findViewById(R.id.rlProduct);




        }
    }

}
