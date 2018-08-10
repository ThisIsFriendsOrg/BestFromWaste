package com.example.user.waste_for_best;


import android.content.Intent;
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

import java.util.ArrayList;

public class FoodWasteActivity extends AppCompatActivity {

   // private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;


    ArrayList itemArray;
    ListView listView;
    CustomAdapter adapter;
    ArrayList content = new ArrayList();
    ImageView numberOfClickItems;
    ImageView gallery123;
    ImageView imageView;

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





    }



}
