package com.example.user.waste_for_best;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfilePicActivity extends AppCompatActivity {

    private ImageView zoomProfileImage;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;
   private upload uploads;
    private List<upload> mUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pic);

        zoomProfileImage = findViewById(R.id.zoomProfileImage);

      //  mAuth = FirebaseAuth.getInstance();
      // mDatabaseRef = FirebaseDatabase.getInstance().getReference("profile_Image").child(mAuth.getUid());



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {

            case R.id.editCameraPhoto:

                Intent intent = new Intent(getApplicationContext(), EditProfImgActivity.class);

                startActivity(intent);
                finish();


                return true;

        }
        return false;
    }


}
