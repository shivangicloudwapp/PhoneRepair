package com.cwt.phonerepair.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cwt.phonerepair.R;

import java.util.ArrayList;

public class SsmImageAdapter  extends BaseAdapter {
    private Context ctx;
    private int pos;
    private LayoutInflater inflater;
    private ImageView ivGallery,ivRemoveImg;
    ArrayList<Uri> mArrayUri;
    PredicateLayout predicate;
    public SsmImageAdapter(Context ctx, ArrayList<Uri> mArrayUri) {

        this.ctx = ctx;
        this.mArrayUri = mArrayUri;
    }

    @Override
    public int getCount() {
        return mArrayUri.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayUri.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        pos = position;
        inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.gv_image_subscription_new_store, parent, false);

        ivGallery = (ImageView) itemView.findViewById(R.id.ivStoreImg);
        ivRemoveImg = (ImageView) itemView.findViewById(R.id.ivRemoveImg);

        ivGallery.setImageURI(mArrayUri.get(position));

        predicate=new PredicateLayout(ctx);

        ivRemoveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivRemoveImg.setImageBitmap(null);
                ivRemoveImg.setBackground(null);
                ivGallery.setImageBitmap(null);
                predicate.removeView(ivGallery);
            }
        });

        return itemView;
    }


}