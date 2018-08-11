package com.example.user.waste_for_best;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {


    private Uri mCameraUri;

    private StorageReference mStorageRef;
    private DatabaseReference mdatabaseRef;
    private ProgressBar progressBar;
    private EditText descriptionText;


    ImageView imageView ;
    Button sendButtonId;
    ArrayList descriptionMessage = new ArrayList();
    TextView editPhotoId;


    String mCurrentPhotoPath;

   /* private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }*/



    public void editPhoto(View view){

        takePhoto();
    }

    public void sendButton(View view){

        Log.i("Message",descriptionMessage.toString());
        uploadFile();

      //  Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
      //  startActivity(intent);


    }

    public void takePhoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
       /* if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);*/

                startActivityForResult(takePictureIntent, 1);
            }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode ==1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                takePhoto();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


      progressBar=findViewById(R.id.progressBar);
        descriptionText=findViewById(R.id.descriptionText);

        mStorageRef = FirebaseStorage.getInstance().getReference("images");
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("images");

           imageView = findViewById(R.id.imageView);
            descriptionText = findViewById(R.id.descriptionText);
            sendButtonId = findViewById(R.id.sendButtonId);
            editPhotoId = findViewById(R.id.editPhotoId);


            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            } else {

                takePhoto();
            }

            descriptionText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    descriptionMessage.clear();

                    if (charSequence.length() > 0) {

                        sendButtonId.setVisibility(View.VISIBLE);

                        descriptionMessage.add(String.valueOf(charSequence));

                    } else {

                        sendButtonId.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });



        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null){


            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            imageView.setImageBitmap(imageBitmap);

           /* ByteArrayOutputStream bytes=new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(),imageBitmap,"Title",null);
            Uri mCameraUri = Uri.parse(path);

            Log.i("_Uri",mCameraUri.toString());
            */



        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

private void uploadFile()
{
    if(mCameraUri != null)
    {
        StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mCameraUri));

        fileReference.putFile(mCameraUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(0);

                    }
                }, 500);
                Toast.makeText(CameraActivity.this, "Upload Succesful", Toast.LENGTH_LONG).show();
                upload uploadName = new upload(descriptionText.getText().toString().trim(),
                        taskSnapshot.getDownloadUrl().toString());
                String uploadId = mdatabaseRef.push().getKey();
                mdatabaseRef.child(uploadId).setValue(uploadName);



            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CameraActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressBar.setProgress((int) progress);

                    }
                });
    }
    else
    {
        Toast.makeText(this, "No photo Choosen", Toast.LENGTH_SHORT).show();
    }

}


}








