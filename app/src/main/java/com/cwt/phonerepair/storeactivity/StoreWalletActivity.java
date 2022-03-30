package com.cwt.phonerepair.storeactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.adapter.ServicesTabPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class StoreWalletActivity extends AppCompatActivity {
    TabLayout tlWallet;
    ViewPager vpWallet;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_wallet);

        vpWallet=findViewById(R.id.vpWallet);
        tlWallet=findViewById(R.id.tlWallet);

        tlWallet.addTab(tlWallet.newTab().setText("Transaction"));
        tlWallet.addTab(tlWallet.newTab().setText("Withdrawal"));

        ServicesTabPagerAdapter adapter=new ServicesTabPagerAdapter(context, getSupportFragmentManager(),tlWallet.getTabCount());
        vpWallet.setAdapter(adapter);
        vpWallet.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlWallet));
        tlWallet.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){


                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    vpWallet.setCurrentItem(tab.getPosition());
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
