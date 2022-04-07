package com.cwt.phonerepair.storeactivity;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
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
import com.cwt.phonerepair.adapter.GalleryAdapter2;
import com.cwt.phonerepair.modelclass.response.AddProduct.AddProductResponse;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementModel;
import com.cwt.phonerepair.utils.Customprogress;
import com.cwt.phonerepair.utils.FilePath;
import com.cwt.phonerepair.utils.SessionManager;

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

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llProductImg;

    GridView gridViewProduct;

    EditText etDescription, etPrice;

    ImageView ivBackAllPro;

    Button btnAddBtn;

    ArrayList<Uri> mArrayUri;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;

    Context context;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;
    List<String> imagesEncodedList;

    ProductManagementModel productManagementModel;


    ArrayList<String> imageList;
    int PICK_IMAGE_MULTIPLE = 1;

    String imageEncoded;
    String description, price;
    GalleryAdapter2 galleryAdapter;


    String imgDecodableString;
    ArrayList<String> pictures = new ArrayList<>();
    public static final int REQUEST_STORAGE = 2;
    public static final int SELECT_FILE = 110;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        initView();
        getData();
    }

    private void getData() {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                productManagementModel = (ProductManagementModel) intent.getSerializableExtra("data");


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


  /*  @Override
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
                } *//*else if (SetImage.equals("StoreImg")) {
                   // ChoosestoreImage(this);

                } else {

                    //ChooseSSmImage(this);
                }*//*
                break;
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {

                    Uri mImageUri = data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    cursor.close();

                    ArrayList<Uri> mArrayUri = new ArrayList<>();
                    mArrayUri.add(mImageUri);
                    imageList.add(mImageUri.getPath());

                    System.out.println("image....." + imageList);

                    galleryAdapter = new GalleryAdapter(getApplicationContext(), mArrayUri);
                    gridViewProduct.setAdapter(galleryAdapter);
                    gridViewProduct.setVerticalSpacing(gridViewProduct.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewProduct
                            .getLayoutParams();
                    mlp.setMargins(0, gridViewProduct.getHorizontalSpacing(), 0, 0);

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
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                            imageList.add(imageEncoded.toString());

                            System.out.println("image....." + imageList);


                            galleryAdapter = new GalleryAdapter(getApplicationContext(), mArrayUri);
                            gridViewProduct.setAdapter(galleryAdapter);
                            gridViewProduct.setVerticalSpacing(gridViewProduct.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridViewProduct
                                    .getLayoutParams();
                            mlp.setMargins(0, gridViewProduct.getHorizontalSpacing(), 0, 0);


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
    }*/


    private void initView() {

        context = AddProductActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        sessionManager = new SessionManager(this);
        imageList = new ArrayList<>();

        ivBackAllPro = findViewById(R.id.ivBackAllPro);
        gridViewProduct = findViewById(R.id.gridViewProduct);
        llProductImg = findViewById(R.id.llProductImg);
        etDescription = findViewById(R.id.etDescription);
        btnAddBtn = findViewById(R.id.btnAddBtn);
        etPrice = findViewById(R.id.etPrice);

        ivBackAllPro.setOnClickListener(this);
        btnAddBtn.setOnClickListener(this);
        llProductImg.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.ivBackAllPro:
                onBackPressed();
                break;
            case R.id.btnAddBtn:
                addProduct();
                break;

            case R.id.llProductImg:
               /* Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);*/
                checkPermissionsForStorage();
                break;

            default:
                break;

        }

    }


    private void addProduct() {

        Customprogress.showPopupProgressSpinner(context, true);

        HashMap<String, RequestBody> data = new HashMap<>();
        data.put("description", createRequestBody(etDescription.getText().toString().trim()));
        data.put("price", createRequestBody(etPrice.getText().toString().trim()));
        data.put("store_id", createRequestBody(productManagementModel.getStoreId().toString()));
        data.put("title", createRequestBody(productManagementModel.getTitle()));

        List<MultipartBody.Part> productImage = new ArrayList<>();
        for (int i = 0; i < pictures.size(); i++) {
            File file = new File(pictures.get(i));
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("product_image[]", file.getName(), requestBody);
            productImage.add(filePart);
        }
        Call<AddProductResponse> call = jsonPlaceHolderApi.AddProduct("Bearer " + sessionManager.getSavedToken(), data, productImage);
        call.enqueue(new Callback<AddProductResponse>() {
            @Override
            public void onResponse(Call<AddProductResponse> call, Response<AddProductResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Toast.makeText(AddProductActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddProductActivity.this, ProductManagementActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddProductActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<AddProductResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Toast.makeText(AddProductActivity.this, "" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private RequestBody createRequestBody(String s) {

        return RequestBody.create(MediaType.parse("multipart/form-data"), s);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermissionsForStorage() {
        if (ActivityCompat.checkSelfPermission(((Activity) context), WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(((Activity) context), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            System.out.println("GALLERY OPEN 22");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//           true intent.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY, -1);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
        } else {
            requestStoragePermission();
        }
    }
    private void requestStoragePermission() {
        try {
            ActivityCompat.requestPermissions(((Activity) context), new String[]{WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception>>", e.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FILE) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            if (data.getData() != null) {

                Uri selectedMediaUri = data.getData();
                if (selectedMediaUri.toString().contains("image")) {
                    Bitmap thumbnail = (BitmapFactory.decodeFile(FilePath.getPath(context, selectedMediaUri)));
                    Bitmap bitmap = Bitmap.createScaledBitmap(thumbnail, 720, 1280, true);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
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
                    Uri tempUri = getImageUri(context, thumbnail);
                    System.out.println("data.getData() " + data.getData());
                    imgDecodableString = getRealPathFromURI(tempUri);
                    Log.e("GallerysingleImagePath", imgDecodableString);
                    pictures.add(imgDecodableString);
                   /* adapter = new CustomGrid(getActivity(), pictures);
                    rv_selected_imagevideo.setAdapter(adapter);
                    adapter.notifyDataSetChanged();*/
                    System.out.println("pictures >>>>"+pictures);
                    galleryAdapter = new GalleryAdapter2(getApplicationContext(), pictures);
                    galleryAdapter.notifyDataSetChanged();
                    gridViewProduct.setAdapter(galleryAdapter);


                }
            } else {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri selectedMediaUri = item.getUri();
                        mArrayUri.add(selectedMediaUri);
                        if (selectedMediaUri.toString().contains("image")) {


                            Bitmap thumbnail = (BitmapFactory.decodeFile(FilePath.getPath(context, selectedMediaUri)));

                            Bitmap bitmap = Bitmap.createScaledBitmap(thumbnail, 720, 1280, true);
                            Log.e("Picture", bitmap.toString());
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                            File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+"" + ".jpg");

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
                            Uri tempUri = getImageUri(context, thumbnail);

                            // CALL THIS METHOD TO GET THE ACTUAL PATH
                            //   File finalFile = new File(getRealPathFromURI(tempUri));

                            System.out.println("data.getData() " + data.getData());
                            imgDecodableString = getRealPathFromURI(tempUri);
                            Log.e("GalleryImagePath", imgDecodableString);
                            pictures.add(imgDecodableString);
                            System.out.println("pictures >>>>"+pictures);
                            galleryAdapter = new GalleryAdapter2(getApplicationContext(), pictures);
                            galleryAdapter.notifyDataSetChanged();
                            gridViewProduct.setAdapter(galleryAdapter);


                        }
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

}