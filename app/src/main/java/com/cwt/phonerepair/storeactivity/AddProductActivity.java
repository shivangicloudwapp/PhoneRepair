package com.cwt.phonerepair.storeactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.activity.GalleryAdapter;
import com.cwt.phonerepair.modelclass.response.AddProduct.AddProductResponse;
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

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llProductImg;

    GridView gridViewProduct;

    EditText etDescription,etPrice;

    ImageView ivBackAllPro;

    Button btnAddBtn;

    ArrayList<Uri> mArrayUri;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;

    Context context;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;
    List<String> imagesEncodedList;
    int PICK_IMAGE_MULTIPLE = 1;

    String imageEncoded;
    String description, price;
    GalleryAdapter galleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        initView();
    }




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

                    ArrayList<Uri> mArrayUri = new ArrayList<>();
                    mArrayUri.add(mImageUri);

                    galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
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
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();


                                galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
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
    }


    private void initView() {

        context=AddProductActivity.this;
        jsonPlaceHolderApi= ApiUtils.getAPIService();
        sessionManager= new SessionManager(this);

        ivBackAllPro=findViewById(R.id.ivBackAllPro);
        gridViewProduct=findViewById(R.id.gridViewProduct);
        llProductImg=findViewById(R.id.llProductImg);
        etDescription=findViewById(R.id.etDescription);
        btnAddBtn=findViewById(R.id.btnAddBtn);
        etPrice=findViewById(R.id.etPrice);

        ivBackAllPro.setOnClickListener(this);
        btnAddBtn.setOnClickListener(this);
        llProductImg.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ivBackAllPro:
                onBackPressed();
                break;
            case R.id.btnAddBtn:
                addProduct();
                break;

            case R.id.llProductImg:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);

                break;

            default:
                break;

        }

    }

    private void addProduct() {

        Customprogress.showPopupProgressSpinner(context,true);

        HashMap<String, RequestBody> data = new HashMap<>();
        data.put("description", createRequestBody(etDescription.getText().toString().trim()));
        data.put("price", createRequestBody(etPrice.getText().toString().trim()));
        data.put("store_id", createRequestBody("1"));
        data.put("title", createRequestBody("fridge"));

        List<MultipartBody.Part> image = new ArrayList<>();

        if (mArrayUri != null && !mArrayUri.equals("")) {
            File file = new File(String.valueOf(mArrayUri));
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image = Collections.singletonList(MultipartBody.Part.createFormData("Product_image", file.getName(), requestFile));
        }

        Call<AddProductResponse>call= jsonPlaceHolderApi.AddProduct("Bearer "+sessionManager.getSavedToken(),data,image);
        call.enqueue(new Callback<AddProductResponse>() {
            @Override
            public void onResponse(Call<AddProductResponse> call, Response<AddProductResponse> response) {

                if (response.isSuccessful()){
                    Customprogress.showPopupProgressSpinner(context,false);

                    if (response.body().getStatus()){

                        Intent intent= new Intent(AddProductActivity.this,ProductManagementActivity.class);
                        startActivity(intent);

                    }
                }

            }

            @Override
            public void onFailure(Call<AddProductResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context,true);

            }
        });








    }




    private RequestBody createRequestBody(String s) {

        return RequestBody.create(MediaType.parse("multipart/form-data"), s);

    }




}