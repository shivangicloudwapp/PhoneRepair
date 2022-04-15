package com.cwt.phonerepair.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.DrawableClickListener;
import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.MainActivity;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener ,GoogleApiClient.OnConnectionFailedListener {


    int PLACE_PICKER_REQUEST    =   1;
    private GoogleApiClient mGoogleApiClient;

    RecyclerView rvourExcluStore,rvBanner;
    Context context;
    ArrayList<HomeStoreModel> modelArrayList;
        ArrayList<HomeBannerModel> bannerList;
  Button btnBookNow;
  TextView tvSeeAll;
  JsonPlaceHolderApi jsonPlaceHolderApi;
  SessionManager sessionManager;
    HomeStoreModel model;
ImageView ivGetCurruntLocation;
    private static final int REQUEST_LOCATION = 1;
    protected LocationManager locationManager;
    double lat = 0.0, lng = 0.0;
    String address = "";


    TextView tvCurrentLocation;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        CurrentLocation();
      /*  mGoogleApiClient = new GoogleApiClient
                .Builder(context)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(),this)
                .build();*/
        return view;

    }

    private void initView(View view) {

        context=getActivity();
        jsonPlaceHolderApi= ApiUtils.getAPIService();
        sessionManager = new SessionManager(context);
        tvSeeAll=view.findViewById(R.id.tvSeeAll);
        rvBanner=view.findViewById(R.id.rvBanner);
        rvourExcluStore=view.findViewById(R.id.rvourExcluStore);
       // ivGetCurruntLocation=view.findViewById(R.id.ivGetCurruntLocation);
        tvCurrentLocation=view.findViewById(R.id.tvCurrentLocation);
        tvSeeAll.setOnClickListener(this);

        tvCurrentLocation.setOnClickListener(this);
/*

    tvCurrentLocation.setDrawableClickListener(new DrawableClickListener() {


            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        //Do something here
                        break;

                    default:
                        break;
                }
            }

        });
*/

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
                        OurExclusiveStoreAdapter adapter1 = new OurExclusiveStoreAdapter(modelArrayList, context
                                /*, new GetHomeStoreId() {
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

        switch(v.getId()) {

            case R.id.tvSeeAll:
                Intent intent =new Intent(getActivity(), AllStoresActivity.class);
                startActivity(intent);
                break;
            case R.id.tvCurrentLocation:

                PlacePicker.IntentBuilder builder   =   new PlacePicker.IntentBuilder();
                Intent intent1;
                try {
                    intent1  =   builder.build(getActivity());
                    startActivityForResult(intent1,PLACE_PICKER_REQUEST );
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

                break;
        }


    }

    private void CurrentLocation() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();

        }
    }

    @SuppressLint("NewApi")
    private void getLocation()
    {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lat = latti;
                lng =longi;
                try {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(getContext(), Locale.getDefault());
                    addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    address = addresses.get(0).getAddressLine(0);
                    tvCurrentLocation.setText(address);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            } else {

                Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location1 != null) {
                    double latti = location1.getLatitude();
                    double longi = location1.getLongitude();
                    lat = latti;
                    lng =longi;
                    try {







                        Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(context, Locale.getDefault());
                        addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        address = addresses.get(0).getAddressLine(0);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

//                Toast.makeText(context, "Unable to Trace your location", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton(""+("yes"), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(""+("No"), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();

                }
                return;
            }

        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if( requestCode == PLACE_PICKER_REQUEST)
        {
            if(resultCode == RESULT_OK)
            {
                Place place =   PlacePicker.getPlace(data,context);
                Double latitude = place.getLatLng().latitude;
                Double longitude = place.getLatLng().longitude;
                String address = String.valueOf(latitude)+String.valueOf(longitude);
                tvCurrentLocation.setText(address);

            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}




