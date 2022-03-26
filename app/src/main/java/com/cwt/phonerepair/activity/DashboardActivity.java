package com.cwt.phonerepair.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.cwt.phonerepair.R;
import com.cwt.phonerepair.fragment.HomeFragment;
import com.cwt.phonerepair.fragment.OrderFragment;
import com.cwt.phonerepair.fragment.ProfileFragment;
import com.cwt.phonerepair.fragment.ServicesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity  {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        bottomNavigationView=findViewById(R.id.bottom_navigation);



        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentMenu, new HomeFragment()).commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.services:
                        selectedFragment = new ServicesFragment();
                        break;
                    case R.id.order:
                        selectedFragment = new OrderFragment();
                        break;

                    case R.id.profile:
                        selectedFragment = new ProfileFragment();
                        break;
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentMenu, selectedFragment)
                        .commit();
                return true;
            }

        });











    }

}