package com.example.user.waste_for_best;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class phoneNumberVerificaton extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verificaton);

        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, CountryCode.countryNames));

        editText = findViewById(R.id.editText);

        findViewById(R.id.continueSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=CountryCode.countryAreaCodes[spinner.getSelectedItemPosition()];

                String number=editText.getText().toString().trim();

                if(number.isEmpty()|| number.length()<10)
                {
                    editText.setError("Valid Number is required");
                    editText.requestFocus();
                    return;
                }

                String phoneNumber="+" + code + number;

                Intent intent=new Intent(getApplicationContext(),verifyPhoneActivity.class);
                intent.putExtra("phonenumber",phoneNumber);
                startActivity(intent);

            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent intent=new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }

    public void continueSignUp(View view)
    {
        Toast.makeText(this, "Inside phone Sign Up", Toast.LENGTH_SHORT).show();
    }



}
