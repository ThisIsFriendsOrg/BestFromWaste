package com.example.user.waste_for_best;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditProfImgActivity extends AppCompatActivity {

    private Uri selectedImage;
    private ImageView uploadImage;
    private Button setImageButton;

    private StorageReference mStorageRef;
    private DatabaseReference mdatabaseRef;
    private FirebaseAuth mAuth;
    private String name = "UnKnown";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

                getPhoto();
                //}
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_prof_img);

        uploadImage = findViewById(R.id.uploadImage);
        setImageButton = findViewById(R.id.setImageButton);
        mAuth = FirebaseAuth.getInstance();

        mStorageRef = FirebaseStorage.getInstance().getReference("profile_Image");
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("profile_Image").child(mAuth.getUid());


        if (Build.VERSION.SDK_INT < 23) {

            getPhoto();

        } else {

            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {

                getPhoto();
            }
        }
    }

    public void setImage(View view) {

        uploadFile();

        Intent intent = new Intent(getApplicationContext(),ProfilePicActivity.class);
        startActivity(intent);
        finish();
    }


    public void getPhoto() {


        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 7);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 7 && resultCode == RESULT_OK && data != null) {

            selectedImage = data.getData();

            uploadImage.setImageURI(selectedImage);


        }

    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile() {
        if (selectedImage != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(selectedImage));


            fileReference.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Toast.makeText(SetPriceActivity.this, "Upload Succesful", Toast.LENGTH_LONG).show();

                            if (phoneNumberVerificaton.userName != null){

                                name = phoneNumberVerificaton.userName.toString();
                            }else {

                                name = "UnKnown";
                            }
                            upload uploadName = new upload(name ,
                                    taskSnapshot.getDownloadUrl().toString());
                            String uploadId = mdatabaseRef.push().getKey();
                            mdatabaseRef.child(uploadId).setValue(uploadName);
                            Log.i("upload id", uploadId);
                            Log.i("upload Name", uploadName.toString());
                           // Log.i("Price", price);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfImgActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        } else {
            Toast.makeText(this, "No photo Choosen", Toast.LENGTH_SHORT).show();
        }

    }
}
