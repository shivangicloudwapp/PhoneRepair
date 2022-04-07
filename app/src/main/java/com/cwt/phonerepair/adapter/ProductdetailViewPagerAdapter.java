package com.cwt.phonerepair.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cwt.phonerepair.Interface.GetStoreId;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.Allurls;
import com.cwt.phonerepair.modelclass.response.allStores.AllStoreModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductdetailViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    //  List<StoreDetailsViewPagerAdapter>list;

    ArrayList<AllStoreModel> models;
    GetStoreId getStoreId;
    //private Integer[] images={R.drawable.viepagerimg,R.drawable.viepagerimg,R.drawable.viepagerimg,R.drawable.viepagerimg,R.drawable.viepagerimg};


    public ProductdetailViewPagerAdapter(Context context, ArrayList<AllStoreModel> models) {
        this.context = context;
        this.models = models;
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );

        AllStoreModel detailsModel=models.get(position);
        View view =layoutInflater.inflate(R.layout.stores_image_slider,null);
        ImageView imageView=view.findViewById(R.id.image_view);

        Picasso.with(context).load(Allurls.ImageUrl+detailsModel.getStoreImage()).fit().centerCrop()
                .placeholder(R.drawable.group1042)
                .into(imageView);

        ViewPager viewPager=(ViewPager) container;
        viewPager.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager=(ViewPager) container;
        View view=(View) object;
        viewPager.removeView(view);
    }
}