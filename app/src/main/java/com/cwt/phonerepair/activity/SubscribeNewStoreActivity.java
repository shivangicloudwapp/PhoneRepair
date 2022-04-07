package com.cwt.phonerepair.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.adapter.GalleryAdapter;
import com.cwt.phonerepair.adapter.SsmImageAdapter;
import com.cwt.phonerepair.modelclass.response.sbscriptionstore.SubscriptionStoreResponse;
import com.cwt.phonerepair.modelclass.response.subscriptionPlan.SubscriptionPlanModel;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscribeNewStoreActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSave;
    ImageView ivBackSubNewstore, ivStoreImg, ivSsmImg;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;
    Context context;

    EditText etStoreName, etRegistrationNumber, etAddress, etAboutStore;
    LinearLayout llStoreImg, llSsmImg;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    String picturePath;
    String SetImage = "StoreImg";

    private Button btn;
    int PICK_IMAGE_MULTIPLE = 1;
    int PICK_IMAGE_MULTIPLE_SSM = 2;
    String imageEncoded;
    List<String> imagesEncodedList;
    private GridView gridViewImageStore,gridViewSsmImage;
     GalleryAdapter galleryAdapter;
SsmImageAdapter ssmImageAdapter;
    ArrayList<Uri> mArrayUri;

    ArrayList<SubscriptionPlanModel> modelArrayList;

    String price, title, duration, items, startdate, enddate, planId;

    SubscriptionPlanModel subscriptionPlanModelMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_new_store);
        initView();
