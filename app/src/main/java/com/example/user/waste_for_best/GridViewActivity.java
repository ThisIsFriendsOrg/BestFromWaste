package com.example.user.waste_for_best;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class GridViewActivity extends AppCompatActivity {

    GridView gridView;
    ImageView homeButtonId;
    ImageView cameraButtonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        gridView = findViewById(R.id.gridView);
        homeButtonId = findViewById(R.id.homeButtonId);
        cameraButtonId = findViewById(R.id.cameraButtonId);

        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                  Toast.makeText(GridViewActivity.this, " Hi i'm at position " + position, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void cameraButton(View view){

        Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,CameraActivity.class);
        startActivity(intent);

    }

    public void homeButton(View view){

        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }

}
