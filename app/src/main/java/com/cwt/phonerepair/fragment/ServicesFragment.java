package com.cwt.phonerepair.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.adapter.ServicesTabPagerAdapter;
import com.cwt.phonerepair.servicesfragment.AcceptedFragment;
import com.cwt.phonerepair.servicesfragment.CompletedFragment;
import com.cwt.phonerepair.servicesfragment.PendingFragment;
import com.cwt.phonerepair.servicesfragment.RejectedFragment;
import com.google.android.material.tabs.TabLayout;

public class ServicesFragment extends Fragment {
TabLayout tlServices;
ViewPager vpService;
   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        vpService = view.findViewById(R.id.vpService);
        tlServices = view.findViewById(R.id.tlServices);

        tlServices.addTab(tlServices.newTab().setText("Pending"));
        tlServices.addTab(tlServices.newTab().setText("Rejected"));
        tlServices.addTab(tlServices.newTab().setText("Accepted"));
        tlServices.addTab(tlServices.newTab().setText("Completed"));
        tlServices.setTabGravity(TabLayout.GRAVITY_CENTER);

        ServicesTabPagerAdapter adapter=new ServicesTabPagerAdapter(getContext(),getChildFragmentManager(),tlServices.getTabCount());
        vpService.setAdapter(adapter);
        vpService.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlServices));
        tlServices.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                vpService.setCurrentItem(tab.getPosition());
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