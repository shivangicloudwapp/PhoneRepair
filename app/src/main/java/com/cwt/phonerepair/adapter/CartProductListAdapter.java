package com.cwt.phonerepair.adapter;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.Interface.UpdateCartInterface;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.activity.CartActivity;
import com.cwt.phonerepair.activity.SubscribeNewStoreActivity;
import com.cwt.phonerepair.activity.serviceActivity.ServiceDetailsCompleteActivity;
import com.cwt.phonerepair.modelclass.response.cart.gettocart.GetToCartModel;
import com.cwt.phonerepair.modelclass.response.cart.gettocart.GetToCartReponse;
import com.cwt.phonerepair.modelclass.response.cart.updatecart.UpdateCartReponse;
import com.cwt.phonerepair.modelclass.service.ServiceCompleteModel;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartProductListAdapter extends RecyclerView.Adapter<CartProductListAdapter.ViewHolder> {

    Context context;

    List<GetToCartModel> modelList;

    UpdateCartInterface updateCartInterface;
    GetToCartModel model;
    String action;
    public CartProductListAdapter(List<GetToCartModel> modelList, Context context, UpdateCartInterface updateCartInterface) {
        this.context = context;
        this.modelList = modelList;
        this.updateCartInterface = updateCartInterface;
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
    public void onBindViewHolder(@NonNull CartProductListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GetToCartModel model = modelList.get(position);
        holder.tvProduct.setText(model.getStatus());

        holder.ivproductAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //...............for add Product........//
                int count = Integer.parseInt(holder.tvProductQty.getText().toString());
                int Ins = count + 1;

                holder.tvProductQty.setText(String.valueOf(Ins));

                //...............update add Product on Cart........//

                updateCartInterface.getUpdateCart(modelList.get(position),action);


            }

        });

        holder.ivproductRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //...............for remove Product........//

                int count = Integer.parseInt(holder.tvProductQty.getText().toString());

                if (count==0){
                }

                else {


                    int Ins=count-1;

                    holder.tvProductQty.setText(String.valueOf(Ins));

                    //...............update remove Product on Cart........//

                    updateCartInterface.getUpdateCart(modelList.get(position),action);
                }



            }
        });


    }





    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProduct,tvProductQty;
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
