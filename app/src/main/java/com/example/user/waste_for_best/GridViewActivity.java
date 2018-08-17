package com.example.user.waste_for_best;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    GridView gridView;
    ImageView homeButtonId;
    ImageView cameraButtonId;

    ImageAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<upload> mUpload;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        gridView = findViewById(R.id.gridView);
        homeButtonId = findViewById(R.id.homeButtonId);
        cameraButtonId = findViewById(R.id.cameraButtonId);

        mUpload = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("buySell_Image");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUpload.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    upload upload = postSnapshot.getValue(com.example.user.waste_for_best.upload.class);
                    mUpload.add(upload);

                }

               mAdapter =new ImageAdapter(getApplicationContext(),mUpload);
                gridView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                  Toast.makeText(GridViewActivity.this, " Hi i'm at position " + position, Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void cameraButton(View view){

        Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(this,gridCameraActivity.class);
        startActivity(intent);


    }

    public void homeButton(View view){

        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();

        gridView.smoothScrollToPosition(0);
    }


}
