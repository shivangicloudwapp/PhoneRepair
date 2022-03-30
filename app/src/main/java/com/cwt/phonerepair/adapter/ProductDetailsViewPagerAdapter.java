package com.cwt.phonerepair.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cwt.phonerepair.R;

import java.util.List;

public class ProductDetailsViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    List<StoreDetailsViewPagerAdapter> list;
    private Integer[] images={R.drawable.phone3,R.drawable.phone3,R.drawable.phone3,R.drawable.phone3,R.drawable.phone3};

    public ProductDetailsViewPagerAdapter(Context context)
    {
        this.context=context;
    }
    @Override
    public int getCount() {
        return list.size();
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
        View view =layoutInflater.inflate(R.layout.stores_image_slider,null);
        ImageView imageView=view.findViewById(R.id.image_view);
        imageView.setImageResource(images[position]);
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