package com.cwt.phonerepair.storeadapter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cwt.phonerepair.storeserviceorderfragment.StoreServiceOrderAcceptedFragment;
import com.cwt.phonerepair.storeserviceorderfragment.StoreServiceOrderCompletedFragment;
import com.cwt.phonerepair.storeserviceorderfragment.StoreServiceOrderPendingFragment;
import com.cwt.phonerepair.storeserviceorderfragment.StoreServiceOrderRejectedFragment;

public class StoreServiceOrderTabPagerAdapter extends FragmentPagerAdapter {

    Context mContext;
    int mTotalTabs;

    public StoreServiceOrderTabPagerAdapter(Context context, FragmentManager fragmentManager, int totalTabs) {
        super(fragmentManager);
        mContext = context;
        mTotalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d("asasas", position + "");
        switch (position) {
            case 0:
                return new StoreServiceOrderPendingFragment();
            case 1:
                return new StoreServiceOrderRejectedFragment();
            case 2:
                return new StoreServiceOrderAcceptedFragment();

            case 3:
                return new StoreServiceOrderCompletedFragment();


            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return mTotalTabs;
    }
}