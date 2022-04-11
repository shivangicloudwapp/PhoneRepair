package com.cwt.phonerepair.storeadapter;

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

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.Allurls;
import com.cwt.phonerepair.activity.SubscribeNewStoreActivity;
import com.cwt.phonerepair.activity.SubscriptionPlanActivity;
import com.cwt.phonerepair.modelclass.ProductModel;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementModel;
import com.cwt.phonerepair.storeactivity.AddProductActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductManagementAdapter extends RecyclerView.Adapter<ProductManagementAdapter.ViewHolder> {

    Context context;

    ArrayList<ProductManagementModel> modelList;

    public ProductManagementAdapter(ArrayList<ProductManagementModel> modelList, Context context) {
        this.context = context;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public ProductManagementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_product_management, viewGroup, false);
        ProductManagementAdapter.ViewHolder viewHolder = new ProductManagementAdapter.ViewHolder(listItem);



        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ProductManagementAdapter.ViewHolder holder, int position) {
        ProductManagementModel model = modelList.get(position);
        holder.tvProductName.setText(model.getTitle());
        holder.tvProductPrice.setText(Integer.toString(model.getPrice()));

        Picasso.with(context).load(Allurls.ImageUrl+model.getProductImage()).fit().centerCrop()
                .placeholder(R.drawable.group1042)
                .into(holder.ivProdcut);


        holder.rlProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddProductActivity.class);
                intent.putExtra("store_id", (Serializable) modelList.get(position));
                context.startActivity(intent);
            }
        });

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, holder.menu);
                popup.getMenuInflater().inflate(R.menu.poupup_menu_edit_delete, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
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
