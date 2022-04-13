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

import com.cwt.phonerepair.Interface.GetAddressIdInterface;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.modelclass.response.getAddress.GetAddressModel;

import java.util.ArrayList;

public class GetCartAddressAdapter extends RecyclerView.Adapter<GetCartAddressAdapter.ViewHolder> {

        Context context;

        ArrayList<GetAddressModel> modelList;
        GetAddressIdInterface getAddressIdInterface;



public GetCartAddressAdapter(ArrayList<GetAddressModel> modelList, Context context,GetAddressIdInterface getAddressIdInterface) {
        this.context = context;
        this.modelList = modelList;
     this.getAddressIdInterface=getAddressIdInterface;
        }

@NonNull
@Override
public GetCartAddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_cart_address, viewGroup, false);
    GetCartAddressAdapter.ViewHolder viewHolder = new GetCartAddressAdapter.ViewHolder(listItem);
        return viewHolder;
        }

@RequiresApi(api = Build.VERSION_CODES.N)
@Override
public void onBindViewHolder(@NonNull GetCartAddressAdapter.ViewHolder holder, int position) {
        GetAddressModel model = modelList.get(position);
        holder.tvAddType.setText(model.getType());
        holder.tvAddress.setText(model.getAddress());

        getAddressIdInterface.getAddressId(modelList.get(position));

        }
@Override
public int getItemCount() {
        return modelList.size();
        }


public class ViewHolder extends RecyclerView.ViewHolder {
    TextView tvAddType,tvAddress;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tvAddType = itemView.findViewById(R.id.tvAddType);
        tvAddress = itemView.findViewById(R.id.tvAddress);

    }
}

}