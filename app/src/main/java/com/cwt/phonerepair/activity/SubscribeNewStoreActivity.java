package com.cwt.phonerepair.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.modelclass.response.sbscriptionstore.SubscriptionStoreResponse;
import com.cwt.phonerepair.modelclass.response.subscriptionPlan.SubscriptionPlanModel;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.RoundRectCornerImageView;
import com.cwt.phonerepair.utils.SessionManager;
import com.cwt.phonerepair.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    EditText etStoreName, etRegistrationNumber, etAddress, etAboutStore;
    LinearLayout llStoreImg, llSsmImg;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    String picturePath;
    String SetImage = "StoreImg";
//    GalleryAdapter galleryAdapter;
    private Button btn;
    int PICK_IMAGE_MULTIPLE = 1;
    int PICK_IMAGE_MULTIPLE_SSM = 2;
    String imageEncoded;
    List<String> imagesEncodedList;
    private RecyclerView rvImageStore, rvSsmImage;
//    SsmImageAdapter ssmImageAdapter;
    ArrayList<Uri> mArrayUri;
    String imgDecodableString;

    ArrayList<SubscriptionPlanModel> modelArrayList;
    String price, title, duration, items, startdate, enddate, planId;
    SubscriptionPlanModel subscriptionPlanModelMain;
    public static final int REQUEST_STORAGE = 2;
//    public static final int SELECT_FILE = 110;

    //------------------------file code====================================
    String encodeStoreimg,encodeSsmimg, cartkey;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    File uploadFileI;
    ArrayList<String> storeImageFiles = new ArrayList<>();
    ArrayList<String> ssmImageFiles = new ArrayList<>();
    //    CustomGrid adapter;
    StoreImagesAdapter storeImagesAdapter;
    SsmImageAdapter ssmImageAdapter;
    int gallery_val =10;
    //------------------------file code====================================
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
        rvSsmImage = findViewById(R.id.rvSsmImage);
        rvImageStore = findViewById(R.id.rvImageStore);


        btnSave.setOnClickListener(this);
        ivBackSubNewstore.setOnClickListener(this);
        llStoreImg.setOnClickListener(this);
        llSsmImg.setOnClickListener(this);

        rvImageStore.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager =new GridLayoutManager(getActivity(), 3);
        rvImageStore.setLayoutManager(new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false));

        rvSsmImage.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager =new GridLayoutManager(getActivity(), 3);
        rvSsmImage.setLayoutManager(new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false));


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


                        subScribeNewStore();



                }


                break;

            case R.id.llStoreImg:
                SetImage = "StoreImg";
                if (storeImageFiles.size()!=0)
                {
                    storeImageFiles.clear();
                    storeImagesAdapter = new StoreImagesAdapter(context, storeImageFiles);
                    rvImageStore.setAdapter(storeImagesAdapter);
                }
                selectStoreImage();
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
                if (ssmImageFiles.size()!=0)
                {
                    ssmImageFiles.clear();
                    ssmImageAdapter = new SsmImageAdapter(context, ssmImageFiles);
                    rvSsmImage.setAdapter(storeImagesAdapter);
                }
                selectSsmImage();
                break;

