package com.cwt.phonerepair.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.GetHomeStoreId;
import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.activity.AllStoresActivity;
import com.cwt.phonerepair.adapter.BannerAdapter;
import com.cwt.phonerepair.adapter.OurExclusiveStoreAdapter;
import com.cwt.phonerepair.modelclass.response.home.HomeBannerModel;
import com.cwt.phonerepair.modelclass.response.home.HomeResponse;
import com.cwt.phonerepair.modelclass.response.home.HomeStoreModel;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {



    RecyclerView rvourExcluStore,rvBanner;
    Context context;
    ArrayList<HomeStoreModel> modelArrayList;
        ArrayList<HomeBannerModel> bannerList;
  Button btnBookNow;
  TextView tvSeeAll;
  JsonPlaceHolderApi jsonPlaceHolderApi;
  SessionManager sessionManager;
    HomeStoreModel model;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        return view;

    }

    private void initView(View view) {

        context=getActivity();
        jsonPlaceHolderApi= ApiUtils.getAPIService();
        sessionManager = new SessionManager(context);
        tvSeeAll=view.findViewById(R.id.tvSeeAll);
        rvBanner=view.findViewById(R.id.rvBanner);
        rvourExcluStore=view.findViewById(R.id.rvourExcluStore);
        tvSeeAll.setOnClickListener(this);
        modelArrayList=new ArrayList<>() ;
        bannerList=new ArrayList<>();

        if (Utils.checkConnection(context)) {
            stores();

        } else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }

    private void stores() {

        Customprogress.showPopupProgressSpinner(context, true);
        Call<HomeResponse> call = jsonPlaceHolderApi.Home("Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                if (response.isSuccessful()) {
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (response.body().getStatus()) {
                        bannerList= (ArrayList<HomeBannerModel>) response.body().getData().getBanner();
                        BannerAdapter adapter = new BannerAdapter( bannerList,context);
                        rvBanner.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                        rvBanner.setAdapter( adapter);
                        rvourExcluStore.setHasFixedSize(true);


                        modelArrayList= (ArrayList<HomeStoreModel>) response.body().getData().getStore();
                        OurExclusiveStoreAdapter adapter1 = new OurExclusiveStoreAdapter(modelArrayList, context/*, new GetHomeStoreId() {
                            @Override
                            public void getHomeStoreId(HomeStoreModel storeModel) {
                                System.out.println("store....id..."+storeModel.getId());

                                model=storeModel;

                            }
                        }*/);
                        rvourExcluStore.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                        rvourExcluStore.setAdapter( adapter1);
                        rvourExcluStore.setHasFixedSize(true);


                    }
                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
               Customprogress.showPopupProgressSpinner(context, false);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v==tvSeeAll){
            Intent intent =new Intent(getActivity(), AllStoresActivity.class);
            startActivity(intent);
        }

       /* else if (v==btnBookNow){
            Intent intent =new Intent(getActivity(), BookServiceActivity.class);
            startActivity(intent);

        }*/
    }
}