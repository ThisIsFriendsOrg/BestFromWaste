package com.example.user.waste_for_best;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class SetPriceActivity extends AppCompatActivity {

    EditText priceText;
    private Button setPriceButton;

    private String price;
    private StorageReference mStorageRef;
    private DatabaseReference mdatabaseRef;
    private Uri mCameraUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_price);

        mStorageRef = FirebaseStorage.getInstance().getReference("buySell_Image");
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("buySell_Image");

        priceText = findViewById(R.id.priceText);
        setPriceButton = findViewById(R.id.setPriceButton);

        Intent intent = getIntent();
        String path = intent.getStringExtra("CameraUri");
        mCameraUri = Uri.parse(path);

        //  priceText.setText("\u20B9");

        priceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (s.length() > 1) {

                    setPriceButton.setVisibility(View.VISIBLE);


                } else {

                    setPriceButton.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setPrice(View view) {

       // Log.i("_price", price.toString());

        price = priceText.getText().toString().trim();

        uploadFile();

        Intent intent = new Intent(this, SellBuyActivity.class);
        startActivity(intent);
        finish();


    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile() {
        if (mCameraUri != null) {

            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mCameraUri));

            fileReference.putFile(mCameraUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Toast.makeText(SetPriceActivity.this, "Upload Succesful", Toast.LENGTH_LONG).show();
                            upload uploadName = new upload(price ,
                                    taskSnapshot.getDownloadUrl().toString());
                            String uploadId = mdatabaseRef.push().getKey();
                            mdatabaseRef.child(uploadId).setValue(uploadName);
                            Log.i("upload id", uploadId);
                            Log.i("upload Name", uploadName.toString());
                            Log.i("Price", price);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SetPriceActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        } else {
            Toast.makeText(this, "No photo Choosen", Toast.LENGTH_SHORT).show();
        }

    }


}