//                checkPermissionsForStorage();

            /*    Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Select Picture"), PICK_IMAGE_MULTIPLE_SSM);*/
                //  ChooseSSmImage(context);

            case R.id.ivBackSubNewstore:
                onBackPressed();
                break;

            default:
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
        for (int i = 0; i < storeImageFiles.size(); i++) {
            File file = new File(storeImageFiles.get(i));
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("plan_image[]", file.getName(), requestBody);
            plan_image.add(filePart);
        }


        List<MultipartBody.Part> store_image = new ArrayList<>();
        for (int i = 0; i < ssmImageFiles.size(); i++) {
            File file = new File(ssmImageFiles.get(i));
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
                        showBottomSheetDialog();
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

        customdialog.show();
        btnSave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customdialog.cancel();
            }


        });



    }

    //.....................StoreImageSelect........................//

    private void selectStoreImage() {


        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Upload Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";

                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                    } else {
                        if(storeImageFiles!=null){
                            if(storeImageFiles.size()==10){
                                Toast.makeText(context,"Can select a maximum of 10 images", Toast.LENGTH_SHORT).show();
                            }else{
                                storeImageCameraIntent();

                            }
                        }else{
                            storeImageCameraIntent();


                        }
                    }


                }
                else if (items[item].equals("Choose from Library"))
                {
                    userChoosenTask = "Choose from Library";

                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        if(storeImageFiles!=null){
                            if(storeImageFiles.size()>0){
                                gallery_val= 10-storeImageFiles.size();
                            }
                        }
                       /* Intent intent = new Intent(context, AlbumSelectActivity.class);
                        intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, gallery_val); // set limit for image selection
                        startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);*/
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                    }

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    private void storeImageCameraIntent() {
        try {

            System.out.println("CAMERA OPEN 22");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //.....................SsmImageSelect........................//

    private void  selectSsmImage(){
        {
            final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Upload Photo");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @SuppressLint("NewApi")
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Take Photo")) {
                        userChoosenTask = "Take Photo";

                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 22);
                        } else {
                            if(storeImageFiles!=null){
                                if(storeImageFiles.size()==10){
                                    Toast.makeText(context,"Can select a maximum of 10 images", Toast.LENGTH_SHORT).show();
                                }else{
                                    ssmImageCameraIntent();

                                }
                            }else{
                                ssmImageCameraIntent();

                            }
                        }


                    }
                    else if (items[item].equals("Choose from Library"))
                    {
                        userChoosenTask = "Choose from Library";

                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
                        } else {
                            if(storeImageFiles!=null){
                                if(storeImageFiles.size()>0){
                                    gallery_val= 10-storeImageFiles.size();
                                }
                            }
                       /* Intent intent = new Intent(context, AlbumSelectActivity.class);
                        intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, gallery_val); // set limit for image selection
                        startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);*/
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 11);
                        }

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();

        }
    }

    private void ssmImageCameraIntent() {
        try {

            System.out.println("CAMERA OPEN 22");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 22);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("Request Code >>>>>>>" + requestCode);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                 /*   Intent intent = new Intent(context, AlbumSelectActivity.class);
                    intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, gallery_val); // set limit for image selection
                    startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);*/
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

                } else {
//                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    storeImageCameraIntent();
                    ssmImageCameraIntent();

                } else {
//                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            System.out.println("request code >>>>>>>>>"+requestCode);
            if (requestCode == 1 && data != null) {
             /*  ArrayList<Image> images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

               for(int i=0;i<images.size();i++){
                   Uri uri = Uri.fromFile(new File(images.get(i).path));
                   encodeimg1 = getRealPathFromURI(uri);
                   files.add(encodeimg1);
               }
               adapter = new ImagesAdapter(context,  files) ;
               grid.setAdapter(adapter);*/
                if (data.getClipData() != null) {
                    System.out.println("clip data >>>>>>>>>"+data.getClipData());
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    System.out.println("Img count >>>>>>>>>"+count);
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        getStoreImageFilePath(imageUri);
                    }
                }
                else
                {
                    Bitmap bm=null;
                    if (data != null) {
                        try {
                            bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    BitmapDrawable d= new BitmapDrawable(bm);
//define bounds for your drawable
                    int left =0;
                    int top = 0;
                    int right=40;
                    int bottom=40;

                    Rect r = new Rect(left,top,right,bottom);
                    //set the new bounds to your drawable
                    d.setBounds(r);
                    Uri tempUri = getStoreImageUri(context,bm);
//                getImageFilePath(tempUri);
                    encodeStoreimg = getStoreImageRealPathFromURI(tempUri);

                    System.out.println("encodeimg1 >>>>>>>>>>>"+ encodeStoreimg);
                    storeImageFiles.add(encodeStoreimg);


                    storeImagesAdapter = new StoreImagesAdapter(context, storeImageFiles);
                    rvImageStore.setAdapter(storeImagesAdapter);

                }
            }

            else if (requestCode == 11 && data != null) {
             /*  ArrayList<Image> images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

               for(int i=0;i<images.size();i++){
                   Uri uri = Uri.fromFile(new File(images.get(i).path));
                   encodeimg1 = getRealPathFromURI(uri);
                   files.add(encodeimg1);
               }
               adapter = new ImagesAdapter(context,  files) ;
               grid.setAdapter(adapter);*/
                if (data.getClipData() != null) {
                    System.out.println("clip data >>>>>>>>>"+data.getClipData());
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    System.out.println("Img count >>>>>>>>>"+count);
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        getSsmImageFilePath(imageUri);
                    }
                }
                else
                {
                    Bitmap bm=null;
                    if (data != null) {
                        try {
                            bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    BitmapDrawable d= new BitmapDrawable(bm);
//define bounds for your drawable
                    int left =0;
                    int top = 0;
                    int right=40;
                    int bottom=40;

                    Rect r = new Rect(left,top,right,bottom);
                    //set the new bounds to your drawable
                    d.setBounds(r);
                    Uri tempUri = getSsmImageUri(context,bm);
//                getImageFilePath(tempUri);
                    encodeSsmimg = getSsmImageRealPathFromURI(tempUri);

                    System.out.println("encodeimg1 >>>>>>>>>>>"+ encodeSsmimg);
                    ssmImageFiles.add(encodeSsmimg);


                    ssmImageAdapter = new SsmImageAdapter(context, ssmImageFiles);
                    rvSsmImage.setAdapter(ssmImageAdapter);

                }
            }


            if (requestCode == 2 && null !=data) {

                onCaptureStoreImageResult(data);

            }

            else if (requestCode == 22 && null !=data){
                onCaptureSsmImageResult(data);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    //.....................StoreImageCapture........................//

    private void onCaptureStoreImageResult(Intent StoreImagedata) {
        try {

            Bitmap storeImagethumbnail = (Bitmap) StoreImagedata.getExtras().get("StoreImagedata");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            storeImagethumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri tempUri = getStoreImageUri(context,storeImagethumbnail);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            //   File finalFile = new File(getRealPathFromURI(tempUri));

            System.out.println("StoreImagedata.getData() " + StoreImagedata.getData());
            encodeStoreimg = getStoreImageRealPathFromURI(tempUri);

            storeImageFiles.add(encodeStoreimg);

            storeImagesAdapter = new StoreImagesAdapter(context, storeImageFiles);
            rvImageStore.setAdapter(storeImagesAdapter);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Uri getStoreImageUri(Context inContext, Bitmap inImage) {
        String storeImagepath = null;
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            Long tsLong = System.currentTimeMillis()/1000;
            storeImagepath = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, ""+tsLong, null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return Uri.parse(storeImagepath);
    }

    //.............SsmImageCapture.............//

    private void onCaptureSsmImageResult(Intent ssmImagedata) {
        try {

            Bitmap ssmImagethumbnail = (Bitmap) ssmImagedata.getExtras().get("SsmImagedata");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ssmImagethumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri tempUri = getSsmImageUri(context,ssmImagethumbnail);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            //   File finalFile = new File(getRealPathFromURI(tempUri));

            System.out.println("SsmImagedata.getData() " + ssmImagedata.getData());
            encodeSsmimg = getSsmImageRealPathFromURI(tempUri);

            ssmImageFiles.add(encodeSsmimg);

            ssmImageAdapter = new SsmImageAdapter(context, ssmImageFiles);
            rvSsmImage.setAdapter(ssmImageAdapter);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Uri getSsmImageUri(Context inContext, Bitmap inImage) {
        String ssmImagePath = null;
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            Long tsLong = System.currentTimeMillis()/1000;
            ssmImagePath = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, ""+tsLong, null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return Uri.parse(ssmImagePath);
    }



    //.....................StorePathUri........................//

    private String getStoreImageRealPathFromURI(Uri StoreImagecontentURI) {
        String result;


        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(StoreImagecontentURI, projection, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = StoreImagecontentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = 0;
            idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }

        return result;

    }

    public void getStoreImageFilePath(Uri storeImageUri) {

        File file = new File(storeImageUri.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range") String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            storeImageFiles.add(imagePath);
            cursor.close();

            storeImagesAdapter = new StoreImagesAdapter(context,   storeImageFiles);
            rvImageStore.setAdapter(storeImagesAdapter);

        }
    }


    //.............SsmPathUri.............//
    private String getSsmImageRealPathFromURI(Uri ssmImagecontentURI) {
        String result;


        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(ssmImagecontentURI, projection, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = ssmImagecontentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = 0;
            idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }

        return result;

    }

    public void getSsmImageFilePath(Uri ssmImageUri) {

        File file = new File(ssmImageUri.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            ssmImageFiles.add(image);
            cursor.close();

            ssmImageAdapter = new SsmImageAdapter(context,ssmImageFiles);
            rvSsmImage.setAdapter(ssmImageAdapter);

        }
    }



    //.....................StoreAdapter........................//
    public class StoreImagesAdapter extends RecyclerView.Adapter<StoreImagesAdapter.ViewHolder> {
        private List<String> imageStoreList;
        private Context context;

        public StoreImagesAdapter(Context context, List<String> imageStoreList) {
            this.imageStoreList = imageStoreList;
            this.context = context;

        }

        // Create new views
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, null);
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_single, parent, false);

            ViewHolder viewHolder = new ViewHolder(itemLayoutView);
//            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));

            return viewHolder;
        }

        @SuppressLint("RecyclerView")
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, int position)
        {
            Bitmap bmp = BitmapFactory.decodeFile(storeImageFiles.get(position));
            viewHolder.gridStoreimage.setImageBitmap(bmp);
            viewHolder.imgStorecross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("position >>>>>>>>>>>"+position);
                    storeImageFiles.remove(position);
                    System.out.println("web list >>>>>>>>>>>"+storeImageFiles);
                    storeImagesAdapter = new StoreImagesAdapter(context, storeImageFiles);
                    rvImageStore.setAdapter(storeImagesAdapter);
                    storeImagesAdapter.notifyDataSetChanged();
                }
            });


        }

        // Return the size arraylist
        @Override
        public int getItemCount() {
            return imageStoreList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView imgStorecross;
            public RoundRectCornerImageView gridStoreimage;


            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);

                gridStoreimage = itemLayoutView.findViewById(R.id.gridStoreimage);
                imgStorecross = itemLayoutView.findViewById(R.id.imgStorecross);
            }
        }
        // method to access in activity after updating selection
        public List<String> getStoreImageList() {
            return imageStoreList;
        }

    }



    //.............SsmAdapter.............//
    public class SsmImageAdapter extends RecyclerView.Adapter<SsmImageAdapter.ViewHolder> {
        private List<String> ssmImageStoreList;
        private Context context;

        public SsmImageAdapter(Context context, List<String> ssmImageStoreList) {
            this.ssmImageStoreList = ssmImageStoreList;
            this.context = context;

        }

        // Create new views
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, null);
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gv_ssm_image, parent, false);

            ViewHolder viewHolder = new ViewHolder(itemLayoutView);
