package com.cwt.phonerepair.storeactivity;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.cwt.phonerepair.activity.SubscribeNewStoreActivity;
import com.cwt.phonerepair.adapter.StoreImageAddProductAdapter;
import com.cwt.phonerepair.modelclass.response.AddProduct.AddProductResponse;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementModel;
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

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llProductImg;

    GridView gridViewProduct;

    EditText etDescription, etPrice;

    ImageView ivBackAllPro;

    Button btnAddBtn;
    Context context;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;

    ProductManagementModel productManagementModel;
    ArrayList<String> imageList;
    StoreImageAddProductAdapter storeImageAdapter;


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
                productManagementModel = (ProductManagementModel) intent.getSerializableExtra("store_id");


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




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


                String price = etPrice.getText().toString();
                String description = etDescription.getText().toString();

                if (TextUtils.isEmpty(price)) {
                    Toast.makeText(context, "Please Enter Price", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(description)) {
                    Toast.makeText(context, "Please Enter description", Toast.LENGTH_SHORT).show();

                }  else {

                    if (Utils.checkConnection(context)) {

                        addProduct();

                    } else {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
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

    private void addProduct(){
        Customprogress.showPopupProgressSpinner(context, true);
        HashMap<String, RequestBody> data = new HashMap<>();
        data.put("discription", createRequestBody(etDescription.getText().toString().trim()));
        data.put("price", createRequestBody(etPrice.getText().toString().trim()));
        data.put("store_id", createRequestBody(String.valueOf(productManagementModel.getStoreId())));
        System.out.println("store_id...."+productManagementModel.getStoreId().toString());
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
                        Intent intent = new Intent(AddProductActivity.this, ProductManagementActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddProductActivity.this, " Product not add successfully" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<AddProductResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Toast.makeText(AddProductActivity.this, "Product Add successfully" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                pictures.add(imgDecodableString);

                System.out.println("pictures >>>>"+pictures);
                storeImageAdapter = new StoreImageAddProductAdapter(getApplicationContext(), pictures);
                storeImageAdapter.notifyDataSetChanged();
                gridViewProduct.setAdapter(storeImageAdapter);

            } else {
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
                        pictures.add(imgDecodableString);
                        System.out.println("pictures >>>>"+pictures);
                    storeImageAdapter = new StoreImageAddProductAdapter(getApplicationContext(), pictures);
                    storeImageAdapter.notifyDataSetChanged();
                        gridViewProduct.setAdapter(storeImageAdapter);                    }
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



    public class StoreImageAddProductAdapter extends BaseAdapter {
        private Context ctx;
        private int pos;
        private LayoutInflater inflater;
        private ImageView ivRemoveAddProduct,ivImgAddProduct;
        ArrayList<String> mArrayUri;
        public StoreImageAddProductAdapter(Context ctx, ArrayList<String> mArrayUri) {

            this.ctx = ctx;
            this.mArrayUri = mArrayUri;
        }

        @Override
        public int getCount() {
            return mArrayUri.size();
        }

        @Override
        public Object getItem(int position) {
            return mArrayUri.get(position);
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

            View itemView = inflater.inflate(R.layout.gv_storeimage_addproduct, parent, false);

            ivRemoveAddProduct = (ImageView) itemView.findViewById(R.id.ivRemoveAddProduct);
            ivImgAddProduct = (ImageView) itemView.findViewById(R.id.ivImgAddProduct);

            //ivGallery.setImageURI(Uri.parse(mArrayUri.get(position)));
            Bitmap bmp = BitmapFactory.decodeFile(mArrayUri.get(position));
            ivImgAddProduct.setImageBitmap(bmp);
            ivRemoveAddProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("position >>>>>>>>>>>" + position);
                    mArrayUri.remove(position);
                    System.out.println("web list >>>>>>>>>>>" + mArrayUri);
                    // web.notifyAll();

                    storeImageAdapter = new AddProductActivity.StoreImageAddProductAdapter( AddProductActivity.this,mArrayUri);
                    gridViewProduct.setAdapter(storeImageAdapter);
                    storeImageAdapter.notifyDataSetChanged();
                }
            });

            return itemView;
        }


    }





}





