/*

        if (checkAndRequestPermission(SubscribeNewStoreActivity.this)) {
            ChoosestoreImage(SubscribeNewStoreActivity.this);
        }
*/
/* Intent intent = getIntent();
       // modelArrayList = (ArrayList<SubscriptionPlanModel>)getIntent().getSerializableExtra("planList");
        imageList=intent.getStringArrayListExtra("planList");*/

        getData();

             /*  Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<SubscriptionPlanModel> object = new ArrayList<>();
        args.getSerializable("PlanList");
          price = object.get(0).getPrice();
          title = object.get(1).getPrice();
          duration = object.get(2).getPrice();
        items = object.get(3).getPrice();
        startdate = object.get(4).getPrice();
        enddate = object.get(5).getPrice();
        planId = object.get(6).getPrice();

        Toast.makeText(this, "listData"+object, Toast.LENGTH_SHORT).show();
        Log.d("kkkkk", String.valueOf(modelArrayList.size()));*/

    }

        private void getData() {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                subscriptionPlanModelMain = (SubscriptionPlanModel) intent.getSerializableExtra("data");
                System.out.println(""+subscriptionPlanModelMain.getTitle());
                title = subscriptionPlanModelMain.getTitle();
                duration = subscriptionPlanModelMain.getDuration();
                items = subscriptionPlanModelMain.getItems();
                price = subscriptionPlanModelMain.getPrice();
                startdate = "";
                enddate = "";
                planId = subscriptionPlanModelMain.getId().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*
    private boolean checkAndRequestPermission(SubscribeNewStoreActivity subscribeNewStoreActivity) {

        int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;

    }
*/

    private void initView() {
        context = SubscribeNewStoreActivity.this;
        sessionManager = new SessionManager(this);
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        modelArrayList = new ArrayList<>();


        btnSave = findViewById(R.id.btnSave);
        ivBackSubNewstore = findViewById(R.id.ivBackSubNewstore);
        etStoreName = findViewById(R.id.etStoreName);
        etRegistrationNumber = findViewById(R.id.etRegistrationNumber);
        etAddress = findViewById(R.id.etAddress);
        etAboutStore = findViewById(R.id.etAboutStore);
        llStoreImg = findViewById(R.id.llStoreImg);
        llSsmImg = findViewById(R.id.llSsmImg);
        gridViewSsmImage = findViewById(R.id.gridViewSsmImage);
        gridViewImageStore = findViewById(R.id.gridViewImageStore);


        btnSave.setOnClickListener(this);
        ivBackSubNewstore.setOnClickListener(this);
        llStoreImg.setOnClickListener(this);
        llSsmImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:

                  subScribeNewStore();

                showBottomSheetDialog();
                break;

            case R.id.llStoreImg:

                SetImage = "StoreImg";
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
                //ChoosestoreImage(context);
                break;

            case R.id.llSsmImg:
                SetImage = "SsmImg";
                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Select Picture"), PICK_IMAGE_MULTIPLE);

              //  ChooseSSmImage(context);
                break;
            case R.id.ivBackSubNewstore:
                onBackPressed();
                break;

            default:
        }
    }

  /*  private void ChooseSSmImage(Context context) {

        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit"}; // create a menuOption Array
        // create a dialog for showing the optionsMenu
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // set the items in builder
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (optionsMenu[i].equals("Take Photo")) {
                    // Open the camera and get the photo
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (optionsMenu[i].equals("Choose from Gallery")) {
                    // choose from  external storage
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);
                } else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }

    private void ChoosestoreImage(Context context) {

        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit"}; // create a menuOption Array
        // create a dialog for showing the optionsMenu
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // set the items in builder
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (optionsMenu[i].equals("Take Photo")) {
                    // Open the camera and get the photo
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (optionsMenu[i].equals("Choose from Gallery")) {
                    // choose from  external storage
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);
                } else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }
*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                            .show();
                } else if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "FlagUp Requires Access to Your Storage.",
                            Toast.LENGTH_SHORT).show();
                } /*else if (SetImage.equals("StoreImg")) {
                   // ChoosestoreImage(this);

                } else {

                    //ChooseSSmImage(this);
                }*/
                break;
        }


    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();
                    Cursor cursor = getContentResolver().query(mImageUri, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();
                    ArrayList<Uri> mArrayUri = new ArrayList<>();
                    mArrayUri.add(mImageUri);
                    galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                    gridView.setAdapter(galleryAdapter);
                    gridView.setHorizontalSpacing(gridView.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridView.getLayoutParams();
                    mlp.setMargins(0, gridView.getHorizontalSpacing(), 0, 0);

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                            galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                            gridView.setAdapter(galleryAdapter);
                            gridView.setHorizontalSpacing(gridView.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridView
                                    .getLayoutParams();
                            mlp.setMargins(0, gridView.getHorizontalSpacing(), 0, 0);

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(mImageUri);


                    if (SetImage.equals("StoreImg")){
                        galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                        gridViewImageStore.setAdapter(galleryAdapter);
                        gridViewImageStore.setVerticalSpacing(gridViewImageStore.getHorizontalSpacing());
                        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewImageStore
                                .getLayoutParams();
                        mlp.setMargins(0, gridViewImageStore.getHorizontalSpacing(), 0, 0);
                    }

                    else if  (SetImage.equals("SsmImg")) {
                        ssmImageAdapter = new SsmImageAdapter(getApplicationContext(),mArrayUri);
                        gridViewSsmImage.setAdapter(galleryAdapter);
                        gridViewSsmImage.setVerticalSpacing(gridViewSsmImage.getHorizontalSpacing());
                        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewSsmImage
                                .getLayoutParams();
                        mlp.setMargins(0, gridViewSsmImage.getHorizontalSpacing(), 0, 0);
                    }



                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                            if (SetImage.equals("StoreImg")){
                                galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                                gridViewImageStore.setAdapter(galleryAdapter);
                                gridViewImageStore.setVerticalSpacing(gridViewImageStore.getHorizontalSpacing());
                                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewImageStore
                                        .getLayoutParams();
                                mlp.setMargins(0, gridViewImageStore.getHorizontalSpacing(), 0, 0);
                            }

                            else if  (SetImage.equals("SsmImg")) {
                                ssmImageAdapter = new SsmImageAdapter(getApplicationContext(),mArrayUri);
                                gridViewSsmImage.setAdapter(galleryAdapter);
                                gridViewSsmImage.setVerticalSpacing(gridViewSsmImage.getHorizontalSpacing());
                                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewSsmImage
                                        .getLayoutParams();
                                mlp.setMargins(0, gridViewSsmImage.getHorizontalSpacing(), 0, 0);
                            }

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }





   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");

                        if (SetImage.equals("StoreImg")){
                            ivStoreImg.setImageBitmap(selectedImage);

                        }

                        else if  (SetImage.equals("SsmImg")) {
                            ivSsmImg.setImageBitmap(selectedImage);

                        }


                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                picturePath = cursor.getString(columnIndex);


                                if (SetImage.equals("StoreImg")){
                                    ivStoreImg.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                                }

                                else if  (SetImage.equals("SsmImg")) {
                                    ivSsmImg.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                                }

                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }
*/
    public void subScribeNewStore() {
        Customprogress.showPopupProgressSpinner(context,true);
        HashMap<String, RequestBody> data = new HashMap<>();


        data.put("plan_title", createRequestBody(subscriptionPlanModelMain.getTitle()));
        data.put("plan_duration", createRequestBody(subscriptionPlanModelMain.getDuration()));
        data.put("plan_items", createRequestBody(subscriptionPlanModelMain.getItems()));
        data.put("plan_price", createRequestBody(subscriptionPlanModelMain.getPrice()));
        data.put("start_date", createRequestBody(""));
        data.put("end_date", createRequestBody(""));
        data.put("registration_no", createRequestBody(etRegistrationNumber.getText().toString()));
        data.put("address", createRequestBody(etAddress.getText().toString().trim()));
        data.put("about_store", createRequestBody(etAboutStore.getText().toString().trim()));
        data.put("plan_id", createRequestBody(subscriptionPlanModelMain.getId().toString()));
        data.put("store_name", createRequestBody(etStoreName.getText().toString().trim()));

        List<MultipartBody.Part> plan_image = new ArrayList<>();

        if (mArrayUri != null && !mArrayUri.equals("")) {
            File file = new File(String.valueOf(mArrayUri));
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            plan_image = Collections.singletonList(MultipartBody.Part.createFormData("plan_image", file.getName(), requestFile));
        }

        List<MultipartBody.Part> store_image = new ArrayList<>();

        if (mArrayUri != null && !mArrayUri.equals("")) {
            File file = new File(String.valueOf(mArrayUri));
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            store_image = Collections.singletonList(MultipartBody.Part.createFormData("store_image", file.getName(), requestFile));
        }


        Call<SubscriptionStoreResponse>call=jsonPlaceHolderApi.SubscriptionStore("Bearer "+sessionManager.getSavedToken(),data,plan_image,store_image);
        call.enqueue(new Callback<SubscriptionStoreResponse>() {
            @Override
            public void onResponse(Call<SubscriptionStoreResponse> call, Response<SubscriptionStoreResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);

                if (response.isSuccessful()){

                    if (response.body().getStatus()){

                        Intent intent= new Intent(SubscribeNewStoreActivity.this,DashboardActivity.class);
                        startActivity(intent);

                    }
                }

            }

            @Override
            public void onFailure(Call<SubscriptionStoreResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Toast.makeText(SubscribeNewStoreActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }




    private RequestBody createRequestBody(String s) {

        return RequestBody.create(MediaType.parse("multipart/form-data"), s);

    }

    private void showBottomSheetDialog() {


        Dialog customdialog = new Dialog(this);
        customdialog.setContentView(R.layout.subscribe_new_store_dailog);
        Window window = customdialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        window.setGravity(Gravity.CENTER);
        customdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button btnSave1 = customdialog.findViewById(R.id.btnDone);
        btnSave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                

                customdialog.cancel();
            }


        });



        customdialog.show();
    }
}