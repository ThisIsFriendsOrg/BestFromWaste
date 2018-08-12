package com.example.user.waste_for_best;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class galleryActivity extends AppCompatActivity {

    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mdatabaseRef;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private EditText descriptionText;
    private ImageView imageView;
    private static final int PICK_IMAGE_REQUEST = 2;
    FirebaseAuth mAuth;

    Button sendButtonId;
    ArrayList descriptionMessage = new ArrayList();
    TextView editPhotoId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String userName = mUser.getUid();

        mStorageRef=FirebaseStorage.getInstance().getReference("photos");
        mdatabaseRef= FirebaseDatabase.getInstance().getReference(userName);
        descriptionText = findViewById(R.id.descriptionText);
        sendButtonId = findViewById(R.id.sendButtonId);
        editPhotoId = findViewById(R.id.editPhotoId);
        progressBar=findViewById(R.id.progressBar);


        imageView=findViewById(R.id.imageView);
        Intent intent = getIntent();
        String s;
        s = intent.getStringExtra("imageuri");
        mImageUri= Uri.parse(s);
        setPhoto();


        editPhotoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
                setPhoto();
            }
        });
    }



    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile()
    {
        if(mImageUri != null)
        {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));


            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);

                        }
                    }, 1000);
                    Toast.makeText(galleryActivity.this, "Upload Succesful", Toast.LENGTH_LONG).show();
                    upload uploadName = new upload(descriptionMessage.toString(),
                            taskSnapshot.getDownloadUrl().toString());
                    String uploadId = mdatabaseRef.push().getKey();
                    mdatabaseRef.child(uploadId).setValue(uploadName);
                    Log.i("upload id",uploadId);
                    Log.i("upload Name",uploadName.toString());



                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(galleryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);

                        }
                    });
        }
        else
        {
            Toast.makeText(this, "No photo Choosen", Toast.LENGTH_SHORT).show();
        }

    }

    public void sendButton(View view)
    {
        uploadFile();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Uploading Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(0);
        progressDialog.show();

        Log.i("_Text",descriptionMessage.toString());
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

        }
    }

    public void setPhoto()
    {
        if (mImageUri != null) {
            Picasso
                    .with(this)
                    .load(mImageUri)
                    .fit()
                    .centerInside()
                    .into(imageView);

            Log.i("Image Uri",mImageUri.toString());


            descriptionText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    descriptionMessage.clear();

                    if (charSequence.length() > 0) {

                        sendButtonId.setVisibility(View.VISIBLE);

                        descriptionMessage.add(String.valueOf(charSequence));

                    } else {

                        sendButtonId.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }


}
