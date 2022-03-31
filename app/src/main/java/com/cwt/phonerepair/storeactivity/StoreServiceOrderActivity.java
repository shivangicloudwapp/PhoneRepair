package com.cwt.phonerepair.storeactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.adapter.ServicesTabPagerAdapter;
import com.cwt.phonerepair.storeadapter.StoreServiceOrderTabPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class StoreServiceOrderActivity extends AppCompatActivity {


    TabLayout tlServiceOrder;
    ViewPager vpServiceOrder;
Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_service_order);

        initView();
    }

    private void initView() {


        tlServiceOrder = findViewById(R.id.tlServiceOrder);
        vpServiceOrder = findViewById(R.id.vpServiceOrder);

        tlServiceOrder.addTab(tlServiceOrder.newTab().setText("Pending"));
        tlServiceOrder.addTab(tlServiceOrder.newTab().setText("Rejected"));
        tlServiceOrder.addTab(tlServiceOrder.newTab().setText("Accepted"));
        tlServiceOrder.addTab(tlServiceOrder.newTab().setText("Completed"));
        tlServiceOrder.setTabGravity(TabLayout.GRAVITY_CENTER);

        StoreServiceOrderTabPagerAdapter adapter=new StoreServiceOrderTabPagerAdapter(context,getSupportFragmentManager(),tlServiceOrder.getTabCount());
        vpServiceOrder.setAdapter(adapter);
        vpServiceOrder.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlServiceOrder));
        tlServiceOrder.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                vpServiceOrder.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}