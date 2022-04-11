package com.cwt.phonerepair.activity;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.modelclass.response.sbscriptionstore.SubscriptionStoreResponse;
import com.cwt.phonerepair.modelclass.response.subscriptionPlan.SubscriptionPlanModel;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    ImageView ivBackSubNewstore;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;
    Context context;
    ArrayList<String> imageStoreList = new ArrayList<>();
    ArrayList<String> ssmImageStoreList = new ArrayList<>();
    EditText etStoreName, etRegistrationNumber, etAddress, etAboutStore;
    LinearLayout llStoreImg, llSsmImg;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    String picturePath;
    String SetImage = "StoreImg";
    GalleryAdapter galleryAdapter;
    private Button btn;
    int PICK_IMAGE_MULTIPLE = 1;
    int PICK_IMAGE_MULTIPLE_SSM = 2;
    String imageEncoded;
    List<String> imagesEncodedList;
    private GridView gridViewImageStore, gridViewSsmImage;
    SsmImageAdapter ssmImageAdapter;
    ArrayList<Uri> mArrayUri;
    String imgDecodableString;

    ArrayList<SubscriptionPlanModel> modelArrayList;
    String price, title, duration, items, startdate, enddate, planId;
    SubscriptionPlanModel subscriptionPlanModelMain;
    public static final int REQUEST_STORAGE = 2;
    public static final int SELECT_FILE = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_new_store);
        initView();

        getData();

    }

    private void getData() {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                subscriptionPlanModelMain = (SubscriptionPlanModel) intent.getSerializableExtra("data");
                System.out.println("store.Data.." + subscriptionPlanModelMain.getTitle());
                title = subscriptionPlanModelMain.getTitle();
                duration = subscriptionPlanModelMain.getDuration();
                items = subscriptionPlanModelMain.getItems();
                price = subscriptionPlanModelMain.getPrice();
                startdate = subscriptionPlanModelMain.getCreatedAt();
                enddate = subscriptionPlanModelMain.getIsDeleted();
                planId = String.valueOf(subscriptionPlanModelMain.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
                String storeName = etStoreName.getText().toString();
                String registrartionNum = etRegistrationNumber.getText().toString();
                String address = etAddress.getText().toString();
                String aboutStore = etAboutStore.getText().toString();

                if (TextUtils.isEmpty(storeName)) {
                    Toast.makeText(context, "Please Enter a Store Name", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(registrartionNum)) {
                    Toast.makeText(context, "Please Enter Registration Number", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(address)) {
                    Toast.makeText(context, "Please Enter Address", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(aboutStore)) {
                    Toast.makeText(context, "Please Enter About Store", Toast.LENGTH_SHORT).show();

                } else {

                    if (Utils.checkConnection(context)) {

                        subScribeNewStore();
                        showBottomSheetDialog();

                    } else {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

               /* btnImg2.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (img1Selected2 != null) {
                            img2Selected1 = "fulfilled";
                            choosePictureAction();
                        }else {
                            Log.e("Please select first image", "Please select first image");
                        }
                    }
                });*/

                break;

            case R.id.llStoreImg:
                SetImage = "StoreImg";
                checkPermissionsForStorage();
               /* Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
                */
                //ChoosestoreImage(context);
                break;

            case R.id.llSsmImg:
                SetImage = "SsmImg";

                checkPermissionsForStorage();

            /*    Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Select Picture"), PICK_IMAGE_MULTIPLE_SSM);*/
                //  ChooseSSmImage(context);
                break;
            case R.id.ivBackSubNewstore:
                onBackPressed();
                break;

            default:
        }
    }

    private void checkPermissionsForStorage() {

        if (ActivityCompat.checkSelfPermission(((Activity) context), WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(((Activity) context), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            System.out.println("GALLERY OPEN 22");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
       //    true intent.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY, -1);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);


        } else {
            requestStoragePermission();
        }
    }

    private void requestStoragePermission() {

        {
            try {
                ActivityCompat.requestPermissions(((Activity) context), new String[]{WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception>>", e.toString());
            }
        }
    }

    public void subScribeNewStore() {
        Customprogress.showPopupProgressSpinner(context, true);
        HashMap<String, RequestBody> data = new HashMap<>();
        data.put("plan_title", createRequestBody(subscriptionPlanModelMain.getTitle()));
        data.put("plan_duration", createRequestBody(subscriptionPlanModelMain.getDuration()));
        data.put("plan_items", createRequestBody(subscriptionPlanModelMain.getItems()));
        data.put("plan_price", createRequestBody(subscriptionPlanModelMain.getPrice()));
        data.put("start_date", createRequestBody(subscriptionPlanModelMain.getCreatedAt()));
        data.put("end_date", createRequestBody(subscriptionPlanModelMain.getIsDeleted()));
        data.put("registration_no", createRequestBody(etRegistrationNumber.getText().toString()));
        data.put("address", createRequestBody(etAddress.getText().toString().trim()));
        data.put("about_store", createRequestBody(etAboutStore.getText().toString().trim()));
        data.put("plan_id", createRequestBody(subscriptionPlanModelMain.getId().toString()));
        data.put("store_name", createRequestBody(etStoreName.getText().toString().trim()));

        List<MultipartBody.Part> plan_image = new ArrayList<>();
        for (int i = 0; i < imageStoreList.size(); i++) {
            File file = new File(imageStoreList.get(i));
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("plan_image[]", file.getName(), requestBody);
            plan_image.add(filePart);
        }


        List<MultipartBody.Part> store_image = new ArrayList<>();
        for (int i = 0; i < ssmImageStoreList.size(); i++) {
            File file = new File(ssmImageStoreList.get(i));
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("store_image[]", file.getName(), requestBody);
            store_image.add(filePart);
        }

        Call<SubscriptionStoreResponse> call = jsonPlaceHolderApi.SubscriptionStore("Bearer " + sessionManager.getSavedToken(), data, plan_image, store_image);
        call.enqueue(new Callback<SubscriptionStoreResponse>() {
            @Override
            public void onResponse(Call<SubscriptionStoreResponse> call, Response<SubscriptionStoreResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);

                if (response.isSuccessful()) {

                    if (response.body().getStatus()) {

                        Intent intent = new Intent(SubscribeNewStoreActivity.this, DashboardActivity.class);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FILE) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            if(SetImage.equals("StoreImg")){
                if (data.getData() != null) {
                    Uri selectedMediaUri = data.getData();
                    Bitmap bm=null;
                    if (data != null) {
                        try {
                            bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    BitmapDrawable d= new BitmapDrawable(bm);
                    int left =0;
                    int top = 0;
                    int right=40;
                    int bottom=40;
                    Rect r = new Rect(left,top,right,bottom);
                    d.setBounds(r);
                    Uri tempUri = getImageUri(context,bm);
                    System.out.println("data.getData() " + data.getData());
//                encodeimg1 = getRealPathFromURI(tempUri);
                    imgDecodableString = getRealPathFromURI(tempUri);
                    Log.e("GallerysingleImagePath", imgDecodableString);
                    imageStoreList.add(imgDecodableString);
                   /* adapter = new CustomGrid(getActivity(), pictures);
                    rv_selected_imagevideo.setAdapter(adapter);
                    adapter.notifyDataSetChanged();*/
                    System.out.println("pictures..StoreImg>>>>"+imageStoreList);
                    galleryAdapter = new GalleryAdapter(getApplicationContext(),imageStoreList);
                    gridViewImageStore.setAdapter(galleryAdapter);
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewImageStore
                            .getLayoutParams();
                    mlp.setMargins(0, gridViewImageStore.getHorizontalSpacing(), 0, 0);


              /*
                if(SetImage.equals("StoreImg")){
                    galleryAdapter = new GalleryAdapter(getApplicationContext(),pictures);
                    gridViewImageStore.setAdapter(galleryAdapter);
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewImageStore
                            .getLayoutParams();
                    mlp.setMargins(0, gridViewImageStore.getHorizontalSpacing(), 0, 0);


                }
                else if (SetImage.equals("SsmImg")){

                    System.out.println("pictures..SsmImg>>>>"+pictures);
                    ssmImageAdapter = new SsmImageAdapter(getApplicationContext(),pictures);
                    gridViewSsmImage.setAdapter(ssmImageAdapter);
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewSsmImage
                            .getLayoutParams();
                    mlp.setMargins(0, gridViewSsmImage.getHorizontalSpacing(), 0, 0);
                }*/

                }

                else {
                    if (data.getClipData() != null) {

                        Bitmap bm=null;

                        if (data != null) {
                            try {
                                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        BitmapDrawable d= new BitmapDrawable(bm);
                        int left =0;
                        int top = 0;
                        int right=40;
                        int bottom=40;
                        Rect r = new Rect(left,top,right,bottom);
                        d.setBounds(r);
                        Uri tempUri = getImageUri(context,bm);
                        System.out.println("data.getData() " + data.getData());
                        imgDecodableString = getRealPathFromURI(tempUri);
                        Log.e("GalleryImagePath", imgDecodableString);
                        imageStoreList.add(imgDecodableString);
                        System.out.println("pictures..StoreImg>>>>"+imageStoreList);

                        galleryAdapter = new GalleryAdapter(getApplicationContext(),imageStoreList);
                        gridViewImageStore.setAdapter(galleryAdapter);
                        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewImageStore
                                .getLayoutParams();
                        mlp.setMargins(0, gridViewImageStore.getHorizontalSpacing(), 0, 0);



                      /*  if(SetImage.equals("StoreImg")){

                            galleryAdapter = new GalleryAdapter(getApplicationContext(),pictures);
                            gridViewImageStore.setAdapter(galleryAdapter);
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewImageStore
                                    .getLayoutParams();
                            mlp.setMargins(0, gridViewImageStore.getHorizontalSpacing(), 0, 0);


                        }

                        else if (SetImage.equals("SsmImg")){

                            System.out.println("pictures..SsmImg>>>>"+pictures);
                            ssmImageAdapter = new SsmImageAdapter(getApplicationContext(),pictures);
                            gridViewSsmImage.setAdapter(ssmImageAdapter);
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewSsmImage
                                    .getLayoutParams();
                            mlp.setMargins(0, gridViewSsmImage.getHorizontalSpacing(), 0, 0);
                        } */

                    }
                }
            }

            else if (SetImage.equals("SsmImg")){
                if (data.getData() != null) {
                    Uri selectedMediaUri = data.getData();
                    Bitmap bm=null;
                    if (data != null) {
                        try {
                            bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    BitmapDrawable d= new BitmapDrawable(bm);
                    int left =0;
                    int top = 0;
                    int right=40;
                    int bottom=40;
                    Rect r = new Rect(left,top,right,bottom);
                    d.setBounds(r);
                    Uri tempUri = getImageUri(context,bm);
                    System.out.println("data.getData() " + data.getData());
//                encodeimg1 = getRealPathFromURI(tempUri);
                    imgDecodableString = getRealPathFromURI(tempUri);
                    Log.e("GallerysingleImagePath", imgDecodableString);
                    ssmImageStoreList.add(imgDecodableString);
                   /* adapter = new CustomGrid(getActivity(), pictures);
                    rv_selected_imagevideo.setAdapter(adapter);
                    adapter.notifyDataSetChanged();*/
                    System.out.println("pictures..SsmImg>>>>"+ ssmImageStoreList);
                    ssmImageAdapter = new SsmImageAdapter(getApplicationContext(), ssmImageStoreList);
                    gridViewSsmImage.setAdapter(ssmImageAdapter);
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewSsmImage
                            .getLayoutParams();
                    mlp.setMargins(0, gridViewSsmImage.getHorizontalSpacing(), 0, 0);


              /*
                if(SetImage.equals("StoreImg")){
                    galleryAdapter = new GalleryAdapter(getApplicationContext(),pictures);
                    gridViewImageStore.setAdapter(galleryAdapter);
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewImageStore
                            .getLayoutParams();
                    mlp.setMargins(0, gridViewImageStore.getHorizontalSpacing(), 0, 0);


                }
                else if (SetImage.equals("SsmImg")){

                    System.out.println("pictures..SsmImg>>>>"+pictures);
                    ssmImageAdapter = new SsmImageAdapter(getApplicationContext(),pictures);
                    gridViewSsmImage.setAdapter(ssmImageAdapter);
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewSsmImage
                            .getLayoutParams();
                    mlp.setMargins(0, gridViewSsmImage.getHorizontalSpacing(), 0, 0);
                }*/

                }

                else {
                    if (data.getClipData() != null) {

                        Bitmap bm=null;
                        if (data != null) {
                            try {
                                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        BitmapDrawable d= new BitmapDrawable(bm);
                        int left =0;
                        int top = 0;
                        int right=40;
                        int bottom=40;
                        Rect r = new Rect(left,top,right,bottom);
                        d.setBounds(r);
                        Uri tempUri = getImageUri(context,bm);
                        System.out.println("data.getData() " + data.getData());
                        imgDecodableString = getRealPathFromURI(tempUri);
                        Log.e("GalleryImagePath", imgDecodableString);
                        ssmImageStoreList.add(imgDecodableString);

                        System.out.println("pictures..SsmImg>>>>"+ ssmImageStoreList);
                        ssmImageAdapter = new SsmImageAdapter(getApplicationContext(), ssmImageStoreList);
                        gridViewSsmImage.setAdapter(ssmImageAdapter);
                        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewSsmImage
                                .getLayoutParams();
                        mlp.setMargins(0, gridViewSsmImage.getHorizontalSpacing(), 0, 0);

                      /*  if(SetImage.equals("StoreImg")){

                            galleryAdapter = new GalleryAdapter(getApplicationContext(),pictures);
                            gridViewImageStore.setAdapter(galleryAdapter);
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewImageStore
                                    .getLayoutParams();
                            mlp.setMargins(0, gridViewImageStore.getHorizontalSpacing(), 0, 0);


                        }

                        else if (SetImage.equals("SsmImg")){

                            System.out.println("pictures..SsmImg>>>>"+pictures);
                            ssmImageAdapter = new SsmImageAdapter(getApplicationContext(),pictures);
                            gridViewSsmImage.setAdapter(ssmImageAdapter);
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewSsmImage
                                    .getLayoutParams();
                            mlp.setMargins(0, gridViewSsmImage.getHorizontalSpacing(), 0, 0);
                        } */

                    }
                }
            }

        }



    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = null;
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
            path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "" + System.currentTimeMillis(), null);

            return Uri.parse(path);




        } catch (Exception e) {
            e.printStackTrace();
        }
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentURI, projection, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = 0;
            idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }

        return result;

    }

    public class GalleryAdapter extends BaseAdapter {
        private Context ctx;
        private int pos;
        private LayoutInflater inflater;
        private ImageView ivGallery,ivRemoveImg;
        ArrayList<String> imageListStore;
        public GalleryAdapter(Context ctx, ArrayList<String> imageListStore) {
            this.ctx = ctx;
            this.imageListStore = imageListStore;
        }

        @Override
        public int getCount() {
            return imageListStore.size();
        }

        @Override
        public Object getItem(int position) {
            return imageListStore.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            pos = position;
            inflater = (LayoutInflater) ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View itemView = inflater.inflate(R.layout.gv_image_subscription_new_store, parent, false);

            ivGallery = (ImageView) itemView.findViewById(R.id.ivStoreImg);
            ivRemoveImg = (ImageView) itemView.findViewById(R.id.ivRemoveImg);



            Bitmap bmp = BitmapFactory.decodeFile(imageListStore.get(position));

            ivGallery.setImageBitmap(bmp);


            ivRemoveImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("position >>>>>>>>>>>" + position);
                    imageListStore.remove(position);
                    System.out.println("web list >>>>>>>>>>>" + imageListStore);
                      // web.notifyAll();

                    galleryAdapter = new GalleryAdapter( SubscribeNewStoreActivity.this,imageListStore);
                    gridViewImageStore.setAdapter(galleryAdapter);
                    galleryAdapter.notifyDataSetChanged();
                }
            });



            return itemView;
        }


    }


    public class SsmImageAdapter extends BaseAdapter {
        private Context ctx;
        private int pos;
        private LayoutInflater inflater;
        private ImageView ivSsmImg,ivRemoveImgSsm;
        ArrayList<String> ssmListImage;
        public SsmImageAdapter(Context ctx, ArrayList<String> ssmListImage) {

            this.ctx = ctx;
            this.ssmListImage = ssmListImage;
        }

        @Override
        public int getCount() {
            return ssmListImage.size();
        }

        @Override
        public Object getItem(int position) {
            return ssmListImage.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            pos = position;
            inflater = (LayoutInflater) ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.gv_ssm_image, parent, false);

            ivSsmImg = (ImageView) itemView.findViewById(R.id.ivSsmImg);
            ivRemoveImgSsm = (ImageView) itemView.findViewById(R.id.ivRemoveImgSsm);

            //ivGallery.setImageURI(Uri.parse(mArrayUri.get(position)));
            Bitmap bmp = BitmapFactory.decodeFile(ssmListImage.get(position));
            ivSsmImg.setImageBitmap(bmp);

            ivRemoveImgSsm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("position >>>>>>>>>>>" + position);
                    ssmListImage.remove(position);
                    System.out.println("web list >>>>>>>>>>>" + ssmListImage);
                    // web.notifyAll();

                    ssmImageAdapter = new SsmImageAdapter( SubscribeNewStoreActivity.this,ssmListImage);
                    gridViewImageStore.setAdapter(galleryAdapter);
                    galleryAdapter.notifyDataSetChanged();
                }
            });


            return itemView;
        }


    }

}

