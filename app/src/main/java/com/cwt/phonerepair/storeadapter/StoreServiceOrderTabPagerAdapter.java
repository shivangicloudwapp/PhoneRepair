package com.cwt.phonerepair.storeadapter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cwt.phonerepair.fragment.WalletTransactionFragment;
import com.cwt.phonerepair.fragment.WalletWithdrawalFragment;
import com.cwt.phonerepair.storefragment.StoreServiceAcceptedFragment;
import com.cwt.phonerepair.storefragment.StoreServiceCompletedFragment;
import com.cwt.phonerepair.storefragment.StoreServicePendingFragment;
import com.cwt.phonerepair.storefragment.StoreServiceRejectedFragment;

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
                return new StoreServicePendingFragment();
            case 1:
                return new StoreServiceRejectedFragment();
            case 2:
                return new StoreServiceAcceptedFragment();

            case 3:
                return new StoreServiceCompletedFragment();


            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return mTotalTabs;
    }
}