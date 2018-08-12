package com.example.user.waste_for_best;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class phoneNumberVerificaton extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    private EditText nameText;
    private ArrayList userName = new ArrayList<>();
    private Button continueSignUp;
    private String number;

    private RelativeLayout relativeLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verificaton);

        nameText = findViewById(R.id.nameText);
        spinner = findViewById(R.id.spinner);
        relativeLayout = findViewById(R.id.relativeLayout);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, CountryCode.countryNames));

        editText = findViewById(R.id.editText);
        continueSignUp = findViewById(R.id.continueSignUp);

        continueSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=CountryCode.countryAreaCodes[spinner.getSelectedItemPosition()];

                number = editText.getText().toString().trim();

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

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                userName.clear();

                if(s.length() ==10){

                    nameText.setVisibility(View.VISIBLE);

                    userName.add( nameText.getText().toString().trim());


                }else{

                    userName.clear();

                    nameText.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                userName.clear();

                if (s .length() > 0){

                    continueSignUp.setVisibility(View.VISIBLE);

                    userName.add(String.valueOf(s));


                }else {

                    continueSignUp.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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

public void relativeLayout(View view){

    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

}




}
