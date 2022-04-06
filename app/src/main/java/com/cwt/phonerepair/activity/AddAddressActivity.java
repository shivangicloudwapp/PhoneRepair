package com.cwt.phonerepair.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.modelclass.parameter.AddAddressParameter;
import com.cwt.phonerepair.modelclass.response.addAddress.AddAddressResponse;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBackAddAddress;
    Button btnSubmit;

    LinearLayout llHome,llWork,llOther;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;
    Context context;
    EditText etFlatNo,etLandmark,etAlternateNum,etName,etContact,etAddress;
       String flatNum,landmark,alternateNum,name,contactNum,address;

    String Type="home";
    String latitude, longitude;
    private static final int REQUEST_LOCATION = 1;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        initView();


        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


    }

    private void initView() {
        context= AddAddressActivity.this;
         jsonPlaceHolderApi= ApiUtils.getAPIService();
        sessionManager= new SessionManager(context);
        btnSubmit=findViewById(R.id.btnSubmit);
        ivBackAddAddress=findViewById(R.id.ivBackAddAddress);
        etFlatNo=findViewById(R.id.etFlatNo);
        etLandmark=findViewById(R.id.etLandmark);
        etAlternateNum=findViewById(R.id.etAlternateNum);
        etName=findViewById(R.id.etName);
        etContact=findViewById(R.id.etContact);
        etAddress=findViewById(R.id.etAddress);
        llHome=findViewById(R.id.llHome);
        llWork=findViewById(R.id.llWork);
        llOther=findViewById(R.id.llOther);

        ivBackAddAddress.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        llHome.setOnClickListener(this);
        llWork.setOnClickListener(this);
        llOther.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnSubmit:
                flatNum=etFlatNo.getText().toString().trim();
                landmark=etLandmark.getText().toString().trim();
                alternateNum=etAlternateNum.getText().toString().trim();
                name=etName.getText().toString().trim();
                contactNum=etContact.getText().toString().trim();
                address=etAddress.getText().toString().trim();

                 locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();
                } else {
                    getLocation();
                }

                if (flatNum.isEmpty()) {
                    etFlatNo.setError("Please Enter Flat No / Office / Floor");
                    etFlatNo.requestFocus();
                    return;
                }

               /* else if (landmark.isEmpty()) {
                    etLandmark.setError("Please Enter Landmark");
                    etLandmark.requestFocus();
                    return;
                }*/
/*
                else if (alternateNum.isEmpty()) {
                    etLandmark.setError("Please Enter Landmark");
                    etLandmark.requestFocus();
                    return;
                }*/


               else if (name.isEmpty()) {
                    etName.setError("Please Enter Name");
                    etName.requestFocus();
                    return;
                }

                else if (contactNum.isEmpty()) {
                    etContact.setError("Please Enter Contact Number");
                    etContact.requestFocus();
                    return;
                }

                else if (address.isEmpty()) {
                    etAddress.setError("Please Enter Address");
                    etAddress.requestFocus();
                    return;
                }

                else {

                    if (Utils.checkConnection(context)) {

                        addAddress();
                    } else {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }



                break;
            case R.id.ivBackAddAddress:
                onBackPressed();
                break;
            case R.id.llHome:
                Type="home";
                Toast.makeText(context, Type, Toast.LENGTH_SHORT).show();
                break;
            case R.id.llWork:
                Type="work";

                Toast.makeText(context, Type, Toast.LENGTH_SHORT).show();

                break;

            case R.id.llOther:
                Type="other";

                    Toast.makeText(context, Type, Toast.LENGTH_SHORT).show();


            default:
                break;



        }


    }

    private void OnGPS()   {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                AddAddressActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                AddAddressActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
             //   showLocation.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addAddress() {
        Customprogress.showPopupProgressSpinner(context,true);
        AddAddressParameter parameter= new AddAddressParameter(flatNum,landmark,alternateNum,name,contactNum,Type,latitude,longitude,address);
        Call<AddAddressResponse>call=jsonPlaceHolderApi.AddAddress(parameter,"Bearer "+sessionManager.getSavedToken());
        call.enqueue(new Callback<AddAddressResponse>() {
            @Override
            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus()){

                        Intent intent =new Intent(AddAddressActivity.this, ServiceRequestSuccessActivity.class);
                           startActivity(intent);

                    }

                    else {
                        Toast.makeText(AddAddressActivity.this, "failed...."+response.body().getMassage(), Toast.LENGTH_SHORT).show();

                    }

                }

            }


            @Override
            public void onFailure(Call<AddAddressResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
}