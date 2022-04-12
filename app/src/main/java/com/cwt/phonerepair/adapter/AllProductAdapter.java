package com.cwt.phonerepair.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.cwt.phonerepair.Interface.GetHomeStoreId;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.Allurls;
import com.cwt.phonerepair.activity.ProductDetailsActivity;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementModel;
import com.cwt.phonerepair.modelclass.response.getStoreallProdcut.GetStoreAllProdcutModel;
import com.cwt.phonerepair.modelclass.response.home.HomeStoreModel;
import com.cwt.phonerepair.storeactivity.AddProductActivity;
import com.cwt.phonerepair.storeactivity.StoreHomeActivity;
import com.cwt.phonerepair.storeadapter.ProductManagementAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.ViewHolder> {

    Context context;

    ArrayList<GetStoreAllProdcutModel> modelList;


    public AllProductAdapter(ArrayList<GetStoreAllProdcutModel> modelList, Context context) {
        this.context = context;
        this.modelList = modelList;

    }


    @NonNull
    @Override
    public AllProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.all_product_list, viewGroup, false);
        AllProductAdapter.ViewHolder viewHolder = new AllProductAdapter.ViewHolder(listItem);


        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull AllProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GetStoreAllProdcutModel model = modelList.get(position);
        holder.tvProductName.setText(model.getTitle());
        holder.tvProductPrice.setText(Integer.toString(model.getPrice()));

        Picasso.with(context).load(Allurls.ImageUrl+model.getProductImage()).fit().centerCrop()
                .placeholder(R.drawable.group1042)
                .into(holder.ivProdcut);


        holder.rlProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductDetailsActivity.class);
                intent.putExtra("data",modelList.get(position));
                System.out.println("product_id....allProduct"+modelList.get(position));

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName,tvProductPrice;

        ImageView ivProdcut,menu;

        RelativeLayout rlProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            ivProdcut = itemView.findViewById(R.id.ivProdcut);
            rlProduct = itemView.findViewById(R.id.rlProduct);
            menu = itemView.findViewById(R.id.menu);


        }
    }

}
