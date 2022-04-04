package com.cwt.phonerepair.adapter;

import android.content.Context;
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
import com.cwt.phonerepair.modelclass.response.AddAddressModel;
import com.cwt.phonerepair.modelclass.response.GetAddressModel;
import com.cwt.phonerepair.modelclass.response.HomeBannerModel;

import java.util.ArrayList;

public class GetAddressAdapter extends RecyclerView.Adapter<GetAddressAdapter.ViewHolder> {

        Context context;

        ArrayList<GetAddressModel> modelList;



    public GetAddressAdapter(ArrayList<GetAddressModel> modelList, Context context) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
@Override
public GetAddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_get_address, viewGroup, false);
    GetAddressAdapter.ViewHolder viewHolder = new GetAddressAdapter.ViewHolder(listItem);
        return viewHolder;
        }

@RequiresApi(api = Build.VERSION_CODES.N)
@Override
public void onBindViewHolder(@NonNull GetAddressAdapter.ViewHolder holder, int position) {
    GetAddressModel model = modelList.get(position);
    holder.tvType.setText(model.getType());
    holder.tvName.setText(model.getName());
    holder.tvPhoneNum.setText(model.getContactNumber());
    holder.tvAddress.setText(model.getAddress());
/*
        holder.cvComplete.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

        Intent intent = new Intent(view.getContext(), ServiceDetailsCompleteActivity.class);
        context.startActivity(intent);

        }
        });


        }*/
}
@Override
public int getItemCount() {
        return modelList.size();
        }


public class ViewHolder extends RecyclerView.ViewHolder {
    TextView tvType,tvName,tvPhoneNum,tvAddress;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tvType = itemView.findViewById(R.id.tvType);
        tvName = itemView.findViewById(R.id.tvName);
        tvPhoneNum = itemView.findViewById(R.id.tvPhoneNum);
        tvAddress = itemView.findViewById(R.id.tvAddress);




    }
}

}