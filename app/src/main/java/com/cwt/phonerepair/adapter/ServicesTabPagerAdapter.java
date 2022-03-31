package com.cwt.phonerepair.adapter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.cwt.phonerepair.fragment.ServicesFragment;
import com.cwt.phonerepair.servicesfragment.AcceptedFragment;
import com.cwt.phonerepair.servicesfragment.CompletedFragment;
import com.cwt.phonerepair.servicesfragment.PendingFragment;
import com.cwt.phonerepair.servicesfragment.RejectedFragment;

import java.util.ArrayList;
import java.util.List;

public class ServicesTabPagerAdapter extends FragmentPagerAdapter {

    Context mContext;
    int mTotalTabs;

    public ServicesTabPagerAdapter(Context context, FragmentManager fragmentManager, int totalTabs) {
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
                return new PendingFragment();
            case 1:
                return new RejectedFragment();
            case 2:
                return new AcceptedFragment();
            case 3:
                return new CompletedFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return mTotalTabs;
    }
}