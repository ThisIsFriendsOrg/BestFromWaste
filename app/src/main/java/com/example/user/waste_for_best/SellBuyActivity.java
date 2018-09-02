package com.example.user.waste_for_best;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SellBuyActivity extends AppCompatActivity {

    private GridView gridView;

    private ImageAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<upload> mUpload;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                   gridView.smoothScrollToPosition(0);

                    return true;
                case R.id.navigation_camera:

                    Intent intent = new Intent(getApplicationContext(),gridCameraActivity.class);
                    startActivity(intent);
                    finish();

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_buy);


        gridView = findViewById(R.id.gridView);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        mUpload = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("buySell_Image");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUpload.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    upload upload = postSnapshot.getValue(com.example.user.waste_for_best.upload.class);
                    mUpload.add(upload);

                }

                mAdapter = new ImageAdapter(getApplicationContext(), mUpload);

                gridView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                Toast.makeText(SellBuyActivity.this, " Hi i'm at position " + position, Toast.LENGTH_SHORT).show();




            }
        });

    }

}
