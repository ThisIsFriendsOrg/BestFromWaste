package com.example.user.waste_for_best;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class FoodWasteActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 2;
    private Uri mImageUri;


    ArrayList itemArray;
    ListView listView;
    CustomAdapter adapter;
    ArrayList content = new ArrayList();
    ImageView numberOfClickItems;
    ImageView imageGallery;
   // ImageView imageView;

    public void numberOfCheckedItems(View view) {

        numberOfClickItems = findViewById(R.id.numberOfClickItems);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation);
        numberOfClickItems.startAnimation(animation);


        if (content.size() > 0) {

            Log.i("Item List", content.toString());
            Intent intent = new Intent(this, CameraActivity.class);
            startActivity(intent);

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), " Please Select the item to continue.. ", Toast.LENGTH_LONG);
            View view1 = toast.getView();
            view1.setBackgroundColor(Color.BLUE);
            toast.show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_waste);



        listView = findViewById(R.id.listView);

        itemArray = new ArrayList();

        imageGallery=(ImageView)findViewById(R.id.galleryButton);

        itemArray.add(new DataModel("Dry", false));
        itemArray.add(new DataModel("Wet", false));
        itemArray.add(new DataModel("PartyLeftOver", false));
        itemArray.add(new DataModel("Restaurant", false));
        itemArray.add(new DataModel("Other", false));

        adapter = new CustomAdapter(getApplicationContext(), itemArray);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                DataModel dataModel = (DataModel) itemArray.get(i);
                dataModel.checked = !dataModel.checked;
                adapter.notifyDataSetChanged();

                if (dataModel.checked) {

                    content.add(dataModel.name);
                } else {
                    content.remove(dataModel.name);
                }
            }
        });

        imageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (content.size() > 0) {

                    Log.i("Item List", content.toString());
                    openFileChooser();

                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), " Please Select the item to continue.. ", Toast.LENGTH_LONG);
                    View view1 = toast.getView();
                    view1.setBackgroundColor(Color.BLUE);
                    toast.show();
                }

            }
        });





    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            mImageUri = data.getData();
            Log.i("Image uri", mImageUri.toString());

            Intent intent=new Intent(FoodWasteActivity.this,galleryActivity.class);
            intent.putExtra("imageuri",mImageUri.toString());
            startActivity(intent);


        }
    }
}
