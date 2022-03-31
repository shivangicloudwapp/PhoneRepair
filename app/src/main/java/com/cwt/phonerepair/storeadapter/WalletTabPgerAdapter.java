package com.cwt.phonerepair.storeadapter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cwt.phonerepair.fragment.WalletTransactionFragment;
import com.cwt.phonerepair.fragment.WalletWithdrawalFragment;

public class WalletTabPgerAdapter extends FragmentPagerAdapter {

    Context mContext;
    int mTotalTabs;

    public WalletTabPgerAdapter(Context context, FragmentManager fragmentManager, int totalTabs) {
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
                return new WalletTransactionFragment();
            case 1:
                return new WalletWithdrawalFragment();

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return mTotalTabs;
    }
}