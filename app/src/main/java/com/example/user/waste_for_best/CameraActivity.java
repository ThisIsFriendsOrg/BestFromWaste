package com.example.user.waste_for_best;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class CameraActivity extends AppCompatActivity {

    ImageView imageView ;
    EditText descriptionText;
    Button sendButtonId;
    ArrayList descriptionMessage = new ArrayList();
    TextView editPhotoId;

    public void editPhoto(View view){

        takePhoto();
    }

    public void sendButton(View view){

        Log.i("Message",descriptionMessage.toString());

        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(intent);


    }

    public void takePhoto(){

        // Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath());
        startActivityForResult(intent, 1);
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

        imageView = findViewById(R.id.imageView);
        descriptionText = findViewById(R.id.descriptionText);
        sendButtonId = findViewById(R.id.sendButtonId);
        editPhotoId = findViewById(R.id.editPhotoId);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
        }else{

            takePhoto();
        }

        descriptionText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                descriptionMessage.clear();

                if(charSequence.length() > 0) {

                    sendButtonId.setVisibility(View.VISIBLE);

                    descriptionMessage.add(String.valueOf(charSequence));
                }else {

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

        }
    }

}

