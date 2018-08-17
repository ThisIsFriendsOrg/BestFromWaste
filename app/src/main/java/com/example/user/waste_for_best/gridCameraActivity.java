package com.example.user.waste_for_best;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class gridCameraActivity extends AppCompatActivity {

    private ImageView gridImageView;
    private Button uploadImageButton;
    private int cmr = 2;

    private Uri mCameraUri;
    static  String path;
    private StorageReference mStorageRef;
    private DatabaseReference mdatabaseRef;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode ==1){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Log.i("_WriteExternalStorage","Granted");


            }if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED){


                takePhoto();

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_camera);

        mStorageRef= FirebaseStorage.getInstance().getReference("buySell_Image");
        mdatabaseRef=FirebaseDatabase.getInstance().getReference("buySell_Image");
        gridImageView = findViewById(R.id.gridCameraId);
        uploadImageButton = findViewById(R.id.uploadImageButton);


        if (Build.VERSION.SDK_INT > 23) {

            if (((checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) &&
                    ((checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED))){

                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},1);

            }else{

                takePhoto();
            }
        }else {

            takePhoto();
        }

    }



    public void uploadImage(View view){

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation);
        uploadImageButton.startAnimation(animation);


       // uploadFile();

        Intent intent = new Intent(this,SetPriceActivity.class);
        intent.putExtra("CameraUri",path);
        startActivity(intent);


    }



    public void takePhoto(){


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent,cmr);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == cmr && resultCode == RESULT_OK && data != null){

            Bitmap imgBitmap = (Bitmap) data.getExtras().get("data");

            mCameraUri = getImageUri(getApplicationContext(),imgBitmap);

            gridImageView.setImageURI(mCameraUri);



        }
    }

    public Uri getImageUri(Context mContext, Bitmap bitmap){

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
         path = MediaStore.Images.Media.insertImage(mContext.getContentResolver(),bitmap,"title",null);

        return Uri.parse(path);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.edit,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.editCameraPhoto){

            takePhoto();


        }
        return false;
    }



   /* private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    public void uploadFile()
    {
        if(mCameraUri != null)
        {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mCameraUri));


            fileReference.putFile(mCameraUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(gridCameraActivity.this, "Upload Succesful", Toast.LENGTH_LONG).show();
                            upload uploadName = new upload(SetPriceActivity.price.toString(),
                                    taskSnapshot.getDownloadUrl().toString());
                            String uploadId = mdatabaseRef.push().getKey();
                            mdatabaseRef.child(uploadId).setValue(uploadName);
                            Log.i("upload id",uploadId);
                            Log.i("upload Name",uploadName.toString());
                            Log.i("Price",SetPriceActivity.price.toString());



                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(gridCameraActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }
        else
        {
            Toast.makeText(this, "No photo Choosen", Toast.LENGTH_SHORT).show();
        }

    }*/
}