//            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));

            return viewHolder;
        }

        @SuppressLint("RecyclerView")
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, int position)
        {
            Bitmap bmp = BitmapFactory.decodeFile(ssmImageFiles.get(position));
            viewHolder.grid_imageSsm.setImageBitmap(bmp);
            viewHolder.imgSSmcross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("position >>>>>>>>>>>"+position);
                    ssmImageFiles.remove(position);
                    System.out.println("web list >>>>>>>>>>>"+ssmImageFiles);
                    ssmImageAdapter = new SsmImageAdapter(context, ssmImageFiles);
                    rvSsmImage.setAdapter(ssmImageAdapter);
                    ssmImageAdapter.notifyDataSetChanged();
                }
            });


        }

        // Return the size arraylist
        @Override
        public int getItemCount() {
            return ssmImageStoreList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView imgSSmcross;
            public RoundRectCornerImageView grid_imageSsm;


            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);

                imgSSmcross = itemLayoutView.findViewById(R.id.imgSSmcross);
                grid_imageSsm = itemLayoutView.findViewById(R.id.grid_imageSsm);
            }
        }
        // method to access in activity after updating selection
        public List<String> getSsmList() {
            return ssmImageStoreList;
        }

    }

}

/*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("TAG","data"+data);
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

   /*

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


    }*/


   /* public class SsmImageAdapter extends BaseAdapter {
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


    }*/