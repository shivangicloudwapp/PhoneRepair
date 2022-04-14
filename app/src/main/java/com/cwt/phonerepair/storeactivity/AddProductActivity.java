package com.cwt.phonerepair.storeactivity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cwt.phonerepair.Interface.JsonPlaceHolderApi;
import com.cwt.phonerepair.R;
import com.cwt.phonerepair.Server.ApiUtils;
import com.cwt.phonerepair.modelclass.response.AddProduct.AddProductResponse;
import com.cwt.phonerepair.modelclass.response.AddProduct.ProductManagementModel;
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

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llProductImg;
    EditText etDescription, etPrice;

    ImageView ivBackAllPro;

    Button btnAddBtn;
    Context context;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionManager;
    ProductManagementModel productManagementModel;
    ArrayList<String> imageList;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    File uploadFileI;
    RecyclerView rvImageProduct;
    ArrayList<String> ProductImageFiles = new ArrayList<>();
    AddProductImageAdapter addProductImageAdapter;
    int gallery_val = 10;
    String encodeProductimg;

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
        rvImageProduct = findViewById(R.id.rvImageProduct);
        llProductImg = findViewById(R.id.llProductImg);
        etDescription = findViewById(R.id.etDescription);
        btnAddBtn = findViewById(R.id.btnAddBtn);
        etPrice = findViewById(R.id.etPrice);

        ivBackAllPro.setOnClickListener(this);
        btnAddBtn.setOnClickListener(this);
        llProductImg.setOnClickListener(this);

        rvImageProduct.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager =new GridLayoutManager(getActivity(), 3);
        rvImageProduct.setLayoutManager(new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false));


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

                } else {

                    if (Utils.checkConnection(context)) {

                        addProduct();

                    } else {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.llProductImg:

                if (ProductImageFiles.size() != 0) {
                    ProductImageFiles.clear();
                    addProductImageAdapter = new AddProductImageAdapter(context, ProductImageFiles);
                    rvImageProduct.setAdapter(addProductImageAdapter);
                }
                selectProductImage();

                break;

            default:
                break;

        }

    }



    //.....................ProductImage........................//


    private void selectProductImage() {

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
                        if(ProductImageFiles!=null){
                            if(ProductImageFiles.size()==10){
                                Toast.makeText(context,"Can select a maximum of 10 images", Toast.LENGTH_SHORT).show();
                            }else{
                                storeProductImageCameraIntent();

                            }
                        }else{
                            storeProductImageCameraIntent();


                        }
                    }


                }
                else if (items[item].equals("Choose from Library"))
                {
                    userChoosenTask = "Choose from Library";

                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        if(ProductImageFiles!=null){
                            if(ProductImageFiles.size()>0){
                                gallery_val= 10-ProductImageFiles.size();
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

    private void storeProductImageCameraIntent() {
        try {

            System.out.println("CAMERA OPEN 22");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 2);
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
                    storeProductImageCameraIntent();


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
                        getProductImageFilePath(imageUri);
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
                    Uri tempUri = getProductImageUri(context,bm);
//                getImageFilePath(tempUri);
                    encodeProductimg = getProductImageRealPathFromURI(tempUri);

                    System.out.println("encodeimg1 >>>>>>>>>>>"+ encodeProductimg);
                    ProductImageFiles.add(encodeProductimg);


                    addProductImageAdapter = new AddProductActivity.AddProductImageAdapter(context, ProductImageFiles);
                    rvImageProduct.setAdapter(addProductImageAdapter);

                }
            }



            if (requestCode == 2 && null !=data) {

                onCaptureProductImageResult(data);

            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }


    private void onCaptureProductImageResult(Intent ProductImageData) {
        try {

            Bitmap storeImagethumbnail = (Bitmap) ProductImageData.getExtras().get("ProductImagedata");
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
            Uri tempUri = getProductImageUri(context,storeImagethumbnail);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            //   File finalFile = new File(getRealPathFromURI(tempUri));

            System.out.println("ProductImagedata.getData() " + ProductImageData.getData());
            encodeProductimg = getProductImageRealPathFromURI(tempUri);

            ProductImageFiles.add(encodeProductimg);

            addProductImageAdapter = new AddProductActivity.AddProductImageAdapter(context, ProductImageFiles);
            rvImageProduct.setAdapter(addProductImageAdapter);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    private Uri getProductImageUri(Context context, Bitmap inImage) {

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



    private String getProductImageRealPathFromURI(Uri ProductImagecontentURI) {
            String result;


            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(ProductImagecontentURI, projection, null, null, null);
            if (cursor == null) { // Source is Dropbox or other similar local file path
                result = ProductImagecontentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = 0;
                idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
                cursor.close();
            }

            return result;

        }


    public void getProductImageFilePath(Uri storeImageUri) {

        File file = new File(storeImageUri.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range") String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            ProductImageFiles.add(imagePath);
            cursor.close();

            addProductImageAdapter = new AddProductActivity.AddProductImageAdapter(context,ProductImageFiles);
            rvImageProduct.setAdapter(addProductImageAdapter);

        }
    }



    public class AddProductImageAdapter extends RecyclerView.Adapter<AddProductActivity.AddProductImageAdapter.ViewHolder> {
        private List<String> productImageList;
        private Context context;

        public AddProductImageAdapter(Context context, List<String> imageStoreList) {
            this.productImageList = imageStoreList;
            this.context = context;

        }

        // Create new views
        @Override
        public AddProductActivity.AddProductImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, null);
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_single, parent, false);

            AddProductActivity.AddProductImageAdapter.ViewHolder viewHolder = new AddProductActivity.AddProductImageAdapter.ViewHolder(itemLayoutView);
//            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));

            return viewHolder;
        }

        @SuppressLint("RecyclerView")
        @Override
        public void onBindViewHolder(final AddProductActivity.AddProductImageAdapter.ViewHolder viewHolder, int position)
        {
            Bitmap bmp = BitmapFactory.decodeFile(ProductImageFiles.get(position));
            viewHolder.gridStoreimage.setImageBitmap(bmp);
            viewHolder.imgStorecross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("position >>>>>>>>>>>"+position);
                    ProductImageFiles.remove(position);
                    System.out.println("web list >>>>>>>>>>>"+ProductImageFiles);
                    addProductImageAdapter = new AddProductActivity.AddProductImageAdapter(context, ProductImageFiles);
                    rvImageProduct.setAdapter(addProductImageAdapter);
                    addProductImageAdapter.notifyDataSetChanged();
                }
            });


        }

        // Return the size arraylist
        @Override
        public int getItemCount() {
            return productImageList.size();
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
        public List<String> getProductImageList() {
            return productImageList;
        }

    }



//................Api--AddProduct....................//



    private void addProduct() {
        Customprogress.showPopupProgressSpinner(context, true);
        HashMap<String, RequestBody> data = new HashMap<>();
        data.put("discription", createRequestBody(etDescription.getText().toString().trim()));
        data.put("price", createRequestBody(etPrice.getText().toString().trim()));
        data.put("store_id", createRequestBody(String.valueOf(productManagementModel.getStoreId())));
        System.out.println("store_id...." + productManagementModel.getStoreId().toString());
        data.put("title", createRequestBody(productManagementModel.getTitle()));

        List<MultipartBody.Part> productImage = new ArrayList<>();

        for (int i = 0; i < ProductImageFiles.size(); i++) {
            File file = new File(ProductImageFiles.get(i));
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
}




