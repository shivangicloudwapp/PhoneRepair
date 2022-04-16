package com.cwt.phonerepair.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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

    private Button btn;

     RecyclerView rvImageStore, rvSsmImage;

    ArrayList<SubscriptionPlanModel> modelArrayList;
    String price, title, duration, items, startdate, enddate, planId;
    SubscriptionPlanModel subscriptionPlanModelMain;

    //------------------------file code====================================
    String encodeStoreimg,encodeSsmimg;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    File uploadFileI;
    ArrayList<String> storeImageFiles = new ArrayList<>();
    ArrayList<String> ssmImageFiles = new ArrayList<>();
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

        //........................for Recyclerview Image Store.......................//

        rvImageStore.setHasFixedSize(true);
        rvImageStore.setLayoutManager(new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false));


        //........................for Recyclerview SSm Image.......................//


        rvSsmImage.setHasFixedSize(true);
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
                if (storeImageFiles.size()!=0)
                {
                    storeImagesAdapter = new StoreImagesAdapter(context, storeImageFiles);
                    rvImageStore.setAdapter(storeImagesAdapter);
                }
                selectImage1();

                break;

            case R.id.llSsmImg:

                if (ssmImageFiles.size()!=0)
                {

                    ssmImageAdapter = new SsmImageAdapter(context, ssmImageFiles);
                    rvSsmImage.setAdapter(ssmImageAdapter);
                }
                selectImage2();

                break;

            case R.id.ivBackSubNewstore:
                onBackPressed();
                break;

            default:
        }
    }

    //.....................StoreImageSelect........................//

    private void selectImage1() {


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
                        ActivityCompat.requestPermissions(SubscribeNewStoreActivity.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                    } else {
                        if(storeImageFiles!=null){
                            if(storeImageFiles.size()==10){
                                Toast.makeText(context,"Can select a maximum of 10 images", Toast.LENGTH_SHORT).show();
                            }else{
                                CameraIntent1();

                            }
                        }else{
                            CameraIntent1();


                        }
                    }


                }
                else if (items[item].equals("Choose from Library"))
                {
                    userChoosenTask = "Choose from Library";

                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SubscribeNewStoreActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        /*if(storeImageFiles!=null){
                            if(storeImageFiles.size()>0){
                                gallery_val= 10-storeImageFiles.size();
                            }
                        }*/
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

    private void CameraIntent1() {
        try {

            System.out.println("CAMERA OPEN 22");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //.....................SsmImageSelect........................//


    private void selectImage2() {


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
                        ActivityCompat.requestPermissions(SubscribeNewStoreActivity.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 12);
                    } else {
                        if(ssmImageFiles!=null){
                            if(ssmImageFiles.size()==10){
                                Toast.makeText(context,"Can select a maximum of 10 images", Toast.LENGTH_SHORT).show();
                            }else{
                                CameraIntent2();

                            }
                        }else{
                            CameraIntent2();

                        }
                    }


                }
                else if (items[item].equals("Choose from Library"))
                {
                    userChoosenTask = "Choose from Library";

                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SubscribeNewStoreActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
                    } else {
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

    private void CameraIntent2() {
        try {

            System.out.println("CAMERA OPEN 22");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 12);
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

                }


                return;
            }

            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CameraIntent1();

                }

                else {
//                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;



            }


            case 3: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                 /*   Intent intent = new Intent(context, AlbumSelectActivity.class);
                    intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, gallery_val); // set limit for image selection
                    startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);*/
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 11);

                }


                return;
            }

            case 4: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CameraIntent2();

                }

                else {
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
                        getImageFilePath1(imageUri);
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
                    Uri tempUri = getImageUri1(context,bm);
//                getImageFilePath(tempUri);
                    encodeStoreimg= getImageRealPathFromURI1(tempUri);

                    System.out.println("encodeimg1 >>>>>>>>>>>"+encodeStoreimg);
                    storeImageFiles.add(encodeStoreimg);

                    storeImagesAdapter = new StoreImagesAdapter(context, storeImageFiles);
                    rvImageStore.setAdapter(storeImagesAdapter);
                }
            }
            if (requestCode == 2 && null !=data) {

                onCaptureResult1(data);
            }


            if (requestCode == 11 && data != null) {

                if (data.getClipData() != null) {
                    System.out.println("clip data >>>>>>>>>"+data.getClipData());
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    System.out.println("Img count >>>>>>>>>"+count);
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        getImageFilePath2(imageUri);
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
                    Uri tempUri = getImageUri2(context,bm);
//                getImageFilePath(tempUri);
                    encodeSsmimg= getImageRealPathFromURI2(tempUri);

                    System.out.println("encodeimg1 >>>>>>>>>>>"+encodeSsmimg);
                    ssmImageFiles.add(encodeSsmimg);

                    ssmImageAdapter = new SsmImageAdapter(context, ssmImageFiles);
                    rvSsmImage.setAdapter(ssmImageAdapter);
                }
            }


            if (requestCode == 12 && null !=data) {

                onCaptureResult2(data);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    //.....................StoreImageSelect........................//

    private void onCaptureResult1(Intent data) {
        try {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
           thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            File destination = new File(Environment.getExternalStorageDirectory(), ""+System.currentTimeMillis() + ".jpg");

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
            Uri tempUri = getImageUri1(context,thumbnail);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            //   File finalFile = new File(getRealPathFromURI(tempUri));

            System.out.println("data.getData() " + data.getData());
            encodeStoreimg = getImageRealPathFromURI1(tempUri);

            storeImageFiles.add(encodeStoreimg);

            storeImagesAdapter = new StoreImagesAdapter(context, storeImageFiles);
            rvImageStore.setAdapter(storeImagesAdapter);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Uri getImageUri1(Context inContext, Bitmap inImage) {
        String path = null;
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
           path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, ""+System.currentTimeMillis(), null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return Uri.parse(path);
    }

    private String getImageRealPathFromURI1(Uri contentURI) {
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

    public void getImageFilePath1(Uri Uri) {
        File file = new File(Uri.getPath());
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

    //.....................SsmImageSelect........................//

    private void onCaptureResult2(Intent data) {
        try {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            File destination = new File(Environment.getExternalStorageDirectory(), ""+System.currentTimeMillis() + ".jpg");

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
            Uri tempUri = getImageUri2(context,thumbnail);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            //   File finalFile = new File(getRealPathFromURI(tempUri));

            System.out.println("data.getData() " + data.getData());
            encodeSsmimg = getImageRealPathFromURI2(tempUri);

            ssmImageFiles.add(encodeSsmimg);

            ssmImageAdapter = new SsmImageAdapter(context, ssmImageFiles);
            rvSsmImage.setAdapter(ssmImageAdapter);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Uri getImageUri2(Context inContext, Bitmap inImage) {
        String path = null;
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, ""+System.currentTimeMillis(), null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return Uri.parse(path);
    }

    private String getImageRealPathFromURI2(Uri contentURI) {
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

    public void getImageFilePath2(Uri Uri) {
        File file = new File(Uri.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range") String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            ssmImageFiles.add(imagePath);
            cursor.close();

            ssmImageAdapter = new SsmImageAdapter(context,   ssmImageFiles);
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

    //.....................SsmAdapter........................//
    public class SsmImageAdapter extends RecyclerView.Adapter<SsmImageAdapter.ViewHolder> {
        private List<String> imageSsmList;
        private Context context;

        public SsmImageAdapter(Context context, List<String> imageSsmList) {
            this.imageSsmList = imageSsmList;
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
            return imageSsmList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView imgSSmcross;
            public RoundRectCornerImageView grid_imageSsm;


            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);

                grid_imageSsm = itemLayoutView.findViewById(R.id.grid_imageSsm);
                imgSSmcross = itemLayoutView.findViewById(R.id.imgSSmcross);
            }
        }
        // method to access in activity after updating selection
        public List<String> getStoreImageList() {
            return imageSsmList;
        }

    }








//.........................Api...subScribeNewStore.........................//

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

                    }
                    else{
                        Toast.makeText(SubscribeNewStoreActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
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
                customdialog.dismiss();
                Intent intent = new Intent(SubscribeNewStoreActivity.this, DashboardActivity.class);
                startActivity(intent);
            }

        });


    }






}
