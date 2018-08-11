package com.example.user.waste_for_best;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class galleryActivity extends AppCompatActivity {

    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mdatabaseRef;
    private ProgressBar progressBar;
    private EditText descriptionText;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        imageView=findViewById(R.id.imageView);
        Intent intent = getIntent();
        String s;
        s = intent.getStringExtra("imageuri");
        mImageUri= Uri.parse(s);
        if (mImageUri != null) {
            Picasso
                    .with(this)
                    .load(mImageUri)
                    .fit()
                    .centerInside()
                    .into(imageView);

            Log.i("Image Uri",mImageUri.toString());
        }
    }
}
